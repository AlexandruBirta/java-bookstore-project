package ro.unibuc.fmi.javabookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.javabookstoreproject.model.AccountBook;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
}
