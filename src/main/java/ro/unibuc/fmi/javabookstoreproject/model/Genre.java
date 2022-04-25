package ro.unibuc.fmi.javabookstoreproject.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;

@Getter
public enum Genre {

    NOVEL("novel"),
    SCI_FI("sci-fi"),
    EDUCATIONAL("educational"),
    MYSTERY("mystery"),
    PHILOSOPHY("philosophy"),
    FICTION("fiction");


    private final String value;

    Genre(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Genre fromValue(String text) {
        for (Genre b : Genre.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        throw new ApiException(ExceptionStatus.INVALID_GENRE, text);
    }

}