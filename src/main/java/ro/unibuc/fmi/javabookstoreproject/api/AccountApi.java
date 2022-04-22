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
import ro.unibuc.fmi.javabookstoreproject.model.Account;

@Tag(name = "accounts", description = "Account API")
@Validated
@RequestMapping(value = "/v1")
public interface AccountApi {

    @Operation(summary = "Creates an account", operationId = "createAccount", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Account already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PostMapping(value = "/accounts",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void createAccount(@Parameter(description = "Supplied Account for creation", required = true) @RequestBody Account account);

    @Operation(summary = "Find account by ID", operationId = "getAccountById", description = "Returns a single Account", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/accounts/{accountId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    Account getAccountById(@Parameter(description = "ID of Account to return", required = true) @PathVariable("accountId") Long accountId);

    @Operation(summary = "Deletes an account", operationId = "deleteAccount", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @DeleteMapping(value = "/accounts/{accountId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void deleteAccount(@Parameter(description = "ID of deleted account", required = true) @PathVariable("accountId") Long accountId);

    @Operation(summary = "Updates email of an account", operationId = "updateEmail", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PutMapping(value = "/accounts/{accountId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void updateEmail(@Parameter(description = "ID of deleted account", required = true) @PathVariable("accountId") Long accountId, @Parameter(description = "New email", required = true) @RequestParam("email") String email);

}