package ro.unibuc.fmi.javabookstoreproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.unibuc.fmi.javabookstoreproject.model.*;
import ro.unibuc.fmi.javabookstoreproject.repository.AccountRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.BookRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.ReviewRepository;
import ro.unibuc.fmi.javabookstoreproject.service.ReviewService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
@WebMvcTest(controllers = ReviewController.class)
public class ReviewControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private ReviewRepository reviewRepository;

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
                .genre(Genre.NOVEL)
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
                .rating(Rating.FIVE_STAR)
                .build();

    }

    @Test
    void testMakeReview() throws Exception {

        String endpoint = "/v1/reviews";

        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(testReview);
        Mockito.when(reviewRepository.getById(testReview.getId())).thenReturn(testReview);
        Mockito.when(accountRepository.existsById(testAccount.getId())).thenReturn(true);
        Mockito.when(bookRepository.existsById(testBook.getId())).thenReturn(true);

        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testReview);

        mockMvc.perform(post(endpoint).contentType(APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

}