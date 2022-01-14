package ro.unibuc.fmi.javabookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.javabookstoreproject.model.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
