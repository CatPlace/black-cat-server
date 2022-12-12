package com.spring.blackcat.image;

import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.ImageUploadFailedException;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Deprecated
public class ImageUtils {

    public static BufferedImage resize(File file, int width, int height) {
        BufferedImage inputImage = null;
        try {
            inputImage = ImageIO.read(file);
        } catch (IOException e) {
            throw new ImageUploadFailedException("이미지 리사이징 실패", ErrorInfo.IMAGE_UPLOAD_FAILED);
        }

        int imgWidth = Math.min(inputImage.getWidth(), inputImage.getHeight());
        int imgHeight = imgWidth;

        BufferedImage outputImage = Scalr.crop(inputImage, (inputImage.getWidth() - imgWidth) / 2, (inputImage.getHeight() - imgHeight) / 2, imgWidth, imgHeight, null);

        outputImage = Scalr.resize(outputImage, Scalr.Method.BALANCED, width, height, null);

        return outputImage;
    }
}
