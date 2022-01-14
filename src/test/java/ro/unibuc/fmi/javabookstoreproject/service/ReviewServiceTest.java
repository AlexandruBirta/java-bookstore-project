package ro.unibuc.fmi.javabookstoreproject.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.test.context.TestPropertySource;
import ro.unibuc.fmi.javabookstoreproject.model.*;
import ro.unibuc.fmi.javabookstoreproject.repository.AccountRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.BookRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.ReviewRepository;

import java.time.LocalDateTime;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ReviewService reviewService;

    private static Account testAccount;
    private static Author testAuthor;
    private static Publisher testPublisher;
    private static Book testBook;
    private static Review testReview;

    @BeforeAll
    public static void setup() {

        testAccount = Account.builder()
                .id(1L)
                .firstName("Popescu")
                .lastName("Ion")
                .email("ion.pop@email.com")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testAuthor = Author.builder()
                .id(1L)
                .firstName("Harper")
                .lastName("Lee")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testPublisher = Publisher.builder()
                .id(1L)
                .name("Penguin Books")
                .description("Penguin Books was originally a British publishing house. It was co-founded in 1935 by Sir Allen Lane with his brothers Richard and John, as a line of the publishers The Bodley Head, only becoming a separate company the following year.")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();


        testBook = Book.builder()
                .id(1L)
                .name("To Kill a Mockingbird")
                .description("To Kill a Mockingbird has become a classic of modern American literature, winning the Pulitzer Prize. The plot and characters are loosely based on Lee's observations of her family, her neighbors and an event that occurred near her hometown of Monroeville, Alabama, in 1936, when she was ten.")
                .genre(Book.Genre.NOVEL)
                .isbn("063122581-1")
                .author(testAuthor)
                .publisher(testPublisher)
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testReview = Review.builder()
                .account(testAccount)
                .book(testBook)
                .title("My new favourite book")
                .content("I very much liked the book 'To Kill a Mockingbird' by Harper Lee!")
                .rating(Review.Rating.FIVE_STAR)
                .build();

    }

    @Test
    @DisplayName("Given test review, when a review is saved, then it can be verified for existence")
    void makeReviewTest() {

        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(testReview);
        Mockito.when(reviewRepository.getById(testReview.getId())).thenReturn(testReview);
        Mockito.when(accountRepository.existsById(testAccount.getId())).thenReturn(true);
        Mockito.when(bookRepository.existsById(testBook.getId())).thenReturn(true);

        reviewService.makeReview(testReview);
        Review savedReview = reviewRepository.getById(testReview.getId());
        log.info(savedReview.toString());

        Assertions.assertEquals(savedReview, testReview);

    }

}