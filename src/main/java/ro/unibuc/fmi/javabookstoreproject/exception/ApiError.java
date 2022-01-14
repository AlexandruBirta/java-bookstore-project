package ro.unibuc.fmi.javabookstoreproject.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
@Schema
public class ApiError {

    @Schema
    private final String errorMessage;

    @Schema
    private final HttpStatus httpStatus;

    @Schema
    private final LocalDateTime timestamp;
}