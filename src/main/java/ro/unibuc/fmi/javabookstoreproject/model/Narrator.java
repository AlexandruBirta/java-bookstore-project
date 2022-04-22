package ro.unibuc.fmi.javabookstoreproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Schema
@Entity
@Table(name = "narrator")
public class Narrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @Schema
    private Long id;

    @Schema(maximum = "50", required = true)
    @Column(nullable = false)
    private String firstName;

    @Schema(maximum = "50", required = true)
    @Column(nullable = false)
    private String lastName;

    @Schema(required = true)
    @JoinColumn(
            name = "narrator_id",
            referencedColumnName = "id"
    )
    @OneToMany(
            targetEntity = AudioBook.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    private List<AudioBook> audioBook;

    @Schema
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime insertedDate;

    @Schema
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

}