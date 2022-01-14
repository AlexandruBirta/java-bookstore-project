package ro.unibuc.fmi.javabookstoreproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;
import ro.unibuc.fmi.javabookstoreproject.model.Review;
import ro.unibuc.fmi.javabookstoreproject.repository.AccountRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.BookRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.ReviewRepository;

import java.util.List;

@Slf4j
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AccountRepository accountRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, AccountRepository accountRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.accountRepository = accountRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void makeReview(Review review) {

        if (!accountRepository.existsById(review.getAccount().getId())) {
            throw new ApiException(ExceptionStatus.ACCOUNT_NOT_FOUND, String.valueOf(review.getAccount().getId()));
        }

        if (!bookRepository.existsById(review.getBook().getId())) {
            throw new ApiException(ExceptionStatus.BOOK_NOT_FOUND, String.valueOf(review.getBook().getId()));
        }

        List<Review> repoReviews = reviewRepository.findAll();

        for (Review repoReview : repoReviews) {
            if (repoReview.getTitle().equals(review.getTitle()) &&
                    repoReview.getContent().equals(review.getContent()) &&
                    repoReview.getRating().equals(review.getRating())) {
                throw new ApiException(ExceptionStatus.REVIEW_ALREADY_EXISTS, repoReview.getTitle());
            }
        }

        reviewRepository.save(review);
        log.info("Created " + review);

    }

}
