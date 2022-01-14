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
import ro.unibuc.fmi.javabookstoreproject.model.Narrator;
import ro.unibuc.fmi.javabookstoreproject.repository.NarratorRepository;

import java.time.LocalDateTime;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
class NarratorServiceTest {

    @Mock
    private NarratorRepository narratorRepository;

    @InjectMocks
    private NarratorService narratorService;

    private static Narrator testNarrator;

    @BeforeAll
    public static void setup() {

        testNarrator = Narrator.builder()
                .id(1L)
                .firstName("David")
                .lastName("Attenborough")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

    }

    @Test
    @DisplayName("Given test narrator, when an narrator is saved, then it can be verified for existence")
    void createNarratorTest() {

        Mockito.when(narratorRepository.save(Mockito.any(Narrator.class))).thenReturn(testNarrator);
        Mockito.when(narratorRepository.getById(testNarrator.getId())).thenReturn(testNarrator);

        narratorService.createNarrator(testNarrator);
        Narrator savedNarrator = narratorRepository.getById(testNarrator.getId());

        Assertions.assertEquals(savedNarrator, testNarrator);

    }

    @Test
    @DisplayName("Given test narrator, when an narrator is saved, then it can be deleted")
    void deleteNarratorTest() {

        Mockito.when(narratorRepository.save(Mockito.any(Narrator.class))).thenReturn(testNarrator);
        Mockito.when(narratorRepository.existsById(testNarrator.getId())).thenReturn(true);
        Mockito.when(narratorRepository.getById(testNarrator.getId())).thenReturn(null);

        narratorService.createNarrator(testNarrator);
        narratorService.deleteNarrator(testNarrator.getId());
        Narrator savedNarrator = narratorRepository.getById(testNarrator.getId());


        Assertions.assertNull(savedNarrator);

    }

}