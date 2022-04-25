package ro.unibuc.fmi.javabookstoreproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.javabookstoreproject.model.Book;
import ro.unibuc.fmi.javabookstoreproject.model.Genre;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByGenre(@Param("genre") Genre genre);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM book " +
                    "WHERE genre = :genre " +
                    "ORDER BY random() LIMIT 1")
    Book getRandomByGenre(@Param("genre") String genre);

    @Query(value =
            "SELECT b " +
                    "FROM Book b " +
                    "WHERE b.author.firstName = :authorFirstName AND b.author.lastName = :authorLastName "
    )
    List<Book> findAllByAuthor(@Param("authorFirstName") String authorFirstName, @Param("authorLastName") String authorLastName);

    @Query(value =
            "SELECT b " +
                    "FROM Book b " +
                    "WHERE b.publisher.name = :publisherName "
    )
    List<Book> findAllByPublisher(@Param("publisherName") String publisherName);

    @Query(
            value = "SELECT b " +
                    "FROM Book b "
    )
    Page<Book> findAllPaginated(Pageable page);

}