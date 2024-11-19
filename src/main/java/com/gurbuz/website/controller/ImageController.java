package com.gurbuz.website.controller;

import com.gurbuz.website.dto.ImageDto;
import com.gurbuz.website.exceptions.ResourceNotFoundException;
import com.gurbuz.website.model.Image;
import com.gurbuz.website.model.Product;
import com.gurbuz.website.response.ApiResponse;
import com.gurbuz.website.service.image.IIMageService;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
        private final IIMageService iis;


        @PostMapping("/upload")
        public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
                try {
                        List<ImageDto> imageDtos = iis.saveImages(files, productId);
                        return ResponseEntity.ok(new ApiResponse("Upload successful", imageDtos));
                } catch (Exception e) {
                        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", e.getMessage()));
                }
        }

        @GetMapping("/image/download/")
        public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException{
                Image image = iis.getImageById(imageId);
                ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int)image.getImage().length()));
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(image.getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+image.getFileName() + "\"")
                        .body(resource);
        }

        @PutMapping("/image/{imageId}/update")
        public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
                try {
                        Image image = iis.getImageById(imageId);
                        if(image != null){
                                iis.updateImage(file, imageId);
                                return ResponseEntity.ok(new ApiResponse("Update success!", null));
                        }
                } catch (ResourceNotFoundException e) {
                        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
                }
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));

        }

        @DeleteMapping("/image/{imageId}/delete")
        public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
                try {
                        Image image = iis.getImageById(imageId);
                        if(image != null){
                                iis.deleteImageById(imageId);
                                return ResponseEntity.ok(new ApiResponse("Delete success!", null));
                        }
                } catch (ResourceNotFoundException e) {
                        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
                }
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));

        }


}
