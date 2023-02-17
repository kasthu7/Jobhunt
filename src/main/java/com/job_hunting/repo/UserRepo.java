package com.job_hunting.repo;

import com.job_hunting.dto.UserDto;
import com.job_hunting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findFirstByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

}
