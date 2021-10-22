package com.works.controllers;

import com.works.documents.ElasticSurvey;
import com.works.entities.Options;
import com.works.entities.Survey;
import com.works.entities.redis.SurveyRedis;
import com.works.repositories.OptionsRepository;
import com.works.repositories.SurveyRepository;
import com.works.repositories.elastic.ElasticSearchSurveyRepository;
import com.works.repositories.redis.SurveyRedisRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/survey")
public class SurveyController {

    Integer searchSize;
    final  SurveyRepository sRepo;
    final SurveyRedisRepository srRepo;
    final ElasticSearchSurveyRepository eaRepo;
    final OptionsRepository oRepo;

    public SurveyController(SurveyRepository sRepo, SurveyRedisRepository srRepo, ElasticSearchSurveyRepository eaRepo, OptionsRepository oRepo) {
        this.sRepo = sRepo;
        this.srRepo = srRepo;
        this.eaRepo = eaRepo;
        this.oRepo = oRepo;
    }


    @GetMapping("")
    public String survey(){
        return "survey";
    }

    @GetMapping("/add")
    public String surveyAdd(Model model){
        model.addAttribute("survey", new Survey());
        return "surveyAdd";
    }

    @GetMapping("/detail/{stSaid}")
    public String surveyDetail(@PathVariable String stSaid, Model model ){
        SurveyRedis surveyRedis = srRepo.findById(stSaid).get();
        model.addAttribute("surveyredis",surveyRedis);


        return "surveyDetail";
    }

    @GetMapping("/edit/{stSaid}")
    public String surveyEdit( @PathVariable String stSaid, Model model ) {

        SurveyRedis suRedis = srRepo.findById(stSaid).get();
        model.addAttribute("surveyEdit", suRedis);

        return "surveyEdit";
    }
    @PostMapping("/surveyEdit/{stSaid}")
    public String surveyEdit( @Valid @ModelAttribute("surveyEdit") SurveyRedis surveyRedisEdit, @PathVariable String stSaid, BindingResult bindingResult ){

        SurveyRedis suRedis = srRepo.findById(stSaid).get();
        try {
            if(!bindingResult.hasErrors()){
                surveyRedisEdit.setSaid(suRedis.getSaid());
                surveyRedisEdit.setSid(suRedis.getSid());
                surveyRedisEdit.setOptions(suRedis.getOptions());

                srRepo.save(surveyRedisEdit);

                int id = surveyRedisEdit.getSid();
                Survey survey = sRepo.findById(id).get();
                survey.setSid(surveyRedisEdit.getSid());
                survey.setStitle(surveyRedisEdit.getStitle());
                survey.setOptions(surveyRedisEdit.getOptions());
                sRepo.save(survey);

                return "redirect:/survey";


            }
        }catch (Exception ex){
            System.err.println("Survey Edit Error");
        }
        return "surveyEdit/"+stSaid;
    }


    @GetMapping("/option/{stSaid}")
    public String surveyOption(@PathVariable String stSaid,Model model){
        SurveyRedis surveyRedis = srRepo.findById(stSaid).get();
        model.addAttribute("options",surveyRedis);

        return "surveyOption";
    }


    @PostMapping("/surveyAdd")
    public String surveyAdd(@Valid @ModelAttribute("survey")Survey survey, BindingResult bindingResult){

        try{
        if(!bindingResult.hasErrors()){
            sRepo.save(survey);
            SurveyRedis srRedis = new SurveyRedis();
            srRedis.setSid(survey.getSid());
            srRedis.setStitle(survey.getStitle());
            srRedis.setOptions(survey.getOptions());

            srRepo.save(srRedis);
            return "redirect:/survey";
        }
        }catch (Exception ex){
            System.err.println( "Survey Error: "+ ex);
        }
        return "surveyAdd";
    }

    @ResponseBody
    @GetMapping("/surveyList/{pageNo}/{stpageSize}")
    public List<SurveyRedis> surveyList(@PathVariable String pageNo, @PathVariable String stpageSize) {
        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);

        if (pageSize == -1) {
            List<SurveyRedis> lsx = new ArrayList<>();
            Iterable<SurveyRedis> page = srRepo.findAll();
            List<Survey> srList = sRepo.findAll();
            eaRepo.deleteAll();
            for (SurveyRedis item : page) {
                lsx.add(item);
            }
            srList.forEach(item -> {
                ElasticSurvey elasticSurvey = new ElasticSurvey();
                elasticSurvey.setSid(item.getSid());
                elasticSurvey.setStitle(item.getStitle());
                elasticSurvey.setOptions(item.getOptions());

                eaRepo.save(elasticSurvey);


            });
            Collections.reverse(lsx);
            return lsx;
        } else {
            Pageable pageable = PageRequest.of(ipageNumber, pageSize);
            Slice<SurveyRedis> pageList = srRepo.findByOrderBySidDesc(pageable);
            List<SurveyRedis> ls = pageList.getContent();
            List<Survey> svList = sRepo.findAll();
            eaRepo.deleteAll();

            for (Survey item : svList) {
                ElasticSurvey elasticSurvey = new ElasticSurvey();
                elasticSurvey.setSid(item.getSid());
                elasticSurvey.setStitle(item.getStitle());
                elasticSurvey.setOptions(item.getOptions());

                eaRepo.save(elasticSurvey);
            }
            return ls;
        }
    }


    @ResponseBody
    @GetMapping("/delete/{straid}")
    public String surveyDelete(@PathVariable String straid) {
        try {

            // redis delete - start
            SurveyRedis surveyRedis = srRepo.findById(straid).get();

            int id = surveyRedis.getSid();
            srRepo.delete(surveyRedis);
            // redis delete - end

            // db delete - start
            sRepo.deleteById(id);
            // db delete - end

        } catch (Exception ex) {
            System.err.println("Survey Delete Error : " + ex);
        }
        return "survey";

    }


    @GetMapping("/optionDelete/{straid}/{stoid}")
    public String optionDelete(@PathVariable String straid, @PathVariable String stoid) {

        SurveyRedis redis = new SurveyRedis();
        int id = Integer.parseInt(stoid);
        try {
            // redis delete - start

            SurveyRedis surveyRedis = srRepo.findById(straid).get();

            List<Options> options = surveyRedis.getOptions();
            Integer oid = options.get(id).getOid();
            Integer sid = surveyRedis.getSid();
            Survey survey = sRepo.findById(sid).get();

            options.remove(id);
            redis.setOptions(options);
            survey.setOptions(options);
            redis.setSid(surveyRedis.getSid());
            redis.setStitle(surveyRedis.getStitle());
            redis.setSaid(surveyRedis.getSaid());
            srRepo.deleteById(surveyRedis.getSaid());
            srRepo.save(redis);
            sRepo.saveAndFlush(survey);

            surveyRedis.setOptions(options);
            oRepo.deleteById(oid);
        } catch (Exception ex) {
            System.err.println("Survey Delete Error : " + ex);
        }
        return "redirect:/survey/detail/"+straid;

    }

    @ResponseBody
    @GetMapping("/search/{pageNo}/{stpageSize}/{data}")
    public List<ElasticSurvey> surveySearch(@PathVariable String data, @PathVariable int pageNo, @PathVariable int stpageSize){

        Page<ElasticSurvey> pages = eaRepo.findBySearch(data,PageRequest.of(pageNo,stpageSize));
        List<ElasticSurvey> list = pages.getContent();
        List<ElasticSurvey> list1 = eaRepo.find(data);
        searchSize = list1.size();
        System.out.println(list);
        return list ;


    }


    @PostMapping("/detailAdd/{stSaid}")
    public String optionsAdd(@PathVariable String stSaid, @Valid @ModelAttribute("options") Options options,  BindingResult bindingResult){

        SurveyRedis surveyRedis = srRepo.findById(stSaid).get();
        Survey survey = sRepo.findById(surveyRedis.getSid()).get();

        if(!bindingResult.hasErrors()){

            Options options1 = oRepo.save(options);
            List<Options> lsx = surveyRedis.getOptions();
            lsx.add(options1);
            surveyRedis.setOptions(lsx);

            survey.setOptions(lsx);
            sRepo.save(survey);

            srRepo.save(surveyRedis);

            return "redirect:/survey/detail/"+stSaid;
        }
        return "surveyOption";
    }


    @GetMapping("/optionList/{stSaid}")
    public List<Options> optionList(@PathVariable String stSaid, @Valid @ModelAttribute("options") Options options,  BindingResult bindingResult) {

        SurveyRedis surveyRedis = srRepo.findById(stSaid).get();
        Survey survey = sRepo.findById(surveyRedis.getSid()).get();

        List<Options> lsx = new ArrayList<>();
        if (!bindingResult.hasErrors()) {

        lsx = surveyRedis.getOptions();
            System.out.println("herşey yusuf için:"+ lsx);





        }
        return lsx;

    }



    @ResponseBody
    @GetMapping("/surveyList/pageCount/{stpageSize}/{stPageStatus}")
    public Integer pageCount(@PathVariable String stpageSize,@PathVariable String stPageStatus) {
        Integer pageStatus = Integer.parseInt(stPageStatus);
        long dataCount;
        if (pageStatus == 1) {
            dataCount = srRepo.count();
        }
        else{
            dataCount = searchSize;

        }
        double totalPageCount = Math.ceil((double)dataCount/Double.parseDouble(stpageSize));
        int pageCount = (int) totalPageCount;
        System.out.println("PageCount : " + pageCount);
        return pageCount;
    }

}
