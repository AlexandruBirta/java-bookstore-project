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
import ro.unibuc.fmi.javabookstoreproject.model.Author;

@Tag(name = "authors", description = "Author API")
@Validated
@RequestMapping(value = "/v1")
public interface AuthorApi {

    @Operation(summary = "Creates an author", operationId = "createAuthor", tags = {"authors"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Author already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PutMapping(value = "/authors",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void createAuthor(@Parameter(description = "Supplied Author for creation", required = true) @RequestBody Author author);

    @Operation(summary = "Find author by ID", operationId = "getAuthorById", description = "Returns a single Author", tags = {"authors"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/authors/{authorId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    Author getAuthorById(@Parameter(description = "ID of Author to return", required = true) @PathVariable("authorId") Long authorId);

    @Operation(summary = "Deletes an author", operationId = "deleteAuthor", tags = {"authors"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @DeleteMapping(value = "/authors/{authorId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void deleteAuthor(@Parameter(description = "ID of deleted author", required = true) @PathVariable("authorId") Long authorId);

}
