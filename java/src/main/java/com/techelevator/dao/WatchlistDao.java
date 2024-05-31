package com.techelevator.dao;

import com.techelevator.dto.AnimeWatchlistDto;
import com.techelevator.model.Anime;
import com.techelevator.model.Watchlist;

import java.util.List;

public interface WatchlistDao {
    void addToWatchlist(Anime anime, int userId);  // Adds an anime to a user's watchlist
    void removeFromWatchlist(int userId, int watchlistId); // Removes an anime from a user's watchlist
    List<AnimeWatchlistDto> getAnimeByUserId(int userId); // Retrieves all anime in a user's watchlist
}

