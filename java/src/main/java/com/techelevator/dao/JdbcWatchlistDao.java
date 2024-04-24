package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Watchlist;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcWatchlistDao implements WatchlistDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcWatchlistDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addToWatchlist(Watchlist watchlist, int userId) {
        String sql = "INSERT INTO watchlist (user_id, anime_title, img_url, duration, episodes, studio_name, " +
                "studio_url, genres, background, synopsis) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            if(userId != 0) {
                jdbcTemplate.update(sql, userId, watchlist.getAnimeTitle(), watchlist.getImgUrl(),
                        watchlist.getDuration(), watchlist.getEpisodes(), watchlist.getStudioName(),
                        watchlist.getStudioUrl(), watchlist.getGenres(), watchlist.getBackground(),
                        watchlist.getSynopsis());
            }
        } catch (DataAccessException e) {
            throw new DaoException("Unable to add to watchlist: " + e.getMessage(), e);
        }
    }


    @Override
    public void removeFromWatchlist(int userId, int watchlistId) {
        String sql = "DELETE FROM watchlist WHERE user_id = ? AND watchlist_id = ? ";
        try {
            jdbcTemplate.update(sql, userId, watchlistId);
        } catch (DataAccessException e) {
            throw new DaoException("Unable to remove from watchlist: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Watchlist> getWatchlistByUserId(int userId) {
        List<Watchlist> watchlist = new ArrayList<>();
        String sql = "SELECT * FROM watchlist WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                watchlist.add(mapRowToWatchlist(results));
            }
        } catch (DataAccessException e) {
            throw new DaoException("Unable to retrieve watchlist: " + e.getMessage(), e);
        }
        return watchlist;
    }
    private Watchlist mapRowToWatchlist(SqlRowSet rs) {
        Watchlist watchlist = new Watchlist();
        watchlist.setWatchlistId(rs.getInt("watchlist_id"));
        watchlist.setUserId(rs.getInt("user_id"));
        watchlist.setAnimeTitle(rs.getString("anime_title"));
        watchlist.setImgUrl(rs.getString("img_url"));
        watchlist.setDuration(rs.getString("duration"));
        watchlist.setEpisodes(rs.getInt("episodes"));
        watchlist.setStudioName(rs.getString("studio_name"));
        watchlist.setStudioUrl(rs.getString("studio_url"));
        watchlist.setGenres(rs.getString("genres"));
        watchlist.setBackground(rs.getString("background"));
        watchlist.setSynopsis(rs.getString("synopsis"));
        return watchlist;
    }

}
