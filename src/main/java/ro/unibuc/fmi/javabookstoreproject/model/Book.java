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
@EqualsAndHashCode
@Builder
@ToString
@Schema
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @Schema
    private Long id;

    @Schema(maximum = "100", required = true)
    @Column(nullable = false)
    private String name;

    @Schema(maximum = "255", required = true)
    @Column(nullable = false)
    private String description;

    @Schema(required = true)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Schema(maximum = "13", required = true)
    @Column(nullable = false)
    private String isbn;

    @Schema(required = true)
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
    )
    @ManyToOne(
            targetEntity = Author.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    private Author author;

    @Schema(required = true)
    @JoinColumn(
            name = "publisher_id",
            referencedColumnName = "id"
    )
    @ManyToOne(
            targetEntity = Publisher.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    private Publisher publisher;

    @Schema
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime insertedDate;

    @Schema
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

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

}