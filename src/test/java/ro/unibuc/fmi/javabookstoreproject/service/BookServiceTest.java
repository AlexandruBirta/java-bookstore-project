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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import ro.unibuc.fmi.javabookstoreproject.model.Author;
import ro.unibuc.fmi.javabookstoreproject.model.Book;
import ro.unibuc.fmi.javabookstoreproject.model.Genre;
import ro.unibuc.fmi.javabookstoreproject.model.Publisher;
import ro.unibuc.fmi.javabookstoreproject.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

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
    @DisplayName("Given test book, when an book is saved, then it can be verified for existence")
    void createBookTest() {

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(testBookOne);
        Mockito.when(bookRepository.getById(testBookOne.getId())).thenReturn(testBookOne);

        bookService.createBook(testBookOne);
        Book savedBook = bookRepository.getById(testBookOne.getId());


        Assertions.assertEquals(savedBook, testBookOne);

    }

    @Test
    @DisplayName("Given list of test books saved, when an book list is searched, then it can be verified for existence")
    void getBooksTest() {

        List<Book> bookList = new ArrayList<>();
        bookList.add(testBookOne);
        bookList.add(testBookTwo);

        int pageIndex = 1;
        int pageSize = 10;

        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by("id").descending());

        Mockito.when(bookRepository.findAllPaginated(pageRequest)).thenReturn(new PageImpl<>(bookList));

        Page<Book> savedBooks = bookService.getBooks(pageIndex, pageSize);
        log.info(savedBooks.toString());

        Assertions.assertEquals(bookList, savedBooks.getContent());

    }

    @Test
    @DisplayName("Given list of test books saved, when an book list is searched by genre, then a specific list of books should be returned")
    void getBooksByGenreTest() {

        List<Book> bookList = new ArrayList<>();
        bookList.add(testBookOne);
        bookList.add(testBookTwo);

        List<Book> bookListByGenre = new ArrayList<>();

        bookListByGenre.add(testBookTwo);

        Genre genre = Genre.MYSTERY;

        Mockito.when(bookRepository.findAllByGenre(genre)).thenReturn(bookListByGenre);

        List<Book> savedBooksByGenre = bookService.getBooksByGenre(genre);
        log.info(savedBooksByGenre.toString());

        Assertions.assertEquals(bookListByGenre, savedBooksByGenre);
        Assertions.assertNotEquals(bookListByGenre, bookList);

    }

    @Test
    @DisplayName("Given list of test books saved, when an book list is searched by author, then a specific list of books should be returned")
    void getBooksByAuthorTest() {

        List<Book> bookList = new ArrayList<>();
        bookList.add(testBookOne);
        bookList.add(testBookTwo);

        List<Book> bookListByAuthor = new ArrayList<>();

        bookListByAuthor.add(testBookOne);

        Mockito.when(bookRepository.findAllByAuthor(testAuthorOne.getFirstName(), testAuthorOne.getLastName())).thenReturn(bookListByAuthor);

        List<Book> savedBooksByAuthor = bookService.getBooksByAuthor(testAuthorOne.getFirstName(), testAuthorOne.getLastName());
        log.info(savedBooksByAuthor.toString());

        Assertions.assertEquals(bookListByAuthor, savedBooksByAuthor);
        Assertions.assertNotEquals(bookListByAuthor, bookList);

    }

    @Test
    @DisplayName("Given list of test books saved, when an book list is searched by publisher, then a specific list of books should be returned")
    void getBooksByPublisherTest() {

        List<Book> bookList = new ArrayList<>();
        bookList.add(testBookOne);
        bookList.add(testBookTwo);

        List<Book> bookListByPublisher = new ArrayList<>();

        bookListByPublisher.add(testBookTwo);

        Mockito.when(bookRepository.findAllByPublisher(testPublisherTwo.getName())).thenReturn(bookListByPublisher);

        List<Book> savedBooksByPublisher = bookService.getBooksByPublisher(testPublisherTwo.getName());
        log.info(savedBooksByPublisher.toString());

        Assertions.assertEquals(bookListByPublisher, savedBooksByPublisher);
        Assertions.assertNotEquals(bookListByPublisher, bookList);

    }

    @Test
    @DisplayName("Given test book, when an book is saved, then it can be deleted")
    void deleteBookTest() {

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(testBookOne);
        Mockito.when(bookRepository.existsById(testBookOne.getId())).thenReturn(true);
        Mockito.when(bookRepository.getById(testBookOne.getId())).thenReturn(null);

        bookService.createBook(testBookOne);
        bookService.deleteBook(testBookOne.getId());
        Book savedBook = bookRepository.getById(testBookOne.getId());


        Assertions.assertNull(savedBook);

    }

}