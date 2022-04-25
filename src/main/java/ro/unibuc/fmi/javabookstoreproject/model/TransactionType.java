package ro.unibuc.fmi.javabookstoreproject.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;

@Getter
public enum TransactionType {

    BOOK_PURCHASE("bookPurchase"),
    BOOK_RENTAL("bookRental"),
    BOOK_SUBSCRIPTION("bookSubscription"),
    AUDIO_BOOK_SUBSCRIPTION("audioBookSubscription");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static TransactionType fromValue(String text) {
        for (TransactionType b : TransactionType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        throw new ApiException(ExceptionStatus.INVALID_TRANSACTION_TYPE, text);
    }

}