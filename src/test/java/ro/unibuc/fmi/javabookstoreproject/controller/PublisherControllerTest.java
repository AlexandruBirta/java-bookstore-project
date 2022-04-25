package ro.unibuc.fmi.javabookstoreproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.unibuc.fmi.javabookstoreproject.model.Publisher;
import ro.unibuc.fmi.javabookstoreproject.repository.PublisherRepository;
import ro.unibuc.fmi.javabookstoreproject.service.PublisherService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
@WebMvcTest(controllers = PublisherController.class)
public class PublisherControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PublisherService publisherService;
    @MockBean
    private PublisherRepository publisherRepository;

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
    void testCreatePublisher() throws Exception {

        String endpoint = "/v1/publishers";

        when(publisherRepository.save(testPublisher)).thenReturn(testPublisher);

        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testPublisher);

        mockMvc.perform(post(endpoint).contentType(APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetPublisherById() throws Exception {

        String endpoint = "/v1/publishers/" + testPublisher.getId();

        when(publisherService.getPublisherById(testPublisher.getId())).thenReturn(testPublisher);

        mockMvc.perform(get(endpoint).contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Penguin Books"))
                .andExpect(jsonPath("$.description").value("Penguin Books was originally a British publishing house. It was co-founded in 1935 by Sir Allen Lane with his brothers Richard and John, as a line of the publishers The Bodley Head, only becoming a separate company the following year."));

    }

    @Test
    void testDeletePublisher() throws Exception {

        String endpoint = "/v1/publishers/" + testPublisher.getId();

        when(publisherRepository.existsById(testPublisher.getId())).thenReturn(true);

        mockMvc.perform(delete(endpoint).contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());

    }

}