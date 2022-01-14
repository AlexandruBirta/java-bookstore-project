package ro.unibuc.fmi.javabookstoreproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;
import ro.unibuc.fmi.javabookstoreproject.model.Narrator;
import ro.unibuc.fmi.javabookstoreproject.repository.NarratorRepository;

import java.util.List;

@Slf4j
@Service
public class NarratorService {

    private final NarratorRepository narratorRepository;

    @Autowired
    public NarratorService(NarratorRepository narratorRepository) {
        this.narratorRepository = narratorRepository;
    }

    @Transactional
    public void createNarrator(Narrator narrator) {

        List<Narrator> repoNarrators = narratorRepository.findAll();

        for (Narrator repoNarrator : repoNarrators) {
            if (repoNarrator.getFirstName().equals(narrator.getFirstName()) &&
                    repoNarrator.getLastName().equals(narrator.getLastName())) {
                throw new ApiException(ExceptionStatus.NARRATOR_ALREADY_EXISTS, repoNarrator.getFirstName() + " " + repoNarrator.getLastName());
            }
        }

        narratorRepository.save(narrator);
        log.info("Created " + narrator);

    }

    public Narrator getNarratorById(Long narratorId) {
        return narratorRepository.findById(narratorId).orElseThrow(
                () -> new ApiException(ExceptionStatus.NARRATOR_NOT_FOUND, String.valueOf(narratorId)));
    }

    public void deleteNarrator(Long narratorId) {

        if (!narratorRepository.existsById(narratorId)) {
            throw new ApiException(ExceptionStatus.NARRATOR_NOT_FOUND, String.valueOf(narratorId));
        }

        narratorRepository.deleteById(narratorId);
        log.info("Deleted narrator with id '" + narratorId + "'");

    }

}
