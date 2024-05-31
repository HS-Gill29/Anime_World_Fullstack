package com.techelevator.dao;

import com.techelevator.dto.AnimeReviewDto;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Anime;
import com.techelevator.model.Review;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcReviewDao implements ReviewDao {
    private final JdbcTemplate jdbcTemplate;
    private final JdbcAnimeDao animeDao;

    public JdbcReviewDao(JdbcTemplate jdbcTemplate, JdbcAnimeDao animeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.animeDao = animeDao;
    }

    @Override
    public void save(AnimeReviewDto animeReview, int userId) {
        Anime anime = animeReview.getAnime();
        Review review = animeReview.getReview();

        String sql = "INSERT INTO review (user_id, anime_id, rating, review_text) VALUES (?, ?, ?, ?)";

        String animeSql = "INSERT INTO anime (title, img_url, duration, episodes," +
                "studio_name, studio_url, genres, background, synopsis)" +
                "VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            boolean animeExists = animeDao.animeExists(anime);

            if (!animeExists) {

                jdbcTemplate.update(animeSql, anime.getTitle(), anime.getImgUrl(), anime.getDuration(),
                        anime.getEpisodes(), anime.getStudioName(), anime.getStudioUrl(), anime.getGenres(),
                        anime.getBackground(), anime.getSynopsis());
            }

            int animeId = animeDao.getAnimeId(anime.getTitle());

            jdbcTemplate.update(sql, userId, animeId, review.getRating(), review.getReviewText());

        } catch (DataAccessException e) {
            throw new DaoException("Failed to save review: " + e.getMessage(), e);
        }
    }


    @Override
    public List<Review> getReview(int userId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "select * from review where user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId);
            while (results.next()){
                reviews.add(mapRowToReview(results));
            }

        } catch (DataIntegrityViolationException e){
            throw new DaoException(e.getMessage());
        }
        return reviews;
    }

    @Override
    public void update(Review review, int reviewId) {
        String sql = "UPDATE review SET rating = ?, review_text = ? WHERE review_id = ?";
        try {
            jdbcTemplate.update(sql,review.getRating(),review.getReviewText(),reviewId);
        } catch (DataAccessException e) {
            throw new DaoException("Failed to update review: " + e.getMessage(), e);
        }
    }


    @Override
    public void deleteById(int reviewId) {
        String sql = "DELETE FROM review WHERE review_id = ?";
        try {
            jdbcTemplate.update(sql, reviewId);
        } catch (DataAccessException e) {
            throw new DaoException("Failed to delete review: " + e.getMessage(), e);
        }
    }
    private Review mapRowToReview(SqlRowSet rs) {
        Review review = new Review();
        review.setReviewId(rs.getInt("review_id"));
        review.setUserId(rs.getInt("user_id"));
        ;
        return review;
    }

}
