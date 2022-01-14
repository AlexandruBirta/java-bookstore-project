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
import ro.unibuc.fmi.javabookstoreproject.repository.AudioBookRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javaaudioBookstoreproject.model.*")
class AudioBookServiceTest {

    @Mock
    private AudioBookRepository audioBookRepository;

    @InjectMocks
    private AudioBookService audioBookService;

    private static Author testAuthorOne;
    private static Author testAuthorTwo;
    private static Narrator testNarratorOne;
    private static Narrator testNarratorTwo;
    private static Publisher testPublisherOne;
    private static Publisher testPublisherTwo;
    private static AudioBook testAudioBookOne;
    private static AudioBook testAudioBookTwo;

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
                .name("Penguin AudioBooks")
                .description("Penguin AudioBooks was originally a British publishing house. It was co-founded in 1935 by Sir Allen Lane with his brothers Richard and John, as a line of the publishers The Bodley Head, only becoming a separate company the following year.")
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

        testNarratorOne = Narrator.builder()
                .id(1L)
                .firstName("Scott")
                .lastName("Brick")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testNarratorTwo = Narrator.builder()
                .id(2L)
                .firstName("Cassandra")
                .lastName("Campbell")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testAudioBookOne = AudioBook.builder()
                .id(1L)
                .name("To Kill a Mockingbird")
                .description("To Kill a Mockingbird has become a classic of modern American literature, winning the Pulitzer Prize. The plot and characters are loosely based on Lee's observations of her family, her neighbors and an event that occurred near her hometown of Monroeville, Alabama, in 1936, when she was ten.")
                .genre(Book.Genre.NOVEL)
                .isbn("063122581-1")
                .author(testAuthorOne)
                .publisher(testPublisherOne)
                .narrator(testNarratorOne)
                .durationInMinutes(305L)
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testAudioBookTwo = AudioBook.builder()
                .id(2L)
                .name("The Hound of the Baskervilles")
                .description("The Hound of the Baskervilles is the third of the four crime novels written by Sir Arthur Conan Doyle featuring the detective Sherlock Holmes. Originally serialised in The Strand Magazine from August 1901 to April 1902, it is set largely on Dartmoor in Devon in England's West Country and tells the story of an attempted murder inspired by the legend of a fearsome, diabolical hound of supernatural origin. Sherlock Holmes and his companion Dr. Watson investigate the case. ")
                .genre(Book.Genre.MYSTERY)
                .isbn("538363100-3")
                .author(testAuthorTwo)
                .publisher(testPublisherTwo)
                .narrator(testNarratorTwo)
                .durationInMinutes(524L)
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

    }

    @Test
    @DisplayName("Given test audioBook, when an audioBook is saved, then it can be verified for existence")
    void createAudioBookTest() {

        Mockito.when(audioBookRepository.save(Mockito.any(AudioBook.class))).thenReturn(testAudioBookOne);
        Mockito.when(audioBookRepository.getById(testAudioBookOne.getId())).thenReturn(testAudioBookOne);

        audioBookService.createAudioBook(testAudioBookOne);
        AudioBook savedAudioBook = audioBookRepository.getById(testAudioBookOne.getId());


        Assertions.assertEquals(savedAudioBook, testAudioBookOne);

    }

    @Test
    @DisplayName("Given list of test audioBooks saved, when an audioBook list is searched, then it can be verified for existence")
    void getAudioBooksTest() {

        List<AudioBook> audioBookList = new ArrayList<>();
        audioBookList.add(testAudioBookOne);
        audioBookList.add(testAudioBookTwo);

        Mockito.when(audioBookRepository.findAll()).thenReturn(audioBookList);

        List<AudioBook> savedAudioBooks = audioBookService.getAudioBooks();
        log.info(savedAudioBooks.toString());

        Assertions.assertEquals(audioBookList, savedAudioBooks);

    }

    @Test
    @DisplayName("Given list of test audioBooks saved, when an audioBook list is searched by genre, then a specific list of audioBooks should be returned")
    void getAudioBooksByGenreTest() {

        List<AudioBook> audioBookList = new ArrayList<>();
        audioBookList.add(testAudioBookOne);
        audioBookList.add(testAudioBookTwo);

        List<AudioBook> audioBookListByGenre = new ArrayList<>();

        audioBookListByGenre.add(testAudioBookTwo);

        String genre = Book.Genre.MYSTERY.getValue();

        Mockito.when(audioBookRepository.findAllByGenre(genre)).thenReturn(audioBookListByGenre);

        List<AudioBook> savedAudioBooksByGenre = audioBookService.getAudioBooksByGenre(genre);
        log.info(savedAudioBooksByGenre.toString());

        Assertions.assertEquals(audioBookListByGenre, savedAudioBooksByGenre);
        Assertions.assertNotEquals(audioBookListByGenre, audioBookList);

    }

    @Test
    @DisplayName("Given list of test audioBooks saved, when an audioBook list is searched by author, then a specific list of audioBooks should be returned")
    void getAudioBooksByAuthorTest() {

        List<AudioBook> audioBookList = new ArrayList<>();
        audioBookList.add(testAudioBookOne);
        audioBookList.add(testAudioBookTwo);

        List<AudioBook> audioBookListByAuthor = new ArrayList<>();

        audioBookListByAuthor.add(testAudioBookOne);

        Mockito.when(audioBookRepository.findAllByAuthor(testAuthorOne.getFirstName(), testAuthorOne.getLastName())).thenReturn(audioBookListByAuthor);

        List<AudioBook> savedAudioBooksByAuthor = audioBookService.getAudioBooksByAuthor(testAuthorOne.getFirstName(), testAuthorOne.getLastName());
        log.info(savedAudioBooksByAuthor.toString());

        Assertions.assertEquals(audioBookListByAuthor, savedAudioBooksByAuthor);
        Assertions.assertNotEquals(audioBookListByAuthor, audioBookList);

    }

    @Test
    @DisplayName("Given list of test audioBooks saved, when an audioBook list is searched by publisher, then a specific list of audioBooks should be returned")
    void getAudioBooksByPublisherTest() {

        List<AudioBook> audioBookList = new ArrayList<>();
        audioBookList.add(testAudioBookOne);
        audioBookList.add(testAudioBookTwo);

        List<AudioBook> audioBookListByPublisher = new ArrayList<>();

        audioBookListByPublisher.add(testAudioBookTwo);

        Mockito.when(audioBookRepository.findAllByPublisher(testPublisherTwo.getName())).thenReturn(audioBookListByPublisher);

        List<AudioBook> savedAudioBooksByPublisher = audioBookService.getAudioBooksByPublisher(testPublisherTwo.getName());
        log.info(savedAudioBooksByPublisher.toString());

        Assertions.assertEquals(audioBookListByPublisher, savedAudioBooksByPublisher);
        Assertions.assertNotEquals(audioBookListByPublisher, audioBookList);

    }

    @Test
    @DisplayName("Given list of test audioBooks saved, when an audioBook list is searched by narrator, then a specific list of audioBooks should be returned")
    void getAudioBooksByNarratorTest() {

        List<AudioBook> audioBookList = new ArrayList<>();
        audioBookList.add(testAudioBookOne);
        audioBookList.add(testAudioBookTwo);

        List<AudioBook> audioBookListByNarrator = new ArrayList<>();

        audioBookListByNarrator.add(testAudioBookOne);

        Mockito.when(audioBookRepository.findAllByNarrator(testNarratorOne.getFirstName(), testNarratorOne.getLastName())).thenReturn(audioBookListByNarrator);

        List<AudioBook> savedAudioBooksByNarrator = audioBookService.getAudioBooksByNarrator(testNarratorOne.getFirstName(), testNarratorOne.getLastName());
        log.info(savedAudioBooksByNarrator.toString());

        Assertions.assertEquals(audioBookListByNarrator, savedAudioBooksByNarrator);
        Assertions.assertNotEquals(audioBookListByNarrator, audioBookList);

    }

    @Test
    @DisplayName("Given test audioBook, when an audioBook is saved, then it can be deleted")
    void deleteAudioBookTest() {

        Mockito.when(audioBookRepository.save(Mockito.any(AudioBook.class))).thenReturn(testAudioBookOne);
        Mockito.when(audioBookRepository.existsById(testAudioBookOne.getId())).thenReturn(true);
        Mockito.when(audioBookRepository.getById(testAudioBookOne.getId())).thenReturn(null);

        audioBookService.createAudioBook(testAudioBookOne);
        audioBookService.deleteAudioBook(testAudioBookOne.getId());
        AudioBook savedAudioBook = audioBookRepository.getById(testAudioBookOne.getId());


        Assertions.assertNull(savedAudioBook);

    }

}
