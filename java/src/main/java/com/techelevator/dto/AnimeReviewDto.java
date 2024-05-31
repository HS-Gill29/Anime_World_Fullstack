package com.techelevator.dto;

import com.techelevator.model.Anime;
import com.techelevator.model.Review;

public class AnimeReviewDto {

    private Anime anime;
    private Review review;

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
