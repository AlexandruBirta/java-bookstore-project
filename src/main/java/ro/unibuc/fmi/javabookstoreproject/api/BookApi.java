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
import ro.unibuc.fmi.javabookstoreproject.model.Book;

import java.util.List;

@Tag(name = "books", description = "Book API")
@Validated
@RequestMapping(value = "/v1")
public interface BookApi {

    @Operation(summary = "Creates a book", operationId = "createBook", tags = {"books"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Book already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PutMapping(value = "/books",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void createBook(@Parameter(description = "Supplied Book for creation", required = true) @RequestBody Book book);

    @Operation(summary = "Find book by ID", operationId = "getBookById", description = "Returns a single Book", tags = {"books"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/books/{bookId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    Book getBookById(@Parameter(description = "ID of Book to return", required = true) @PathVariable("bookId") Long bookId);

    @Operation(summary = "Find all books", operationId = "getBooks", description = "Returns the list of all books", tags = {"books"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No books found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/books",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<Book> getBooks();

    @Operation(summary = "Find all books", operationId = "getBooksByGenre", description = "Returns the list of all books", tags = {"books"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No books found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/books/genre/{genre}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<Book> getBooksByGenre(@Parameter(description = "Genre of books", required = true) @PathVariable("genre") String genre);

    @Operation(summary = "Find all books", operationId = "getBooksByAuthor", description = "Returns the list of all books", tags = {"books"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No books found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/books/author/{authorFirstName}{authorLastName}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<Book> getBooksByAuthor(@Parameter(description = "First name of author", required = true) @PathVariable("authorFirstName") String authorFirstName,
                                @Parameter(description = "Last name of author", required = true) @PathVariable("authorLastName") String authorLastName);

    @Operation(summary = "Find all books", operationId = "getBooksByPublisher", description = "Returns the list of all books", tags = {"books"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No books found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/books/publisher/{publisherName}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<Book> getBooksByPublisher(@Parameter(description = "Name of publisher", required = true) @PathVariable("publisherName") String publisherName);

    @Operation(summary = "Deletes an book", operationId = "deleteBook", tags = {"books"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @DeleteMapping(value = "/books/{bookId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void deleteBook(@Parameter(description = "ID of deleted book", required = true) @PathVariable("bookId") Long bookId);

}
