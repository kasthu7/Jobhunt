package com.job_hunting.services.user;


import com.job_hunting.dto.ChangePasswordDto;
import com.job_hunting.dto.SignupRequest;
import com.job_hunting.dto.UserDto;
import com.job_hunting.responce.GeneralResponse;

public interface UserService {

     UserDto createUser(SignupRequest signupRequest) throws Exception;

     Boolean hasUserWithEmail(String email);

     UserDto getUser(Long userId);

     GeneralResponse updateUser(UserDto userDto);

     GeneralResponse updatePasswordById(ChangePasswordDto changePasswordDto);
}
