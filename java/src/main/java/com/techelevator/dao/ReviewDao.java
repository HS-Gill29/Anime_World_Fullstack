package com.techelevator.dao;

import com.techelevator.dto.AnimeReviewDto;
import com.techelevator.model.Review;

import java.util.List;

public interface ReviewDao {
    void save(AnimeReviewDto animeReview, int userId); // Can handle both adding and updating a review
    List<Review> getReview(int userId);
    void update(Review review, int reviewId);
    void deleteById(int reviewId);
}
