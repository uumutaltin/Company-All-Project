package com.works.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.documents.ElasticProduct;
import com.works.entities.Product;
import com.works.entities.ProductCategories;
import com.works.entities.ProductCategory;
import com.works.entities.ProductImages;
import com.works.layers.ProductAddInterLayer;
import com.works.mvcdto.ProductAddDto;
import com.works.repositories.ProductCategoryAddRepository;
import com.works.repositories.ProductCategoryRepository;
import com.works.repositories.ProductImageRepository;
import com.works.repositories.ProductRepository;
import com.works.repositories.elastic.ElasticSearchProductRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {

    final ProductCategoryAddRepository pcaRepo;
    final ProductAddDto pDto ;
    final ProductCategoryRepository pcato;
    final ProductImageRepository piRepo;
    final ProductRepository pRepo ;
    final ElasticSearchProductRepository espRepo;
    Integer searchSize;
    public ProductController(ProductCategoryAddRepository pcaRepo, ProductAddDto pDto, ProductCategoryRepository pcato, ProductImageRepository piRepo, ProductRepository pRepo, ElasticSearchProductRepository espRepo) {
        this.pcaRepo = pcaRepo;
        this.pDto = pDto;
        this.pcato = pcato;
        this.piRepo = piRepo;
        this.pRepo = pRepo;
        this.espRepo = espRepo;
    }

    @GetMapping("")
    public String product(){
        return "productManagement";
    }
    @GetMapping("/category/add")
    public String addProductCategory(Model model){
        model.addAttribute("productCategory", new ProductCategory());
        model.addAttribute("product",new ProductAddInterLayer());
        return "productCategoryAdd";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product ,  @RequestParam("advertimage_file") MultipartFile file , @RequestParam("pcategory") Integer[] categoryArray,BindingResult bindingResult){


        String fileName = null;
        Product productLast = null;
        if (!file.getOriginalFilename().isEmpty()) {
            fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = fileName.substring(fileName.length() - 4, fileName.length());

            String uui = UUID.randomUUID().toString();
            fileName = uui + ext;
            try{

                List<ProductCategory> ls = new ArrayList<>();
                for (int i = 0 ; i<categoryArray.length;i++){
                    ProductCategory productCategory = pcato.findById(categoryArray[i]).get();
                    ls.add(productCategory);
                }
                ProductImages pImage = new ProductImages();
                pImage.setImageName(fileName);
                ProductImages productImages = piRepo.save(pImage);

                List<ProductImages> list = new ArrayList<>();
                list.add(productImages);
                product.setPimages(list);
                product.setPcategories(ls);

                 productLast = pRepo.save(product);


            }catch (Exception e){
                System.out.println("ProductAdd Error : " + e);
            }
            try {
                String UPLOAD_DIR = "src/main/resources/static/uploads/products/"+ productLast.getPid();
                File directory = new File(UPLOAD_DIR);
                directory.mkdir();
                UPLOAD_DIR = UPLOAD_DIR +'/';
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/products";
    }



    @GetMapping("/add")
    public String addProduct(Model model){
        List<ProductCategory> productCategoryList = pcato.findAll();
        model.addAttribute("productCategoryList",productCategoryList);
        return "productAdd";
    }

    @PostMapping("/category/add")
    public String productCategoryAdd(@Valid @ModelAttribute("productCategory") ProductCategory productCategory, BindingResult bindingResult, @RequestParam(value = "pcategory",defaultValue = "-1") Integer category){
        try{
            if (!bindingResult.hasErrors()) {


                if (category == -1){

                    ProductCategory proCategory = pDto.addProductCategory(productCategory);
                    System.out.println(proCategory.getPcid());


                    ProductCategories  prodCategory = new ProductCategories();
                    prodCategory.setPcategoriesname(productCategory.getPcategoryname());
                    prodCategory.setPcsid(proCategory.getPcid());
                    System.out.println(prodCategory.getPcsid());
                    pDto.productCategoryUpdate(prodCategory);

                }
                else{
                    ProductCategory beforeProdCat = pcato.save(productCategory);
                    ProductCategories proCategory = pDto.pCategoryfindbid(category);
                    List<ProductCategory> ls = proCategory.getPcategory();
                    ls.add(beforeProdCat);
                    proCategory.setPcategory(ls);
                    pDto.productCategoryUpdate(proCategory);
                }
                return "redirect:/products/category/add";
            }
        }catch (Exception e){
            System.err.println("ProductCategoryAdd Error : " + e);
        }
        return "redirect:/products/category/add";

    }

    @ResponseBody
    @GetMapping("/category/delete/{stCid}")
    public Boolean productCategoryDelete(@PathVariable String stCid){
        Integer cid =  Integer.parseInt(stCid);;
        try{
            Optional<ProductCategories> productCategories = pcaRepo.findById(cid);
            if (productCategories.isPresent()){
                pcaRepo.deleteById(cid);
            }
            System.out.println("Buradax: " +productCategories);
            pcato.deleteById(cid);
            return true ;
        }catch (Exception e){
          if (e.toString().contains("exception.ConstraintViolationException:")){
              Integer did = pcaRepo.deleteCategory(cid);
              pcato.deleteById(cid);
              return true ;
          }
          else{
              System.err.println("ProductCategoryDelete : " + e);
              return false;
          }
        }
    }

    @ResponseBody
    @GetMapping("/category/list")
    public List<ProductCategories> productCategoryList(){
    List<ProductCategories> productCategories = pcaRepo.findAll();
    return productCategories;
    }

    @ResponseBody
    @PostMapping("/category/update")
    public Boolean productCategoryUpdate(@RequestBody ProductCategory productCategory){
        Integer cid =  productCategory.getPcid();
        try{
            Optional<ProductCategories> productCategories = pcaRepo.findById(cid);
            if (productCategories.isPresent()){
                ProductCategories pCategory = productCategories.get();
                pCategory.setPcategoriesname(productCategory.getPcategoryname());
                pcaRepo.saveAndFlush(pCategory);
            }
            System.out.println("Buradax: " +productCategories);
            productCategory.setPcid(cid);
            pcato.saveAndFlush(productCategory);
            return true ;
        }catch (Exception e){
                System.err.println("ProductCategoryDelete : " + e);
                return false;
        }
    }


    @ResponseBody
    @GetMapping("/productList/{pageNo}/{stpageSize}")
    public List<Product> productList(@PathVariable String pageNo, @PathVariable String stpageSize){
        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);
        if (pageSize == -1) {
            List<Product> page = pRepo.findAll();
            espRepo.deleteAll();
            page.forEach(item -> {
                ElasticProduct elasticProduct = new ElasticProduct();
                elasticProduct.setPid(item.getPid());
                elasticProduct.setPname(item.getPname());
                elasticProduct.setPsdetail(item.getPsdetail());
                elasticProduct.setPdetail(item.getPdetail());
                elasticProduct.setPprice(item.getPprice());
                elasticProduct.setPtype(item.getPtype());
                elasticProduct.setPcampaign(item.getPcampaign());
                elasticProduct.setPcampaignname(item.getPcampaignname());
                elasticProduct.setPcampaigndetail(item.getPcampaigndetail());
                elasticProduct.setPadress(item.getPadress());
                elasticProduct.setPlatitude(item.getPlatitude());
                elasticProduct.setPlongitude(item.getPlongitude());
                elasticProduct.setPimages(item.getPimages());
                elasticProduct.setPcategories(item.getPcategories());

                espRepo.save(elasticProduct);
            });
            return page;
        }else{
            Pageable pageable = PageRequest.of(ipageNumber, pageSize);
            List<Product> productPageablels = pRepo.findByOrderByPidDesc(pageable);
            List<Product> productList = pRepo.findAll();
            espRepo.deleteAll();
            for (Product item : productList){
                ElasticProduct elasticProduct = new ElasticProduct();
                elasticProduct.setPid(item.getPid());
                elasticProduct.setPname(item.getPname());
                elasticProduct.setPsdetail(item.getPsdetail());
                elasticProduct.setPdetail(item.getPdetail());
                elasticProduct.setPprice(item.getPprice());
                elasticProduct.setPtype(item.getPtype());
                elasticProduct.setPcampaign(item.getPcampaign());
                elasticProduct.setPcampaignname(item.getPcampaignname());
                elasticProduct.setPcampaigndetail(item.getPcampaigndetail());
                elasticProduct.setPadress(item.getPadress());
                elasticProduct.setPlatitude(item.getPlatitude());
                elasticProduct.setPlongitude(item.getPlongitude());
                elasticProduct.setPimages(item.getPimages());
                elasticProduct.setPcategories(item.getPcategories());

                espRepo.save(elasticProduct);
            }
            return productPageablels;
        }
    }
    @ResponseBody
    @GetMapping("/productList/pageCount/{stpageSize}/{stPageStatus}")
    public Integer pageCount(@PathVariable String stpageSize,@PathVariable String stPageStatus) {
        Integer pageStatus = Integer.parseInt(stPageStatus);
        long dataCount;
        if (pageStatus == 1) {
            dataCount = pRepo.count();
        }
        else{
            dataCount = searchSize;

        }
        double totalPageCount = Math.ceil((double)dataCount/Double.parseDouble(stpageSize));
        int pageCount = (int) totalPageCount;
        System.out.println("PageCount : " + pageCount);
        return pageCount;
    }
    @JsonFormat(pattern="yyyy-MM-dd")
    @ResponseBody
    @GetMapping("/search/{pageNo}/{stpageSize}/{data}")
    public List<ElasticProduct> newsSearch(@PathVariable String data, @PathVariable int pageNo,@PathVariable int stpageSize){

        Page<ElasticProduct> pages = espRepo.findBySearch(data,PageRequest.of(pageNo,stpageSize));
        List<ElasticProduct> list = pages.getContent();
        List<ElasticProduct> list1 = espRepo.find(data);
        searchSize = list1.size();
        return list;

    }
    @ResponseBody
    @GetMapping("/delete/{id}")
    public String ProductDelete(@PathVariable Integer id){
        try {
            Product product = pRepo.findById(id).get();
            List<ProductImages> productImages = product.getPimages();
            Integer value = pRepo.deleteProducRelationalCategory(id);
            // redis delete - start
            String UPLOAD_DIR = "src/main/resources/static/uploads/products/"+id;
            FileUtils.deleteDirectory(new File(UPLOAD_DIR));
             pRepo.deleteById(id);
        }catch (Exception e){
            System.err.println("News Delete Error : " + e);
            return "0";
        }
        return "products";
    }
    @GetMapping("/update/{id}")
    public String productUpdate(@PathVariable Integer id, Model model){

        List<ProductCategory> productCategoryList = pcato.findAll();
        model.addAttribute("productCategoryList",productCategoryList);
        Product product = pRepo.findById(id).get();
        model.addAttribute("productUpdate",product);
        return "productEdit";
    }
    @PostMapping("/update/{id}")
    public String productUpdate(@Valid @ModelAttribute("productUpdate") Product product ,@RequestParam(value = "pcategory") Integer[] categoryArray,@PathVariable Integer id ,BindingResult bindingResult){

        System.out.println(product.getPname());
        System.out.println(id);
        System.out.println("Burası");
        product.setPid(id);
        String fileName = null;
        Product productLast = null;
        Product product1 = pRepo.findById(id).get();
        System.out.println(product1.getPname());
            try{

                List<ProductCategory> ls = new ArrayList<>();
                System.out.println(categoryArray.length);
                for (int i = 0 ; i<categoryArray.length;i++){
                    System.out.println("Kategori Değeri : " + categoryArray[i]);
                   ProductCategory productCategory = pcato.findById(categoryArray[i]).get();
                   ls.add(productCategory);
                }

              for (int i = 0 ; i<categoryArray.length;i++){
                    System.out.println(ls.get(i).getPcategoryname());
                }

                System.out.println(ls.get(0));
                product.setPimages(product1.getPimages());
                product.setPcategories(ls);


                System.out.println( "Deneme" + product.getPid());;
                productLast = pDto.updateProduct(product);
                System.out.println(productLast.getPid());


            }catch (Exception e){
                System.out.println("ProductAdd Error : " + e);
            }
               try {
                System.out.println( "Deneme" + product.getPid());;
                productLast = pDto.updateProduct(product);
                System.out.println(productLast.getPid());
            }catch (Exception e){
                System.out.println("ProductAdd Error : " + e);
            }
        return "redirect:/products";

    }

    @GetMapping("/images/{id}")
    public String productImage(Model model, @PathVariable Integer id ){
        Product product = pRepo.findById(id).get();
        model.addAttribute("product",product);
        return "productGallery";
    }


    @PostMapping("/add/image/{id}")
    public  String addProductImage(@PathVariable Integer id ,  @RequestParam("advertimage_file") MultipartFile file ){
        String fileName = null;
        Product productLast = null;
        if (!file.getOriginalFilename().isEmpty()) {
            fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = fileName.substring(fileName.length() - 4, fileName.length());

            String uui = UUID.randomUUID().toString();
            fileName = uui + ext;
            try{
            Product product = pRepo.findById(id).get();
                String UPLOAD_DIR = "src/main/resources/static/uploads/products/"+ product.getPid() +'/';
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                ProductImages pImage = new ProductImages();
                pImage.setImageName(fileName);
                ProductImages productImages = piRepo.save(pImage);

                List<ProductImages> imgList = product.getPimages();
                imgList.add(productImages);

                product.setPimages(imgList);

                productLast = pRepo.saveAndFlush(product);


            }catch (Exception e){
                System.err.println("addProductImage : " + e);
            }
        }
        return "redirect:/products/images/" + id ;
    }

    @GetMapping("/delete/image/{prodid}/{imageid}")
    public String deleteProductImage(@PathVariable Integer prodid ,@PathVariable Integer imageid){
        System.out.println("Burada");
        try{
            pRepo.deleteProductRelationaImage(imageid);
            piRepo.deleteById(imageid);
        }catch (Exception e ){
            System.err.println("DeleteProdImage : " + e );
        }
        return "redirect:/products/images/" + prodid ;
    }
}






