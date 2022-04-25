package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import ro.unibuc.fmi.javabookstoreproject.api.ReviewApi;
import ro.unibuc.fmi.javabookstoreproject.model.Review;
import ro.unibuc.fmi.javabookstoreproject.service.ReviewService;

import javax.validation.Valid;

@Controller
public class ReviewController implements ReviewApi {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public String makeReview(@ModelAttribute @Valid Review review) {
        reviewService.makeReview(review);
        return "create_review.html";
    }

    @Override
    public String makeReview(Model model) {
        model.addAttribute("review", new Review());
        return "create_review.html";
    }

}