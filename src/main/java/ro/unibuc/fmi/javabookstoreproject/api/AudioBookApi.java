package ro.unibuc.fmi.javabookstoreproject.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiError;
import ro.unibuc.fmi.javabookstoreproject.model.AudioBook;

import java.util.List;

@Tag(name = "audioBooks", description = "AudioBook API")
@Validated
@RequestMapping(value = "/v1")
public interface AudioBookApi {

    @Operation(summary = "Creates a audioBook", operationId = "createAudioBook", tags = {"audioBooks"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "AudioBook already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PutMapping(value = "/audioBooks",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void createAudioBook(@Parameter(description = "Supplied AudioBook for creation", required = true) @RequestBody AudioBook audioBook);

    @Operation(summary = "Find audioBook by ID", operationId = "getAudioBookById", description = "Returns a single AudioBook", tags = {"audioBooks"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "AudioBook not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/audioBooks/{audioBookId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    AudioBook getAudioBookById(@Parameter(description = "ID of AudioBook to return", required = true) @PathVariable("audioBookId") Long audioBookId);

    @Operation(summary = "Find all audioBooks", operationId = "getAudioBooks", description = "Returns the list of all audioBooks", tags = {"audioBooks"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No audioBooks found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/audioBooks",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<AudioBook> getAudioBooks();

    @Operation(summary = "Find all audioBooks", operationId = "getAudioBooksByGenre", description = "Returns the list of all audioBooks", tags = {"audioBooks"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No audioBooks found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/audioBooks/genre/{genre}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<AudioBook> getAudioBooksByGenre(@Parameter(description = "Genre of audioBooks", required = true) @PathVariable("genre") String genre);

    @Operation(summary = "Find all audioBooks", operationId = "getAudioBooksByAuthor", description = "Returns the list of all audioBooks", tags = {"audioBooks"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No audioBooks found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/audioBooks/author/{authorFirstName}{authorLastName}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<AudioBook> getAudioBooksByAuthor(@Parameter(description = "First name of author", required = true) @PathVariable("authorFirstName") String authorFirstName,
                                          @Parameter(description = "Last name of author", required = true) @PathVariable("authorLastName") String authorLastName);

    @Operation(summary = "Find all audioBooks", operationId = "getAudioBooksByNarrator", description = "Returns the list of all audioBooks", tags = {"audioBooks"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No audioBooks found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/audioBooks/narrator/{narratorFirstName}{narratorLastName}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<AudioBook> getAudioBooksByNarrator(@Parameter(description = "First name of narrator", required = true) @PathVariable("narratorFirstName") String narratorFirstName,
                                            @Parameter(description = "Last name of narrator", required = true) @PathVariable("narratorLastName") String narratorLastName);

    @Operation(summary = "Find all audioBooks", operationId = "getAudioBooksByPublisher", description = "Returns the list of all audioBooks", tags = {"audioBooks"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No audioBooks found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/audioBooks/publisher/{publisherName}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<AudioBook> getAudioBooksByPublisher(@Parameter(description = "Name of publisher", required = true) @PathVariable("publisherName") String publisherName);

    @Operation(summary = "Deletes an audioBook", operationId = "deleteAudioBook", tags = {"audioBooks"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "AudioBook not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @DeleteMapping(value = "/audioBooks/{audioBookId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void deleteAudioBook(@Parameter(description = "ID of deleted audioBook", required = true) @PathVariable("audioBookId") Long audioBookId);

}
