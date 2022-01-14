package ro.unibuc.fmi.javabookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.javabookstoreproject.model.AudioBook;

import java.util.List;

@Repository
public interface AudioBookRepository extends JpaRepository<AudioBook, Long> {

    public List<AudioBook> findAllByGenre(@Param("genre") String genre);

    @Query(value =
            "SELECT b " +
                    "FROM AudioBook b " +
                    "WHERE b.author.firstName = :authorFirstName AND b.author.lastName = :authorLastName "
    )
    public List<AudioBook> findAllByAuthor(@Param("authorFirstName") String authorFirstName, @Param("authorLastName") String authorLastName);

    @Query(value =
            "SELECT b " +
                    "FROM AudioBook b " +
                    "WHERE b.narrator.firstName = :narratorFirstName AND b.narrator.lastName = :narratorLastName "
    )
    public List<AudioBook> findAllByNarrator(@Param("narratorFirstName") String narratorFirstName, @Param("narratorLastName") String narratorLastName);

    @Query(value =
            "SELECT b " +
                    "FROM AudioBook b " +
                    "WHERE b.publisher.name = :publisherName "
    )
    public List<AudioBook> findAllByPublisher(@Param("publisherName") String publisherName);

}
