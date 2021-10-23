package com.works.restcontrollers;

import com.works.dto.UserDto;
import com.works.entities.Users;
import com.works.utils.ERest;
import com.works.utils.Util;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    final UserDto uDto;
    final Util util;
    public UserRestController(UserDto uDto, Util util) {
        this.uDto = uDto;
        this.util = util;
    }
    // List - start
    @GetMapping("/list/{pageNo}")
    public Map<ERest, Object> list(@PathVariable String pageNo){
        return  uDto.userList(pageNo);
    }
    // List - end

    // Add - start
    @PostMapping("/add")
    public Map<ERest, Object> add(@RequestBody @Valid Users user, BindingResult bindingResult) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            hm.put(ERest.status, false);
            hm.put(ERest.errors, util.errors(bindingResult));
            return hm;
        } else {
            return uDto.userAdd(user);
        }
    }
    // Add - end

    // Update - start
    @PutMapping("/update")
    public Map<ERest, Object> update(@RequestBody @Valid Users user, BindingResult bindingResult){

        Map<ERest, Object> hm = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            hm.put(ERest.status, false);
            hm.put(ERest.errors, util.errors(bindingResult));
            return hm;
        } else {
            return  uDto.updateUser(user);
        }
    }
    // Update - end

    // Delete - start
    @DeleteMapping("/delete/{uid}")
    public Map<ERest, Object> delete( @PathVariable String uid){
        return uDto.deleteUser(uid);
    }
    // Delete - end

    // Logout - start
    @GetMapping("/logout")
    public Map<ERest,Object> logout(){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        hm.put(ERest.status,true);
        hm.put(ERest.message,"Çıkış yapıldı.");
        return hm;
    }
    // Logout - end


}
