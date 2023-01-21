package com.spring.blackcat.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.spring.blackcat.common.code.ImageType;
import com.spring.blackcat.common.exception.custom.FileResizingFailedException;
import com.spring.blackcat.common.exception.custom.ImageUploadFailedException;
import com.spring.blackcat.common.exception.custom.IncorrectlyFormattedFileException;
import lombok.RequiredArgsConstructor;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.spring.blackcat.common.exception.ErrorInfo.*;
import static java.util.Objects.requireNonNull;
import static javax.imageio.ImageIO.read;
import static javax.imageio.ImageIO.write;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String bucket;

    @Override
    public List<String> getImageUrls(ImageType imageType, Long mappedId) {
        List<String> imageUrls = new ArrayList<>();
        List<Image> images = imageRepository.findByImageTypeAndMappedId(imageType, mappedId);
        images.forEach(image -> imageUrls.add(image.getImageUrl()));

        return imageUrls;
    }

    @Override
    public List<String> saveImage(ImageType imageType, Long mappedId, List<MultipartFile> multipartFiles) {
        List<String> imageUrls = uploadImage(multipartFiles);
        imageUrls.forEach(imageUrl -> imageRepository.save(new Image(imageUrl, imageType, mappedId)));

        return imageUrls;
    }

    @Override
    public String deleteImage(String imageUrl) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, imageUrl));
        imageRepository.deleteByImageUrl(imageUrl);

        return imageUrl;
    }

    private List<String> uploadImage(List<MultipartFile> multipartFile) {
        List<String> imageUrlList = new ArrayList<>();

        multipartFile.forEach(file -> {
            if (requireNonNull(file.getContentType()).contains("image")) {
                String fileName = createFileName(file.getOriginalFilename());
                String fileFormatName = file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);

                MultipartFile resizedFile = resizeImage(fileName, fileFormatName, file, 600, 600);

                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(resizedFile.getSize());
                objectMetadata.setContentType(file.getContentType());

                try (InputStream inputStream = resizedFile.getInputStream()) {
                    amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                } catch (IOException e) {
                    throw new ImageUploadFailedException("이미지 업로드에 실패했습니다.", IMAGE_UPLOAD_FAILED);
                }

                imageUrlList.add(amazonS3.getUrl(bucket, fileName).toString());
            }
        });
        return imageUrlList;
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IncorrectlyFormattedFileException("올바르지 않은 파일 형식입니다.", INCORRECTLY_FORMATTED_FILE_EXCEPTION);
        }
    }

    private MultipartFile resizeImage(String fileName, String fileFormatName, MultipartFile originalImage, int targetWidth, int targetHeight) {
        try {
            BufferedImage image = read(originalImage.getInputStream());

            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            if (originWidth < targetWidth && originHeight < targetHeight) {
                return originalImage;
            }

            MarvinImage imageMarvin = new MarvinImage(image);
            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", targetWidth);
            scale.setAttribute("newHeight", targetHeight);
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            write(imageNoAlpha, fileFormatName, baos);
            baos.flush();

            return new MockMultipartFile(fileName, baos.toByteArray());
        } catch (IOException e) {
            throw new FileResizingFailedException("파일 리사이징에 실패했습니다.", FILE_RESIZING_FAILED_EXCEPTION);
        }
    }
}
