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
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @Schema
    private Long id;

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

    @Schema(required = true)
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

    @Schema(maximum = "50", required = true)
    @Column(nullable = false)
    private String title;

    @Schema(maximum = "256")
    private String content;

    @Schema(required = true)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Schema
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime insertedDate;

    @Schema
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

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

}