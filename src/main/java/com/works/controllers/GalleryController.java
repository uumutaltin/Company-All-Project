package com.works.controllers;

import com.works.entities.Gallery;
import com.works.entities.GalleryImage;
import com.works.entities.projection.GalleryJoinImage;
import com.works.repositories.GalleryImageRepository;
import com.works.repositories.GalleryRepository;
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
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
    final private String UPLOAD_DIR = "src/main/resources/static/uploads/gallery/";
    final GalleryRepository gRepo;
    final GalleryImageRepository giRepo;
    public GalleryController(GalleryRepository gRepo, GalleryImageRepository giRepo ) {
        this.gRepo = gRepo;
        this.giRepo = giRepo;
    }


    //--------------- html - start ---------------
    @GetMapping("")
    public String gallery(Model model) {
        List<Gallery> galleryList = gRepo.findAll();

        galleryList.forEach(item->{

            item.getGalleryImages().forEach(i->{
                i.getGiname();
            });
        });
        model.addAttribute("galleryList",galleryList);
        model.addAttribute("gallery", new Gallery());

        return "gallery";
    }

    @GetMapping("/property/{stgid}")
    public String galleryproperty(Model model, @PathVariable String stgid) {

        int id = Integer.parseInt(stgid);
        List<GalleryJoinImage> galleryList = gRepo.allGalleryInfo(id);
        galleryList.forEach(item->{
            System.out.println("image name : "+ item.getGiname());
            System.out.println("galleryID : " + item.getGid());
        });
        model.addAttribute("gallery",gRepo.findById(id).get());
        model.addAttribute("GalleryImage", new GalleryImage());
        model.addAttribute("galleryList",galleryList);
        return "galleryPropertyAdd";
    }

    @GetMapping("/property/edit/{stgid}/{stgiid}")
    public String gallerypropertyEdit(Model model,@PathVariable String stgid ,@PathVariable String stgiid) {
        int gid = Integer.parseInt(stgid);
        int id = Integer.parseInt(stgiid);
        Gallery gallery = gRepo.findById(gid).get();
        GalleryImage galleryImage = giRepo.findById(id).get();
        model.addAttribute("gallery",gallery);
        model.addAttribute("galleryImage",galleryImage);
        return "galleryPropertyEdit";
    }
    //--------------- html - end ---------------


    //--------------- mvc - start ---------------

    // gallery Add - start
    @PostMapping("/add")
    public String galleryAdd(@Valid @ModelAttribute("gallery")Gallery gallery, BindingResult bindingResult){
        try {
            System.out.println(bindingResult);
            if(!bindingResult.hasErrors()){
                gRepo.save(gallery);
                return "redirect:/gallery/property/"+gallery.getGid();
            }

        }catch (Exception e){
            System.err.println("Gallery Add : " + e);
        }
        return "gallery";
    }
    // gallery Add - end

    // gallery List - start
    @ResponseBody
    @GetMapping("/list")
    public List<Gallery> galleryList(){
        List<Gallery> galleries = gRepo.findAll();
        return galleries;
    }
    // gallery List - end

    // gallery Update - start
    @PostMapping("/galleryUpdate/{stgid}")
    public String galleryUpdate(@Valid @ModelAttribute("gallery")Gallery gallery,BindingResult bindingResult,@PathVariable String stgid){


        try{

            int id = Integer.parseInt(stgid);
            Gallery galleryUpdate = gRepo.findById(id).get();
            if(!bindingResult.hasErrors()){
                galleryUpdate.setGid(id);
                galleryUpdate.setGname(gallery.getGname());
                galleryUpdate.setGdetail(gallery.getGdetail());
                galleryUpdate.setGstatus(gallery.getGstatus());
                //galleryUpdate.setGalleryImages(gallery.getGalleryImages());

                gRepo.save(galleryUpdate);
                return "redirect:/gallery/property/"+galleryUpdate.getGid();
            }


        }catch (Exception e){
            System.err.println("Gallery Update Error : " + e);
        }
        return "galleryPropertyAdd";


    }
    // gallery Update - end

    // gallery image Update - start
    @PostMapping("/imageUpdate/{stgid}/{stgiid}")
    public String galleryImageUpdate(@ModelAttribute("galleryImage")GalleryImage galleryImage,@PathVariable String stgid ,@PathVariable String stgiid){

        try {
            System.out.println("image update try a girdi");
            int id = Integer.parseInt(stgiid);
            int gid = Integer.parseInt(stgid);
            Gallery gallery = gRepo.findById(gid).get();
            GalleryImage galleryImageUpdate = giRepo.findById(id).get();


            galleryImageUpdate.setGiid(id);
            galleryImageUpdate.setGisubtitle(galleryImage.getGisubtitle());

            giRepo.save(galleryImageUpdate);

            return "redirect:/gallery/property/"+gallery.getGid();


        }catch (Exception e){
            System.err.println("Gallery Image Update Error : " + e);
        }
        return "galleryPropertyAdd";
    }
    // gallery image Update - end


    // gallery image Add - start
    @PostMapping("/imageAdd")
    public String galleryImageAdd(@ModelAttribute("GalleryImage") GalleryImage image, @RequestParam("gAll")String galleryID, @RequestParam("galleryFile")MultipartFile file){

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
        try{
            int id = Integer.parseInt(galleryID);
            Gallery gallery = gRepo.findById(id).get();


            image.setGiname(fileName);
            giRepo.save(image);

            gallery.getGalleryImages().add(image);
            gRepo.save(gallery);

            return "redirect:/gallery/property/"+gallery.getGid();


        }catch (Exception e){
            System.err.println("Gallery Image Add Error : " + e);
        }

        return "galleryPropertyAdd";

    }
    // gallery image Add - end

    // gallery Delete - start
    @GetMapping("/delete/{stgid}")
    public String galleryDelete(@PathVariable String stgid){

        try{
            int id = Integer.parseInt(stgid);
            Gallery gallery = gRepo.findById(id).get();
            List<GalleryImage> fileNameUpdate = gallery.getGalleryImages();

            fileNameUpdate.forEach(item ->{
                File fileImage = new File(UPLOAD_DIR + item.getGiname());
                if (fileImage.exists()) {
                    fileImage.delete();
                }
            });

            gRepo.deleteById(id);
        }catch (Exception e){
            System.err.println("Gallery Delete Error : " + e);
        }
        return "redirect:/gallery";


    }
    // gallery Delete - end


    // gallery Image Delete - start
    @GetMapping("/deleteImage/{stgid}/{stgiid}")
    public String galleryImageDelete(@PathVariable String stgid ,@PathVariable String stgiid){
        int giid = Integer.parseInt(stgiid);
        int gid = Integer.parseInt(stgid);
        GalleryImage galleryImage = giRepo.findById(giid).get();
        try{

            String fileNameUpdate = galleryImage.getGiname();
            File fileImage = new File(UPLOAD_DIR + fileNameUpdate);
            if (fileImage.exists()) {
                fileImage.delete();
            }
            giRepo.deleteImage(giid);

            giRepo.deleteById(giid);



        }catch (Exception e){
            System.err.println("Gallery Image Delete Error : " + e);
        }
        return "redirect:/gallery/property/"+gid;





    }
    // gallery Image Delete - end





    //--------------- mvc - end ---------------

}
