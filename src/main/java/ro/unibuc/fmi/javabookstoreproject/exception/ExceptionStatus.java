package ro.unibuc.fmi.javabookstoreproject.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import ro.unibuc.fmi.javabookstoreproject.model.Book;
import ro.unibuc.fmi.javabookstoreproject.model.Review;
import ro.unibuc.fmi.javabookstoreproject.model.Transaction;

import java.util.Arrays;

public enum ExceptionStatus {

    ACCOUNT_NOT_FOUND("Account with id '%s' not found!", HttpStatus.NOT_FOUND),
    ACCOUNT_ALREADY_EXISTS("Account with email '%s' already exists!", HttpStatus.BAD_REQUEST),
    AUTHOR_NOT_FOUND("Author with id '%s' not found!", HttpStatus.NOT_FOUND),
    AUTHOR_ALREADY_EXISTS("Author with name '%s' already exists!", HttpStatus.BAD_REQUEST),
    PUBLISHER_NOT_FOUND("Publisher with id '%s' not found!", HttpStatus.NOT_FOUND),
    PUBLISHER_ALREADY_EXISTS("Publisher with name '%s' already exists!", HttpStatus.BAD_REQUEST),
    NARRATOR_NOT_FOUND("Narrator with id '%s' not found!", HttpStatus.NOT_FOUND),
    NARRATOR_ALREADY_EXISTS("Narrator with name '%s' already exists!", HttpStatus.BAD_REQUEST),
    NO_BOOKS_FOUND("There are no books in the catalogue '%s' at this time!", HttpStatus.NOT_FOUND),
    BOOK_NOT_FOUND("Book with id '%s' not found!", HttpStatus.NOT_FOUND),
    BOOK_ALREADY_EXISTS("Book with ISBN '%s' already exists!", HttpStatus.BAD_REQUEST),
    NO_AUDIO_BOOKS_FOUND("There are no audio books in the catalogue '%s' at this time!", HttpStatus.NOT_FOUND),
    AUDIO_BOOK_NOT_FOUND("Audio book with id '%s' not found!", HttpStatus.NOT_FOUND),
    AUDIO_BOOK_ALREADY_EXISTS("Audio book with ISBN '%s' already exists!", HttpStatus.BAD_REQUEST),
    REVIEW_ALREADY_EXISTS("Review with title '%s' already exists!", HttpStatus.BAD_REQUEST),
    TRANSACTION_NOT_FOUND("Transaction with ID '%s' not found!", HttpStatus.NOT_FOUND),
    ACCOUNT_BOOK_NOT_FOUND("Account book with ID '%s' not found!", HttpStatus.NOT_FOUND),
    ACCOUNT_BOOK_ALREADY_EXISTS("Account book for account ID and book ISBN '%s' already exists!", HttpStatus.BAD_REQUEST),
    INVALID_GENRE("Invalid genre '%s'. Genre must be one of " + Arrays.toString(Book.Genre.values()), HttpStatus.NOT_FOUND),
    INVALID_RATING("Invalid rating '%s'. Rating must be one of " + Arrays.toString(Review.Rating.values()), HttpStatus.NOT_FOUND),
    INVALID_TRANSACTION_TYPE("Invalid transaction type '%s'. Transaction type must be one of " + Arrays.toString(Transaction.Type.values()), HttpStatus.NOT_FOUND);

    private final String value;
    private final HttpStatus httpStatus;

    ExceptionStatus(String value, HttpStatus httpStatus) {
        this.value = value;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ExceptionStatus fromValue(String text) {
        for (ExceptionStatus b : ExceptionStatus.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

}
