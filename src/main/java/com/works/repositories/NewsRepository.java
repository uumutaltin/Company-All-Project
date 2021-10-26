package com.works.repositories;

import com.works.entities.News;
import com.works.entities.redis.NewsRedis;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Integer> {

    @Query(value = "SELECT * from news where nstatus = : status and news_category_ncid = : id ", nativeQuery = true)
    List<News> findStatusAndId(Integer id , Boolean status, Pageable pageable);

    @Query("select n from News n where n.nstatus = ?1 and n.newsCategory.ncid = ?2 order by n.nid DESC")
    List<News> findFilterPage(Boolean nstatus, Integer ncid, Pageable pageable);

    @Query("select n from News n where n.nstatus = ?1 and n.newsCategory.ncid = ?2 order by n.nid DESC")
    List<News> findFilter(Boolean nstatus, Integer ncid);

    @Query(value = "SELECT * FROM news as n INNER JOIN news_category as nc on n.news_category_ncid = nc.ncid where nc.ncid = :id", nativeQuery = true)
    List<News> findCategory(Integer id);

    @Query("select n from News n where n.nstatus = true")
    List<News> activeNews();

    @Query("select n from News n where n.nstatus = false")
    List<News> passiveNews();




}
