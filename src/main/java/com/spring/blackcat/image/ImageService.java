package com.spring.blackcat.image;

import com.spring.blackcat.common.code.ImageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<String> getImageUrls(ImageType imageType, Long mappedId);

    List<String> saveImage(ImageType imageType, Long mappedId, List<MultipartFile> multipartFiles);

    String deleteImage(String imageUrl);
}
