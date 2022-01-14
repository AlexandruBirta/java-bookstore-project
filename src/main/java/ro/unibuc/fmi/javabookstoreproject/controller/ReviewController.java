package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.javabookstoreproject.api.ReviewApi;
import ro.unibuc.fmi.javabookstoreproject.model.Review;
import ro.unibuc.fmi.javabookstoreproject.service.ReviewService;

@RestController
public class ReviewController implements ReviewApi {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public void makeReview(Review review) {
        reviewService.makeReview(review);
    }

}
