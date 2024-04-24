package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Review;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcReviewDao implements ReviewDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Review review, int userId) {
        String sql = "INSERT INTO review (user_id, anime_title, rating, review_text, img_url, duration, " +
                "episodes, studio_name, studio_url, genres, background, synopsis) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            if(userId != 0) {
                jdbcTemplate.update(sql, userId, review.getAnimeTitle(), review.getRating(),
                        review.getReviewText(), review.getImgUrl(), review.getDuration(),
                        review.getEpisodes(), review.getStudioName(), review.getStudioUrl(),
                        review.getGenres(), review.getBackground(), review.getSynopsis());
            }
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
        review.setAnimeTitle(rs.getString("anime_title"));
        review.setRating(rs.getInt("rating"));
        review.setReviewText(rs.getString("review_text"));
        review.setImgUrl(rs.getString("img_url"));
        review.setDuration(rs.getString("duration"));
        review.setEpisodes(rs.getInt("episodes"));
        review.setStudioName(rs.getString("studio_name"));
        review.setStudioUrl(rs.getString("studio_url"));
        review.setGenres(rs.getString("genres"));
        review.setBackground(rs.getString("background"));
        review.setSynopsis(rs.getString("synopsis"));
        return review;
    }

}
