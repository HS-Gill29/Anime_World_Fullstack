package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Anime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcAnimeDao implements AnimeDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcAnimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean animeExists(Anime anime) {
        String sql = "SELECT COUNT(*) FROM anime WHERE title = ?";
        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class, anime.getTitle());

            return count > 0;
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage(), e);
        }

    }

    @Override
    public int getAnimeId(String title) {
        String sql = "SELECT anime_id FROM anime WHERE title = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, title);
        } catch (DataAccessException e) {
            throw new DaoException("Unable to retrieve anime ID for title: " + title, e);
        }
    }

//    private Anime mapRowToAnime(SqlRowSet rs){
//        Anime anime = new Anime();
//        anime.setTitle(rs.getString("anime_title"));
//        anime.setImgUrl(rs.getString("img_url"));
//        anime.setDuration(rs.getString("duration"));
//        anime.setEpisodes(rs.getInt("episodes"));
//        anime.setStudioName(rs.getString("studio_name"));
//        anime.setStudioUrl(rs.getString("studio_url"));
//        anime.setGenres(rs.getString("genres"));
//        anime.setBackground(rs.getString("background"));
//        anime.setSynopsis(rs.getString("synopsis"));
//
//        return anime;
//    }

}
