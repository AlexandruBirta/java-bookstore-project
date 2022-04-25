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
@Schema
@Entity
@Table(name = "audio_book")
public class AudioBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @Schema
    private Long id;

    @Schema(maximum = "100", required = true)
    @Column(nullable = false)
    private String name;

    @Schema(maximum = "256", required = true)
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

    @Schema(required = true)
    @JoinColumn(
            name = "narrator_id",
            referencedColumnName = "id"
    )
    @ManyToOne(
            targetEntity = Narrator.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    private Narrator narrator;

    @Schema(required = true)
    @Column(nullable = false)
    private Long durationInMinutes;

}
