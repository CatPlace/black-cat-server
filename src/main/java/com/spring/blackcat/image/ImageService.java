package com.spring.blackcat.image;

import com.spring.blackcat.post.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<String> getImageUrls(Long postId);

    List<String> saveImage(Post post, List<MultipartFile> multipartFile);

    String deleteImage(String imageUrl);
}
