package ro.unibuc.fmi.javabookstoreproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "account_books")
public class AccountBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

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

    @JoinColumn(
            name = "audio_book_id",
            referencedColumnName = "id"
    )
    @OneToOne(
            targetEntity = AudioBook.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    private AudioBook audioBook;

    @Schema
    private Integer rentalDurationInDays;

    @Schema
    private Integer subscriptionDurationInMonths;

    @CreationTimestamp
    private LocalDateTime insertedDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;
}

