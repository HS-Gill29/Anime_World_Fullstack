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
    public List<AnimeReviewDto> getReview(int userId) {
        List<AnimeReviewDto> animeReviews = new ArrayList<>();
        String sql = "SELECT r.*, a.* " +
                "FROM review r  JOIN anime a ON r.anime_id = a.anime_id " +
                "WHERE r.user_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Review review = mapRowToReview(results);
                Anime anime = mapRowToAnime(results);

                AnimeReviewDto animeReviewDto = new AnimeReviewDto();

                animeReviewDto.setReview(review);
                animeReviewDto.setAnime(anime);

                animeReviews.add(animeReviewDto);
            }

        } catch (DataAccessException e) {
            throw new DaoException("Failed to retrieve reviews: " + e.getMessage(), e);
        }

        return animeReviews;
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
        review.setAnimeId(rs.getInt("anime_id"));
        review.setRating(rs.getInt("rating"));
        review.setReviewText(rs.getString("review_text"));
        return review;
    }

    private Anime mapRowToAnime(SqlRowSet rs) {
        Anime anime = new Anime();
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

}
