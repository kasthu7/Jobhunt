package com.job_hunting.controller;

import com.job_hunting.dto.ChangePasswordDto;
import com.job_hunting.responce.GeneralResponse;
import com.job_hunting.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePasswordController {

    @Autowired
    private UserService userService;


    @PostMapping("/updatePassword")
    public GeneralResponse updatePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        GeneralResponse response = new GeneralResponse();
        try {
            return userService.updatePasswordById(changePasswordDto);
        } catch (Exception ex) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Sorry Something Wrong Happened.");
            return response;
        }
    }


}
