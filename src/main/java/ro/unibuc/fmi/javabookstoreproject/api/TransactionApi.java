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
import ro.unibuc.fmi.javabookstoreproject.model.Transaction;

import javax.validation.Valid;

@Tag(name = "transactions", description = "Transaction API")
@Validated
@RequestMapping(value = "/v1")
public interface TransactionApi {

    @Operation(summary = "Makes a transaction associated with an account", operationId = "makeTransaction", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PostMapping(value = "/transactions",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    String makeTransaction(@Parameter(description = "Supplied Transaction", required = true) @ModelAttribute @Valid Transaction transaction);

    @Operation(summary = "Makes a transaction associated with an account", operationId = "makeTransaction", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/transactions/new",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    String makeTransaction(Model model);

}