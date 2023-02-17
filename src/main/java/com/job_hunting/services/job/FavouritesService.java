package com.job_hunting.services.job;

import com.job_hunting.dto.FavouritesDto;
import com.job_hunting.responce.GeneralResponse;

import java.util.List;

public interface FavouritesService {

    GeneralResponse addFavorites(FavouritesDto jobDto);

    List<String> getAllFavorites(Long userId);

    void deleteFavorite(String id);

}
