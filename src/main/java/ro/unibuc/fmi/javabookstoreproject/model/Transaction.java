package ro.unibuc.fmi.javabookstoreproject.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Schema
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @Schema
    private Long id;

    @Schema(maximum = "50", required = true)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Schema(required = true)
    @Column(nullable = false)
    private BigDecimal cost;

    @Schema
    private Integer rentalTime;

    @Schema
    private Integer subscriptionDurationInMonths;

    @Schema
    private Book.Genre subscriptionGenre;

    @Schema(required = true)
    @JoinColumn(
            name = "account_id",
            referencedColumnName = "id"
    )
    @OneToOne(
            targetEntity = Account.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    private Account account;

    @Schema
    @JoinColumn(
            name = "book_id",
            referencedColumnName = "id"
    )
    @OneToOne(
            targetEntity = Book.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE

    )
    private Book book;

    @Schema
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime insertedDate;

    @Schema
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Getter
    public enum Type {

        BOOK_PURCHASE("bookPurchase"),
        BOOK_RENTAL("bookRental"),
        BOOK_SUBSCRIPTION("bookSubscription"),
        AUDIO_BOOK_SUBSCRIPTION("audioBookSubscription");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static Type fromValue(String text) {
            for (Type b : Type.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            throw new ApiException(ExceptionStatus.INVALID_TRANSACTION_TYPE, text);
        }

    }

}