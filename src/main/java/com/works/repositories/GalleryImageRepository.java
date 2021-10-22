package com.works.repositories;

import com.works.entities.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface GalleryImageRepository extends JpaRepository<GalleryImage,Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM gallery_gallery_images WHERE gallery_images_giid=:giid",nativeQuery = true)
    int deleteImage(Integer giid);



}
