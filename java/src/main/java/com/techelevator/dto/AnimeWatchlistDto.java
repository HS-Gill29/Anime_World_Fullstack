package com.techelevator.dto;

import com.techelevator.model.Anime;
import com.techelevator.model.Watchlist;

public class AnimeWatchlistDto {
    private Anime anime;
    private Watchlist watchlist;

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public Watchlist getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(Watchlist watchlist) {
        this.watchlist = watchlist;
    }
}
