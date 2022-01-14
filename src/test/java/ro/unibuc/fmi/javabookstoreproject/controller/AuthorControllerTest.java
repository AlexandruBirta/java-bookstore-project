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
import ro.unibuc.fmi.javabookstoreproject.model.Author;
import ro.unibuc.fmi.javabookstoreproject.repository.AuthorRepository;
import ro.unibuc.fmi.javabookstoreproject.service.AuthorService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
@WebMvcTest(controllers = AuthorController.class)
public class AuthorControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;
    @MockBean
    private AuthorRepository authorRepository;

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
    void testCreateAuthor() throws Exception {

        String endpoint = "/v1/authors";

        when(authorRepository.save(testAuthor)).thenReturn(testAuthor);

        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testAuthor);

        mockMvc.perform(put(endpoint).contentType(APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetAuthorById() throws Exception {

        String endpoint = "/v1/authors/" + testAuthor.getId();

        when(authorService.getAuthorById(testAuthor.getId())).thenReturn(testAuthor);

        mockMvc.perform(get(endpoint).contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Harper"))
                .andExpect(jsonPath("$.lastName").value("Lee"));

    }

    @Test
    void testDeleteAuthor() throws Exception {

        String endpoint = "/v1/authors/" + testAuthor.getId();

        when(authorRepository.existsById(testAuthor.getId())).thenReturn(true);

        mockMvc.perform(delete(endpoint).contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
