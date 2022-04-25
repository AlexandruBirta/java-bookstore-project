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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.unibuc.fmi.javabookstoreproject.model.Author;
import ro.unibuc.fmi.javabookstoreproject.model.Book;
import ro.unibuc.fmi.javabookstoreproject.model.Genre;
import ro.unibuc.fmi.javabookstoreproject.model.Publisher;
import ro.unibuc.fmi.javabookstoreproject.repository.BookRepository;
import ro.unibuc.fmi.javabookstoreproject.service.BookService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;

    private static Author testAuthorOne;
    private static Author testAuthorTwo;
    private static Publisher testPublisherOne;
    private static Publisher testPublisherTwo;
    private static Book testBookOne;
    private static Book testBookTwo;

    @BeforeAll
    public static void setup() {

        testAuthorOne = Author.builder()
                .id(1L)
                .firstName("Harper")
                .lastName("Lee")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testAuthorTwo = Author.builder()
                .id(2L)
                .firstName("Arthur")
                .lastName("Doyle")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testPublisherOne = Publisher.builder()
                .id(1L)
                .name("Penguin Books")
                .description("Penguin Books was originally a British publishing house. It was co-founded in 1935 by Sir Allen Lane with his brothers Richard and John, as a line of the publishers The Bodley Head, only becoming a separate company the following year.")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testPublisherTwo = Publisher.builder()
                .id(2L)
                .name("Macmillan Publishers")
                .description("Macmillan Publishers Ltd is a British publishing company traditionally considered to be one of the 'Big Five' English language publishers.")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testBookOne = Book.builder()
                .id(1L)
                .name("To Kill a Mockingbird")
                .description("To Kill a Mockingbird has become a classic of modern American literature, winning the Pulitzer Prize. The plot and characters are loosely based on Lee's observations of her family, her neighbors and an event that occurred near her hometown of Monroeville, Alabama, in 1936, when she was ten.")
                .genre(Genre.NOVEL)
                .isbn("063122581-1")
                .author(testAuthorOne)
                .publisher(testPublisherOne)
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testBookTwo = Book.builder()
                .id(2L)
                .name("The Hound of the Baskervilles")
                .description("The Hound of the Baskervilles is the third of the four crime novels written by Sir Arthur Conan Doyle featuring the detective Sherlock Holmes. Originally serialised in The Strand Magazine from August 1901 to April 1902, it is set largely on Dartmoor in Devon in England's West Country and tells the story of an attempted murder inspired by the legend of a fearsome, diabolical hound of supernatural origin. Sherlock Holmes and his companion Dr. Watson investigate the case. ")
                .genre(Genre.MYSTERY)
                .isbn("538363100-3")
                .author(testAuthorTwo)
                .publisher(testPublisherTwo)
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

    }

    @Test
    void testCreateBook() throws Exception {

        String endpoint = "/v1/books";

        when(bookRepository.save(testBookOne)).thenReturn(testBookOne);

        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testBookOne);

        mockMvc.perform(post(endpoint).contentType(APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetBookById() throws Exception {

        String endpoint = "/v1/books/" + testBookOne.getId();

        when(bookService.getBookById(testBookOne.getId())).thenReturn(testBookOne);

        mockMvc.perform(get(endpoint).contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("To Kill a Mockingbird"))
                .andExpect(jsonPath("$.description").value("To Kill a Mockingbird has become a classic of modern American literature, winning the Pulitzer Prize. The plot and characters are loosely based on Lee's observations of her family, her neighbors and an event that occurred near her hometown of Monroeville, Alabama, in 1936, when she was ten."))
                .andExpect(jsonPath("$.genre").value("novel"));

    }

    @Test
    void testGetBooks() throws Exception {

        int pageIndex = 1;
        int pageSize = 10;

        String endpoint = "/v1/books?pageIndex=" + pageIndex + "&pageSize=" + pageSize;

        List<Book> bookList = new ArrayList<>();
        bookList.add(testBookOne);
        bookList.add(testBookTwo);

        when(bookService.getBooks(pageIndex, pageSize)).thenReturn(new PageImpl<>(bookList));

        mockMvc.perform(get(endpoint).contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].id").exists())
                .andExpect(jsonPath("$.content.[0].name").value("To Kill a Mockingbird"))
                .andExpect(jsonPath("$.content.[0].description").value("To Kill a Mockingbird has become a classic of modern American literature, winning the Pulitzer Prize. The plot and characters are loosely based on Lee's observations of her family, her neighbors and an event that occurred near her hometown of Monroeville, Alabama, in 1936, when she was ten."))
                .andExpect(jsonPath("$.content.[0].genre").value("novel"))
                .andExpect(jsonPath("$.content.[1].id").exists())
                .andExpect(jsonPath("$.content.[1].name").value("The Hound of the Baskervilles"))
                .andExpect(jsonPath("$.content.[1].description").value("The Hound of the Baskervilles is the third of the four crime novels written by Sir Arthur Conan Doyle featuring the detective Sherlock Holmes. Originally serialised in The Strand Magazine from August 1901 to April 1902, it is set largely on Dartmoor in Devon in England's West Country and tells the story of an attempted murder inspired by the legend of a fearsome, diabolical hound of supernatural origin. Sherlock Holmes and his companion Dr. Watson investigate the case. "))
                .andExpect(jsonPath("$.content.[1].genre").value("mystery"));

    }

    @Test
    void testGetBooksByGenre() throws Exception {

        Genre genre = Genre.MYSTERY;

        String endpoint = "/v1/books/genre/" + genre;

        List<Book> bookListByGenre = new ArrayList<>();

        bookListByGenre.add(testBookTwo);

        Mockito.when(bookRepository.findAllByGenre(genre)).thenReturn(bookListByGenre);
        when(bookService.getBooksByGenre(genre)).thenReturn(bookListByGenre);

        mockMvc.perform(get(endpoint).contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value("The Hound of the Baskervilles"))
                .andExpect(jsonPath("$[0].description").value("The Hound of the Baskervilles is the third of the four crime novels written by Sir Arthur Conan Doyle featuring the detective Sherlock Holmes. Originally serialised in The Strand Magazine from August 1901 to April 1902, it is set largely on Dartmoor in Devon in England's West Country and tells the story of an attempted murder inspired by the legend of a fearsome, diabolical hound of supernatural origin. Sherlock Holmes and his companion Dr. Watson investigate the case. "))
                .andExpect(jsonPath("$[0].genre").value("mystery"));

    }

    @Test
    void testDeleteBook() throws Exception {

        String endpoint = "/v1/books/" + testBookOne.getId();

        when(bookRepository.existsById(testBookOne.getId())).thenReturn(true);

        mockMvc.perform(delete(endpoint).contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());

    }

}