package ro.unibuc.fmi.javabookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.javabookstoreproject.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
