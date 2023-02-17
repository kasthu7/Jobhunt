package com.job_hunting.services.user;

import com.job_hunting.dto.ChangePasswordDto;
import com.job_hunting.dto.SignupRequest;
import com.job_hunting.dto.UserDto;
import com.job_hunting.entity.User;
import com.job_hunting.repo.UserRepo;
import com.job_hunting.responce.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserDto createUser(SignupRequest signupRequest) throws Exception {
        User user = new User(signupRequest.getEmail(), new BCryptPasswordEncoder().encode(signupRequest.getPassword()), signupRequest.getName());
        user = userRepo.save(user);
        if (user == null)
            return  null;

        return user.mapUsertoUserDto();
    }


    public Boolean hasUserWithEmail(String email) {
        return userRepo.findFirstByEmail(email) != null;
    }

    @Override
    public UserDto getUser(Long userId) {
        UserDto userDto = null;
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isPresent()){
            userDto = optionalUser.get().mapUsertoUserDto();
        }
        return userDto;
    }

    public GeneralResponse updateUser(UserDto userDto)  {
        GeneralResponse response = new GeneralResponse();
        User user = null;
            Optional<User> userOptional = userRepo.findById(userDto.getId());
            if (userOptional.isPresent()) {
                user = userOptional.get();

                user.setName(userDto.getName());

                userRepo.save(user);
                response.setMessage("User Updated Successfully");
                response.setStatus(HttpStatus.CREATED);
                return response;
            } else {
                response.setStatus(HttpStatus.NOT_ACCEPTABLE);
                response.setMessage("User Not Found");
                return response;
            }

    }

    @Override
    public GeneralResponse updatePasswordById(ChangePasswordDto changePasswordDto) {
        GeneralResponse response=new GeneralResponse();
        User user = null;
        try {
            Optional<User> userOptional = userRepo.findById(changePasswordDto.getId());
            if (userOptional.isPresent()) {
                user = userOptional.get();
                if (this.bCryptPasswordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())){
                    user.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
                    userRepo.save(user);
                    response.setStatus(HttpStatus.OK);
                    response.setMessage("Changed Successfully!");
                }else {
                    response.setStatus(HttpStatus.NOT_ACCEPTABLE);
                    response.setMessage("Password is incorrect!");
                }
                return response;
            } else {
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("User Not Found");
                return response;
            }
            }catch (Exception e){
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Unable to process");
            return response;
        }
    }
}
