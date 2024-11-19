package com.gurbuz.website.repository;

import com.gurbuz.website.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
