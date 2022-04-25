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
import ro.unibuc.fmi.javabookstoreproject.model.Narrator;
import ro.unibuc.fmi.javabookstoreproject.repository.NarratorRepository;
import ro.unibuc.fmi.javabookstoreproject.service.NarratorService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
@WebMvcTest(controllers = NarratorController.class)
public class NarratorControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NarratorService narratorService;
    @MockBean
    private NarratorRepository narratorRepository;

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
    void testCreateNarrator() throws Exception {

        String endpoint = "/v1/narrators";

        when(narratorRepository.save(testNarrator)).thenReturn(testNarrator);

        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testNarrator);

        mockMvc.perform(post(endpoint).contentType(APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetNarratorById() throws Exception {

        String endpoint = "/v1/narrators/" + testNarrator.getId();

        when(narratorService.getNarratorById(testNarrator.getId())).thenReturn(testNarrator);

        mockMvc.perform(get(endpoint).contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("David"))
                .andExpect(jsonPath("$.lastName").value("Attenborough"));

    }

    @Test
    void testDeleteNarrator() throws Exception {

        String endpoint = "/v1/narrators/" + testNarrator.getId();

        when(narratorRepository.existsById(testNarrator.getId())).thenReturn(true);

        mockMvc.perform(delete(endpoint).contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());

    }

}