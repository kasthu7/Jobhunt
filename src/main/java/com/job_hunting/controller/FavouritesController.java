package com.job_hunting.controller;

import com.job_hunting.dto.FavouritesDto;
import com.job_hunting.responce.GeneralResponse;
import com.job_hunting.services.job.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourites")
public class FavouritesController {

    @Autowired
    private FavouritesService favouritesService;

    @PostMapping("")
    public GeneralResponse addFavorite(@RequestBody FavouritesDto favouritesDto) {
        GeneralResponse response = new GeneralResponse();
        try {
            return favouritesService.addFavorites(favouritesDto);
        } catch (Exception ex) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Sorry Something Wrong Happened.");
            return response;
        }
    }

    @GetMapping("{userId}")
    public GeneralResponse getAllFavorite(@PathVariable Long userId) {
        GeneralResponse response = new GeneralResponse();
        try {
            response.setData(favouritesService.getAllFavorites(userId));
            response.setStatus(HttpStatus.OK);
            return response;
        } catch (Exception ex) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Sorry Something Wrong Happened.");
            return response;
        }
    }

    @DeleteMapping("{id}")
    public GeneralResponse deleteFavorite(@PathVariable String id) {
        GeneralResponse response = new GeneralResponse();
        try {
            favouritesService.deleteFavorite(id);
            response.setStatus(HttpStatus.OK);
            return response;
        } catch (Exception ex) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Sorry Something Wrong Happened.");
            return response;
        }
    }
}
