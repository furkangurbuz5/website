package com.gurbuz.website.service.image;

import com.gurbuz.website.dto.ImageDto;
import com.gurbuz.website.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IImageService {

  Image getImageById(Long id);
  void deleteImageById(Long id);
  List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
  void updateImage(MultipartFile file, Long imageId);


}
