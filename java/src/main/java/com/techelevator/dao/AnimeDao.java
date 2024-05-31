package com.techelevator.dao;

import com.techelevator.model.Anime;

public interface AnimeDao {

    boolean animeExists(Anime anime);
    int getAnimeId(String title);

}
