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
import ro.unibuc.fmi.javabookstoreproject.model.Narrator;

@Tag(name = "narrators", description = "Narrator API")
@Validated
@RequestMapping(value = "/v1")
public interface NarratorApi {

    @Operation(summary = "Creates an narrator", operationId = "createNarrator", tags = {"narrators"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Narrator already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PutMapping(value = "/narrators",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void createNarrator(@Parameter(description = "Supplied Narrator for creation", required = true) @RequestBody Narrator narrator);

    @Operation(summary = "Find narrator by ID", operationId = "getNarratorById", description = "Returns a single Narrator", tags = {"narrators"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Narrator not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/narrators/{narratorId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    Narrator getNarratorById(@Parameter(description = "ID of Narrator to return", required = true) @PathVariable("narratorId") Long narratorId);

    @Operation(summary = "Deletes an narrator", operationId = "deleteNarrator", tags = {"narrators"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Narrator not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @DeleteMapping(value = "/narrators/{narratorId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void deleteNarrator(@Parameter(description = "ID of deleted narrator", required = true) @PathVariable("narratorId") Long narratorId);


}
