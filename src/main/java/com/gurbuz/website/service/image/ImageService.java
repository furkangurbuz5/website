package com.gurbuz.website.service.image;

import com.gurbuz.website.dto.ImageDto;
import com.gurbuz.website.exceptions.ResourceNotFoundException;
import com.gurbuz.website.model.Image;
import com.gurbuz.website.model.Product;
import com.gurbuz.website.repository.ImageRepository;
import com.gurbuz.website.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IIMageService {
        private final ImageRepository ir;
        private final IProductService ps;


        @Override
        public Image getImageById(Long id) {
                return ir.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
        }

        @Override
        public void deleteImageById(Long id) {
                ir.findById(id)
                        .ifPresentOrElse(ir :: delete, ()->{
                                throw new ResourceNotFoundException("No image found with id : "+id);
                        });
        }

        @Override
        public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
                Product product = ps.getProductById(productId);
                List<ImageDto> savedImageDto = new ArrayList<>();
                for(MultipartFile file : files){
                        try{
                                Image image = new Image();
                                image.setFileName(file.getOriginalFilename());
                                image.setFileType(file.getContentType());
                                image.setImage(new SerialBlob(file.getBytes()));
                                image.setProduct(product);

                                String buildDownloadUrl = "/api/v1/images/image/download/";
                                String downloadUrl = buildDownloadUrl + image.getId();
                                image.setDownloadUrl(downloadUrl);
                                Image savedImage = ir.save(image);

                                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                                ir.save(savedImage);

                                ImageDto imageDto = new ImageDto();

                                imageDto.setImageId(savedImage.getId());
                                imageDto.setImageName(savedImage.getFileName());
                                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                                savedImageDto.add(imageDto);
                        }catch(IOException | SQLException e){
                                throw new RuntimeException(e.getMessage());
                        }
                }
                return savedImageDto;
        }



        @Override
        public void updateImage(MultipartFile file, Long imageId) {
                Image image = getImageById(imageId);
                try {
                        image.setFileName(file.getOriginalFilename());
                        image.setFileName(file.getOriginalFilename());
                        image.setImage(new SerialBlob(file.getBytes()));
                        ir.save(image);
                } catch (IOException | SQLException e) {
                        throw new RuntimeException(e.getMessage());
                }
        }

}
