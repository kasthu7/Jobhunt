package com.job_hunting.services.job;

import com.job_hunting.dto.FavouritesDto;
import com.job_hunting.entity.Favourites;
import com.job_hunting.entity.User;
import com.job_hunting.repo.FavouritesRepo;
import com.job_hunting.repo.UserRepo;
import com.job_hunting.responce.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouritesServiceImpl implements FavouritesService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FavouritesRepo favouritesRepo;

    @Override
    public GeneralResponse addFavorites(FavouritesDto favoritesDto) {
        GeneralResponse response = new GeneralResponse();
        User user = null;
        Optional<User> userOptional = userRepo.findById(favoritesDto.getUserid());
        if (userOptional.isPresent()) {
            if(!favouritesRepo.existsByJobIdAndUser(favoritesDto.getJobId(),userOptional.get())) {
                user = userOptional.get();

                Favourites favorites = new Favourites();

                favorites.setJobId(favoritesDto.getJobId());

                favorites.setUser(user);

                favouritesRepo.save(favorites);
                response.setMessage("Favorites Added Successfully");
                response.setStatus(HttpStatus.CREATED);
            }
            else{
                response.setStatus(HttpStatus.NOT_ACCEPTABLE);
                response.setMessage("Already in Favourite List");
            }
        } else {
            response.setStatus(HttpStatus.NOT_ACCEPTABLE);
            response.setMessage("User Not Found");
        }
        return response;
    }

    @Override
    public List<String> getAllFavorites(Long userId){
        return favouritesRepo.findAllByUserId(userId).stream().map(Favourites::getJobId).collect(Collectors.toList());
    }

    @Override
    public void deleteFavorite(String id){
        Optional<Favourites> optionalFavorites = favouritesRepo.findByJobId(id);
        if(optionalFavorites.isPresent()){
            favouritesRepo.delete(optionalFavorites.get());
        }
    }
}
