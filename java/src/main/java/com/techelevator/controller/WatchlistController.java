package com.techelevator.controller;

import com.techelevator.dao.JdbcUserDao;
import com.techelevator.dao.JdbcWatchlistDao;
import com.techelevator.model.User;
import com.techelevator.model.Watchlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/watchlist")
@PreAuthorize("isAuthenticated()")
public class WatchlistController {

    private final JdbcUserDao userDao;
    private final JdbcWatchlistDao watchlistDao;

    @Autowired
    public WatchlistController(JdbcUserDao userDao, JdbcWatchlistDao watchlistDao) {
        this.userDao = userDao;
        this.watchlistDao = watchlistDao;
    }

    // Watchlist endpoints
    @PostMapping
    public void addToWatchlist(@Valid Principal principal, @RequestBody Watchlist watchlist) {
        User user = userDao.getUserByUsername(principal.getName());
            watchlistDao.addToWatchlist(watchlist,user.getId());
    }

    @GetMapping
    public List<Watchlist> getWatchlist(@Valid Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
       return watchlistDao.getWatchlistByUserId(user.getId());
    }

    @DeleteMapping("/{watchlistId}")
    public void removeFromWatchlist(@Valid Principal principal,@PathVariable int watchlistId) {
        User user = userDao.getUserByUsername(principal.getName());
          watchlistDao.removeFromWatchlist(user.getId(), watchlistId);
}
}