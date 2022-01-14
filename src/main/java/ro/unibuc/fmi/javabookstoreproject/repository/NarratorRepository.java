package ro.unibuc.fmi.javabookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.javabookstoreproject.model.Narrator;

@Repository
public interface NarratorRepository extends JpaRepository<Narrator, Long> {
}
