package com.techelevator.controller;

import com.techelevator.dao.JdbcReviewDao;
import com.techelevator.dao.JdbcUserDao;
import com.techelevator.model.Review;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/review")
@PreAuthorize("isAuthenticated()")
public class ReviewController {

    private final JdbcReviewDao reviewDao;
    private final JdbcUserDao userDao;


    @Autowired
    public ReviewController(JdbcReviewDao reviewDao, JdbcUserDao userDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }

    // Get all reviews for a specific anime
    @GetMapping
    public List<Review> getReview(Principal principal) {
       return reviewDao.getReview(userDao.getUserByUsername(principal.getName()).getId());
    }

    // Save or update a review
    @PostMapping
    public void save(Principal principal, @RequestBody Review review) {
        User user = userDao.getUserByUsername(principal.getName());
       reviewDao.save(review,user.getId());
    }

    // Update a review
    @PutMapping("/{reviewId}")
    public void updateReview(@PathVariable int reviewId, @RequestBody Review review) {
        reviewDao.update(review,reviewId);
    }

    // Delete a review
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable int reviewId) {
        reviewDao.deleteById(reviewId);
    }
}

