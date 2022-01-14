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
import ro.unibuc.fmi.javabookstoreproject.model.Publisher;
import ro.unibuc.fmi.javabookstoreproject.repository.PublisherRepository;

import java.time.LocalDateTime;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private static Publisher testPublisher;

    @BeforeAll
    public static void setup() {

        testPublisher = Publisher.builder()
                .id(1L)
                .name("Penguin Books")
                .description("Penguin Books was originally a British publishing house. It was co-founded in 1935 by Sir Allen Lane with his brothers Richard and John, as a line of the publishers The Bodley Head, only becoming a separate company the following year.")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

    }

    @Test
    @DisplayName("Given test publisher, when an publisher is saved, then it can be verified for existence")
    void createPublisherTest() {

        Mockito.when(publisherRepository.save(Mockito.any(Publisher.class))).thenReturn(testPublisher);
        Mockito.when(publisherRepository.getById(testPublisher.getId())).thenReturn(testPublisher);

        publisherService.createPublisher(testPublisher);
        Publisher savedPublisher = publisherRepository.getById(testPublisher.getId());

        Assertions.assertEquals(savedPublisher, testPublisher);

    }

    @Test
    @DisplayName("Given test publisher, when an publisher is saved, then it can be deleted")
    void deletePublisherTest() {

        Mockito.when(publisherRepository.save(Mockito.any(Publisher.class))).thenReturn(testPublisher);
        Mockito.when(publisherRepository.existsById(testPublisher.getId())).thenReturn(true);
        Mockito.when(publisherRepository.getById(testPublisher.getId())).thenReturn(null);

        publisherService.createPublisher(testPublisher);
        publisherService.deletePublisher(testPublisher.getId());
        Publisher savedPublisher = publisherRepository.getById(testPublisher.getId());


        Assertions.assertNull(savedPublisher);

    }

}