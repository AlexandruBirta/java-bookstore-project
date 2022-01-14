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
import ro.unibuc.fmi.javabookstoreproject.model.Publisher;

@Tag(name = "publishers", description = "Publisher API")
@Validated
@RequestMapping(value = "/v1")
public interface PublisherApi {

    @Operation(summary = "Creates an publisher", operationId = "createPublisher", tags = {"publishers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Publisher already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PutMapping(value = "/publishers",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void createPublisher(@Parameter(description = "Supplied Publisher for creation", required = true) @RequestBody Publisher publisher);

    @Operation(summary = "Find publisher by ID", operationId = "getPublisherById", description = "Returns a single Publisher", tags = {"publishers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Publisher not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/publishers/{publisherId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    Publisher getPublisherById(@Parameter(description = "ID of Publisher to return", required = true) @PathVariable("publisherId") Long publisherId);

    @Operation(summary = "Deletes an publisher", operationId = "deletePublisher", tags = {"publishers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Publisher not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @DeleteMapping(value = "/publishers/{publisherId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void deletePublisher(@Parameter(description = "ID of deleted publisher", required = true) @PathVariable("publisherId") Long publisherId);

}