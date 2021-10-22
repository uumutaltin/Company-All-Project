package com.works.repositories;

import com.works.entities.Gallery;
import com.works.entities.projection.GalleryJoinImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery,Integer> {

    @Query(value = "SELECT g.gid,gi.giid,gi.giname,gi.gisubtitle FROM gallery as g INNER JOIN gallery_gallery_images as ggi ON g.gid = ggi.gallery_gid INNER JOIN gallery_image as gi on ggi.gallery_images_giid = gi.giid WHERE g.gid=:id",nativeQuery = true)
    List<GalleryJoinImage> allGalleryInfo(Integer id);



}
