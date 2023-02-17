package com.job_hunting.repo;

import com.job_hunting.entity.Favourites;
import com.job_hunting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouritesRepo extends JpaRepository<Favourites, Long> {

    boolean existsByJobIdAndUser(String jobId, User user);

    List<Favourites> findAllByUserId(Long userId);

    Optional<Favourites> findByJobId(String id);
}
