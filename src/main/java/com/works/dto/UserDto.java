package com.works.dto;

import com.works.config.Config;
import com.works.entities.Users;
import com.works.repositories.UserRepository;
import com.works.services.UserService;
import com.works.utils.ERest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserDto {

    final UserRepository uRepo;
    final UserService uService;
    public UserDto(UserRepository uRepo, UserService uService) {
        this.uRepo = uRepo;
        this.uService = uService;
    }

    // usersList - start
    public Map<ERest,Object> userList(String pageNumber){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        try {
            int ipageNumber = Integer.parseInt(pageNumber);
            Pageable pageable = PageRequest.of(ipageNumber, Config.pageSize);
            List<Users> pageList = uRepo.findByOrderByUidAsc(pageable);
            Long totalcount = uRepo.count();
            hm.put(ERest.status,true);
            hm.put(ERest.message, "Kullanıcı Listeleme işlemi başarılı");
            hm.put(ERest.totalSize,totalcount);
            hm.put(ERest.result, pageList);
            hm.put(ERest.pageStatus, (Config.pageSize * ipageNumber) + " - " + Config.pageSize);
        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Kullanıcı Listeleme işlemi sırasında hata oluştu!");
        }
        return hm;
    }
    // usersList - end

    // userAdd - start
    public Map<ERest,Object> userAdd( Users user){
        Map<ERest, Object> hm = new LinkedHashMap<>();
        String uPassword = uService.encoder().encode(user.getPassword());
        try {
            user.setPassword(uPassword);
            Users users = uRepo.save(user);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "User Ekleme başarılı");
            hm.put(ERest.result, users );
        }catch (Exception ex) {
            hm.put(ERest.status, false);
            if (ex.toString().contains("constraint")) {
                hm.put(ERest.message, "Bu Bilgiler ile daha önce kayıt yapılmış");
            }
        }
        return hm;
    }
    // userAdd - end

    // updateUser - start
    public Map<ERest, Object> updateUser( Users user) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        String uPassword = uService.encoder().encode(user.getPassword());
        user.setPassword(uPassword);
        if( user.getUid() != null ){

            Optional<Users> oUser = uRepo.findById( user.getUid());
            if( oUser.isPresent() ) {
                try {
                    uRepo.saveAndFlush( user );
                    hm.put(ERest.status, true);
                    hm.put(ERest.message, "Kullanıı Güncelleme başarılı");
                    hm.put(ERest.result, user);
                }catch (Exception ex){
                    hm.put(ERest.status, false);
                    if (ex.toString().contains("constraint")) {
                        hm.put(ERest.message, "Bu Bilgiler ile daha önce kayıt yapılmış");
                    }
                }
            }else {
                hm.put(ERest.status, false);
                hm.put(ERest.message, "Kullanıcı Güncelleme işlemi sırasında hata oluştu!");
                hm.put(ERest.result, user);
            }
        }else {
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Kullanıcı Güncelleme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, user);
        }
        return hm;
    }
    // updateUser - end

    // deleteUser - start
    public Map<ERest, Object> deleteUser( String stUid ) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            int uid = Integer.parseInt(stUid);
            uRepo.deleteById(uid);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Kullanıcı Silme başarılı");
            hm.put(ERest.result, stUid);
        }catch (Exception ex){
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Kullanıcı Silme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, stUid);
        }
        return hm;
    }
    // deleteUser - end


}
