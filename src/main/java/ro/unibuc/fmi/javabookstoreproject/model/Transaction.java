package ro.unibuc.fmi.javabookstoreproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private TransactionType type;

    @Schema(required = true)
    @Column(nullable = false)
    private BigDecimal cost;

    @Schema
    private Integer rentalTimeInDays;

    @Schema
    private Integer subscriptionDurationInMonths;

    @Schema
    private Genre subscriptionGenre;

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

}