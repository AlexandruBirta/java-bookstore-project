package ro.unibuc.fmi.javabookstoreproject.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;

@Getter
public enum Rating {

    ONE_STAR("did not like"),
    TWO_STAR("not so good"),
    THREE_STAR("good"),
    FOUR_STAR("very good"),
    FIVE_STAR("excellent");

    private final String value;

    Rating(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Rating fromValue(String text) {
        for (Rating b : Rating.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        throw new ApiException(ExceptionStatus.INVALID_RATING, text);
    }

}