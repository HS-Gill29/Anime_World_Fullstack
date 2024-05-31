package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Anime;
import com.techelevator.model.Watchlist;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import java.util.List;


@Component
public class JdbcWatchlistDao implements WatchlistDao {
    private final JdbcTemplate jdbcTemplate;
    private final JdbcAnimeDao animeDao;

    public JdbcWatchlistDao(JdbcTemplate jdbcTemplate, JdbcAnimeDao animeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.animeDao = animeDao;
    }

    @Override
    public void addToWatchlist(Anime anime, int userId) {
        String sql = "INSERT INTO watchlist (user_id, anime_id) VALUES (?, ?)";

        String animeSql = "INSERT INTO anime (title, img_url, duration, episodes," +
                "studio_name, studio_url, genres, background, synopsis)" +
                "VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        boolean animeExists = animeDao.animeExists(anime);

        try {
            if (!animeExists) {
                jdbcTemplate.update(animeSql, anime.getTitle(), anime.getImgUrl(), anime.getDuration(),
                        anime.getEpisodes(), anime.getStudioName(), anime.getStudioUrl(), anime.getGenres(),
                        anime.getBackground(), anime.getSynopsis());
            }
            int animeId = animeDao.getAnimeId(anime.getTitle());

            jdbcTemplate.update(sql, userId, animeId);

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
    public List<Anime> getAnimeByUserId(int userId) {
        List<Anime> animeList = new ArrayList<>();
        String sql = "SELECT a.* FROM anime a " +
                "JOIN watchlist w ON a.anime_id = w.anime_id " +
                "WHERE w.user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Anime anime = mapRowToAnime(results);
                animeList.add(anime);
            }
        } catch (DataAccessException e) {
            throw new DaoException("Unable to retrieve anime list for user ID: " + userId, e);
        }
        return animeList;
    }

    private Anime mapRowToAnime(SqlRowSet rs) {
        Anime anime = new Anime();
        anime.setAnimeId(rs.getInt("anime_id"));
        anime.setTitle(rs.getString("title"));
        anime.setImgUrl(rs.getString("img_url"));
        anime.setDuration(rs.getString("duration"));
        anime.setEpisodes(rs.getInt("episodes"));
        anime.setStudioName(rs.getString("studio_name"));
        anime.setStudioUrl(rs.getString("studio_url"));
        anime.setGenres(rs.getString("genres"));
        anime.setBackground(rs.getString("background"));
        anime.setSynopsis(rs.getString("synopsis"));
        return anime;
    }

    private Watchlist mapRowToWatchlist(SqlRowSet rs) {
        Watchlist watchlist = new Watchlist();
        watchlist.setWatchlistId(rs.getInt("watchlist_id"));
        watchlist.setUserId(rs.getInt("user_id"));
        return watchlist;
    }


}
