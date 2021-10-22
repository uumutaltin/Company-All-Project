package com.works.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.documents.ElasticNews;
import com.works.entities.News;
import com.works.entities.NewsCategory;
import com.works.entities.redis.NewsRedis;
import com.works.repositories.NewsCategoryRepository;
import com.works.repositories.NewsRepository;
import com.works.repositories.elastic.ElasticSearchNewsRepository;
import com.works.repositories.redis.NewsRedisRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/news")
public class NewsController {

    final private String UPLOAD_DIR = "src/main/resources/static/uploads/news/";
    Integer searchSize;
    final NewsRepository nRepo;
    final NewsRedisRepository nrRepo;
    final ElasticSearchNewsRepository enRepo;
    final NewsCategoryRepository ncRepo;
    public NewsController(NewsRepository nRepo, NewsRedisRepository nrRepo, ElasticSearchNewsRepository enRepo, NewsCategoryRepository ncRepo) {
        this.nRepo = nRepo;
        this.nrRepo = nrRepo;
        this.enRepo = enRepo;
        this.ncRepo = ncRepo;
    }


    //--------------- html - start ---------------
    @GetMapping("")
    public String news(){
        return "news";
    }

    @GetMapping("/add")
    public String newsAdd(Model model){
        model.addAttribute("news", new News());
        return "newsAdd";
    }

    @GetMapping("/category")
    public String newsCategory(Model model){
        model.addAttribute("newsCategory", new NewsCategory());
        return "newsCategory";
    }

    @GetMapping("/update/{strnid}")
    public String newsUpdate(@PathVariable String strnid, Model model){
        NewsRedis newsRedis = nrRepo.findById(strnid).get();
        model.addAttribute("newsUpdate",newsRedis);
        return "newsEdit";
    }

    //--------------- html - end ---------------




    //--------------- mvc - start ---------------

    // news category Add - start
    @PostMapping("/category/add")
    public String newsCategoryAdd(@Valid @ModelAttribute("newsCategory")NewsCategory newsCategory, BindingResult bindingResult){
        try {
            System.out.println(bindingResult);
            if(!bindingResult.hasErrors()){

                ncRepo.save(newsCategory);
                return "redirect:/news";

            }

        }catch (Exception e){
            System.err.println("NewsCategoryAdd Error : " + e);
        }
        return "newsCategory";
    }
    // news category Add - end


    // news category List - start
    @ResponseBody
    @GetMapping("/category/list")
    public List<NewsCategory> newsCategoryList(){
        List<NewsCategory> newsCategories = ncRepo.findAll();
        return newsCategories;
    }
    // news category List - end

    // news Add - start
    @PostMapping("/newsAdd")
    public String newsAdd(@Valid @ModelAttribute("news")News news,BindingResult bindingResult,@RequestParam("newsimage_file") MultipartFile file,@RequestParam("newsCategoryID") String newsCatID){

        String fileName = null;
        if (!file.getOriginalFilename().isEmpty()) {
            fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = fileName.substring(fileName.length() - 4, fileName.length());

            String uui = UUID.randomUUID().toString();
            fileName = uui + ext;
            try {
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            int id = Integer.parseInt(newsCatID);
            NewsCategory newsCategory = ncRepo.findById(id).get();
            System.out.println(bindingResult);
            if(!bindingResult.hasErrors()){

                news.setNewsimage(fileName);
                news.setNewsCategory(newsCategory);
                nRepo.save(news);

                NewsRedis newsRedis = new NewsRedis();
                newsRedis.setNid(news.getNid());
                newsRedis.setNtitle(news.getNtitle());
                newsRedis.setNdescription(news.getNdescription());
                newsRedis.setNdetail(news.getNdetail());
                newsRedis.setNewsimage(news.getNewsimage());
                newsRedis.setNstatus(news.getNstatus());
                newsRedis.setNewsCategory(news.getNewsCategory());
                newsRedis.setNdate(news.getNdate());

                nrRepo.save(newsRedis);
                return "redirect:/news";
            }


        }catch (Exception e){
            System.err.println("NewsAdd Error : " + e);
        }
        return "newsAdd";
    }
    // news Add - end

    // news List - start
    @ResponseBody
    @GetMapping("/newsList/{pageNo}/{stpageSize}")
    public List<NewsRedis> newsList(@PathVariable String pageNo, @PathVariable String stpageSize){

        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);

        if( pageSize == -1) {
            List<NewsRedis> lsx = new ArrayList<>();
            Iterable<NewsRedis> page = nrRepo.findAll();
            List<News> newsList = nRepo.findAll();
            enRepo.deleteAll();

            for (NewsRedis item : page){
                lsx.add(item);
            }
            newsList.forEach(item->{
                ElasticNews elasticNews = new ElasticNews();
                elasticNews.setNid(item.getNid());
                elasticNews.setNtitle(item.getNtitle());
                elasticNews.setNdescription(item.getNdescription());
                elasticNews.setNdetail(item.getNdetail());
                elasticNews.setNewsimage(item.getNewsimage());
                elasticNews.setNstatus(item.getNstatus());
                elasticNews.setNewsCategory(item.getNewsCategory());
                elasticNews.setNdate(item.getNdate());

                enRepo.save(elasticNews);


            });
            Collections.reverse(lsx);
            return lsx;
        } else {
            Pageable pageable = PageRequest.of(ipageNumber, pageSize);
            Slice<NewsRedis> pageList = nrRepo.findByOrderByNidDesc(pageable);
            List<NewsRedis> ls = pageList.getContent();
            List<News> newsList = nRepo.findAll();
            enRepo.deleteAll();

            for(News item : newsList){
                ElasticNews elasticNews = new ElasticNews();
                elasticNews.setNid(item.getNid());
                elasticNews.setNtitle(item.getNtitle());
                elasticNews.setNdescription(item.getNdescription());
                elasticNews.setNdetail(item.getNdetail());
                elasticNews.setNewsimage(item.getNewsimage());
                elasticNews.setNstatus(item.getNstatus());
                elasticNews.setNewsCategory(item.getNewsCategory());
                elasticNews.setNdate(item.getNdate());

                enRepo.save(elasticNews);


            }
            return ls;
        }

    }
    // news List - end

    // pageCount - start
    @ResponseBody
    @GetMapping("/newsList/pageCount/{stpageSize}/{stPageStatus}")
    public Integer pageCount(@PathVariable String stpageSize,@PathVariable String stPageStatus) {
        Integer pageStatus = Integer.parseInt(stPageStatus);
        long dataCount;
        if (pageStatus == 1) {
            dataCount = nrRepo.count();
        }
        else{
            dataCount = searchSize;

        }
        double totalPageCount = Math.ceil((double)dataCount/Double.parseDouble(stpageSize));
        int pageCount = (int) totalPageCount;
        System.out.println("PageCount : " + pageCount);
        return pageCount;
    }
    // pageCount - end

    // news Delete - start
    @ResponseBody
    @GetMapping("/delete/{strnid}")
    public String newsDelete(@PathVariable String strnid){
        try {

            // redis delete - start
            NewsRedis newsRedis = nrRepo.findById(strnid).get();
            String fileNameUpdate = newsRedis.getNewsimage();
            File fileImage = new File(UPLOAD_DIR + fileNameUpdate);
            if (fileImage.exists()) {
                fileImage.delete();
            }

            int id = newsRedis.getNid();
            nrRepo.delete(newsRedis);
            // redis delete - end

            // db delete - start
            nRepo.deleteById(id);
            // db delete - end



        }catch (Exception e){
            System.err.println("News Delete Error : " + e);
        }
        return "news";
    }
    // news Delete - end

    // news Update - start
    @PostMapping("/newsUpdate/{strnid}")
    public String newsUpdate(@Valid @ModelAttribute("newsUpdate")NewsRedis newsRedisUpdate,@PathVariable String strnid, BindingResult bindingResult,@RequestParam("newsimage_file") MultipartFile file,@RequestParam("newsCategoryID") String newsCatID){

        NewsRedis newsRedis = nrRepo.findById(strnid).get();


        String fileName = null;
        if (!file.getOriginalFilename().isEmpty()) {
            fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = fileName.substring(fileName.length() - 4, fileName.length());

            String uui = UUID.randomUUID().toString();
            fileName = uui + ext;

            String fileNameUpdate = newsRedis.getNewsimage();
            File fileImage = new File(UPLOAD_DIR + fileNameUpdate);

            try {
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                if (fileImage.exists()) {
                    fileImage.delete();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fileName = newsRedis.getNewsimage();

        }
        try {
            int id = Integer.parseInt(newsCatID);
            NewsCategory newsCategory = ncRepo.findById(id).get();
            System.out.println(bindingResult);
            if(!bindingResult.hasErrors()){

                newsRedisUpdate.setNewsimage(fileName);
                newsRedisUpdate.setNewsCategory(newsCategory);

                newsRedisUpdate.setNid(newsRedis.getNid());
                newsRedisUpdate.setRnid(newsRedis.getRnid());
                newsRedisUpdate.setNdate(newsRedis.getNdate());

                nrRepo.save(newsRedisUpdate);

                int rid = newsRedisUpdate.getNid();
                News news = nRepo.findById(rid).get();

                news.setNid(newsRedisUpdate.getNid());
                news.setNtitle(newsRedisUpdate.getNtitle());
                news.setNdescription(newsRedisUpdate.getNdescription());
                news.setNdetail(newsRedisUpdate.getNdetail());
                news.setNewsimage(newsRedisUpdate.getNewsimage());
                news.setNstatus(newsRedisUpdate.getNstatus());
                news.setNdate(newsRedisUpdate.getNdate());
                news.setNewsCategory(newsRedisUpdate.getNewsCategory());

                nRepo.save(news);

                return "redirect:/news";
            }


        }catch (Exception e){
            System.err.println("News Update Error : " + e);
        }
        return "redirect:/news/update/"+strnid;


    }
    // news Update - end

    // news Search - start
    @JsonFormat(pattern="yyyy-MM-dd")
    @ResponseBody
    @GetMapping("/search/{pageNo}/{stpageSize}/{data}")
    public List<ElasticNews> newsSearch(@PathVariable String data, @PathVariable int pageNo,@PathVariable int stpageSize){

        Page<ElasticNews> pages = enRepo.findBySearch(data,PageRequest.of(pageNo,stpageSize));
        List<ElasticNews> list = pages.getContent();
        List<ElasticNews> list1 = enRepo.find(data);
        searchSize = list1.size();
        return list;

    }
    // news Search - end

    // filter
    @ResponseBody
    @GetMapping("/newsListFilter/{pageNo}/{stpageSize}/{stnStatus}/{ncid}")
    public List<News> newsListFilter(@PathVariable String pageNo, @PathVariable String stpageSize, @PathVariable Boolean stnStatus, @PathVariable String ncid){
        System.out.println("newsListFilter girdi");
        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);

        int id = Integer.parseInt(ncid);

        if(pageSize == -1){
            List<News> lsx = new ArrayList<>();

            List<News> newsList = nRepo.findFilter(stnStatus,id);
            enRepo.deleteAll();
            for (News item : newsList){
                lsx.add(item);
            }
            newsList.forEach(item->{
                ElasticNews elasticNews = new ElasticNews();
                elasticNews.setNid(item.getNid());
                elasticNews.setNtitle(item.getNtitle());
                elasticNews.setNdescription(item.getNdescription());
                elasticNews.setNdetail(item.getNdetail());
                elasticNews.setNewsimage(item.getNewsimage());
                elasticNews.setNstatus(item.getNstatus());
                elasticNews.setNewsCategory(item.getNewsCategory());
                elasticNews.setNdate(item.getNdate());

                enRepo.save(elasticNews);
            });
            return lsx;

        }else {
            Pageable pageable = PageRequest.of(ipageNumber, pageSize);
            List<News> ls = nRepo.findFilterPage(stnStatus,id,pageable);

            enRepo.deleteAll();
            for(News item : ls){
                ElasticNews elasticNews = new ElasticNews();
                elasticNews.setNid(item.getNid());
                elasticNews.setNtitle(item.getNtitle());
                elasticNews.setNdescription(item.getNdescription());
                elasticNews.setNdetail(item.getNdetail());
                elasticNews.setNewsimage(item.getNewsimage());
                elasticNews.setNstatus(item.getNstatus());
                elasticNews.setNewsCategory(item.getNewsCategory());
                elasticNews.setNdate(item.getNdate());

                enRepo.save(elasticNews);
            }
            return ls;
        }

    }



    // filter




    //--------------- mvc - end ---------------


}
