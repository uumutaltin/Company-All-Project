package com.works.repositories;

import com.works.entities.projection.GalleryJoinImage;
import com.works.entities.projection.LikeJoin;
import com.works.entities.projection.ProductLike;
import com.works.entities.restentity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<UserLike,Integer> {
    @Query(value = "SELECT c.cname,p.pname,u.userpoint,u.likedate FROM `user_like` AS u INNER JOIN product as p ON u.productid = p.pid INNER JOIN customer as c ON u.customerid = c.cid",nativeQuery = true)
    List<LikeJoin> allLikes();
    @Query(value = "SELECT p.pname,AVG(u.userpoint) as avg FROM `user_like` AS u INNER JOIN product as p ON p.pid = u.productid GROUP BY p.pid",nativeQuery = true)
    List<ProductLike> productsLikes();


}
