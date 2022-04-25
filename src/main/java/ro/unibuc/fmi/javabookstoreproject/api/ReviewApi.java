package ro.unibuc.fmi.javabookstoreproject.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiError;
import ro.unibuc.fmi.javabookstoreproject.model.Review;

import javax.validation.Valid;

@Tag(name = "reviews", description = "Review API")
@Validated
@RequestMapping(value = "/v1")
public interface ReviewApi {

    @Operation(summary = "Makes a review", operationId = "makeReview", tags = {"reviews"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Review already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PostMapping(value = "/reviews",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    String makeReview(@Parameter(description = "Supplied Review", required = true) @ModelAttribute @Valid Review review);

    @Operation(summary = "Makes a review", operationId = "makeReview", tags = {"reviews"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Review already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/reviews/new",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    String makeReview(Model model);

}