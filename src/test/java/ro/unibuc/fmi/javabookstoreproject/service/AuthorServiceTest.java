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
import ro.unibuc.fmi.javabookstoreproject.model.Author;
import ro.unibuc.fmi.javabookstoreproject.repository.AuthorRepository;

import java.time.LocalDateTime;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private static Author testAuthor;

    @BeforeAll
    public static void setup() {

        testAuthor = Author.builder()
                .id(1L)
                .firstName("Harper")
                .lastName("Lee")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

    }

    @Test
    @DisplayName("Given test author, when an author is saved, then it can be verified for existence")
    void createAuthorTest() {

        Mockito.when(authorRepository.save(Mockito.any(Author.class))).thenReturn(testAuthor);
        Mockito.when(authorRepository.getById(testAuthor.getId())).thenReturn(testAuthor);

        authorService.createAuthor(testAuthor);
        Author savedAuthor = authorRepository.getById(testAuthor.getId());

        Assertions.assertEquals(savedAuthor, testAuthor);

    }

    @Test
    @DisplayName("Given test author, when an author is saved, then it can be deleted")
    void deleteAuthorTest() {

        Mockito.when(authorRepository.save(Mockito.any(Author.class))).thenReturn(testAuthor);
        Mockito.when(authorRepository.existsById(testAuthor.getId())).thenReturn(true);
        Mockito.when(authorRepository.getById(testAuthor.getId())).thenReturn(null);

        authorService.createAuthor(testAuthor);
        authorService.deleteAuthor(testAuthor.getId());
        Author savedAuthor = authorRepository.getById(testAuthor.getId());


        Assertions.assertNull(savedAuthor);

    }

}