package com.techelevator.dao;

import com.techelevator.model.Anime;

public interface AnimeDao {

    boolean animeExists(String title);
    int getAnimeId(String title);

}
