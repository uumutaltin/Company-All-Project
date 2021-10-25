package com.works.dto;

import com.works.entities.restentity.UserLike;
import com.works.repositories.LikeRepository;
import com.works.utils.ERest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LikeDto {
    final LikeRepository lRepo;

    public LikeDto(LikeRepository lRepo) {
        this.lRepo = lRepo;
    }

    public Map<ERest,Object> likeAdd(Integer productCount ,Integer point ,  Integer cid){
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            UserLike userLike = new UserLike();
            userLike.setCustomerid(cid);
            userLike.setUserpoint(point);
            userLike.setProductid(productCount);

            UserLike l = lRepo.save(userLike);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Ekleme başarılı");
            hm.put(ERest.result, l );
        }catch (Exception ex) {
            hm.put(ERest.status, false);
            hm.put(ERest.message,"Ekleme sırasında hata oluştu!");
        }
        return hm;
    }
}
