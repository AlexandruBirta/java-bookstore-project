package ro.unibuc.fmi.javabookstoreproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;
import ro.unibuc.fmi.javabookstoreproject.model.AudioBook;
import ro.unibuc.fmi.javabookstoreproject.repository.AudioBookRepository;

import java.util.List;

@Slf4j
@Service
public class AudioBookService {

    private final AudioBookRepository audioBookRepository;

    @Autowired
    public AudioBookService(AudioBookRepository audioBookRepository) {
        this.audioBookRepository = audioBookRepository;
    }

    @Transactional
    public void createAudioBook(AudioBook audioBook) {

        List<AudioBook> repoAudioBooks = audioBookRepository.findAll();

        for (AudioBook repoAudioBook : repoAudioBooks) {

            if (repoAudioBook.getIsbn().equals(audioBook.getIsbn())) {
                throw new ApiException(ExceptionStatus.AUDIO_BOOK_ALREADY_EXISTS, repoAudioBook.getIsbn());
            }
        }

        audioBookRepository.save(audioBook);
        log.info("Created " + audioBook);

    }

    public AudioBook getAudioBookById(Long audioBookId) {
        return audioBookRepository.findById(audioBookId).orElseThrow(
                () -> new ApiException(ExceptionStatus.AUDIO_BOOK_NOT_FOUND, String.valueOf(audioBookId)));
    }

    public List<AudioBook> getAudioBooks() {

        List<AudioBook> repoAudioBooks = audioBookRepository.findAll();

        if (repoAudioBooks.isEmpty()) {
            throw new ApiException(ExceptionStatus.NO_AUDIO_BOOKS_FOUND, "all audioBooks");
        }

        return repoAudioBooks;

    }

    public List<AudioBook> getAudioBooksByGenre(String genre) {

        List<AudioBook> repoAudioBooksByGenre = audioBookRepository.findAllByGenre(genre);

        if (repoAudioBooksByGenre.isEmpty()) {
            throw new ApiException(ExceptionStatus.NO_AUDIO_BOOKS_FOUND, genre + " genre");
        }

        return repoAudioBooksByGenre;

    }

    public List<AudioBook> getAudioBooksByAuthor(String authorFirstName, String authorLastName) {

        List<AudioBook> repoAudioBooksByAuthor = audioBookRepository.findAllByAuthor(authorFirstName, authorLastName);

        if (repoAudioBooksByAuthor.isEmpty()) {
            throw new ApiException(ExceptionStatus.NO_AUDIO_BOOKS_FOUND, "author " + authorFirstName + " " + authorLastName);
        }

        return repoAudioBooksByAuthor;

    }

    public List<AudioBook> getAudioBooksByNarrator(String narratorFirstName, String narratorLastName) {

        List<AudioBook> repoAudioBooksByNarrator = audioBookRepository.findAllByNarrator(narratorFirstName, narratorLastName);

        if (repoAudioBooksByNarrator.isEmpty()) {
            throw new ApiException(ExceptionStatus.NO_AUDIO_BOOKS_FOUND, "narrator " + narratorFirstName + " " + narratorLastName);
        }

        return repoAudioBooksByNarrator;

    }

    public List<AudioBook> getAudioBooksByPublisher(String publisherName) {

        List<AudioBook> repoAudioBooksByPublisher = audioBookRepository.findAllByPublisher(publisherName);

        if (repoAudioBooksByPublisher.isEmpty()) {
            throw new ApiException(ExceptionStatus.NO_AUDIO_BOOKS_FOUND, "publisher " + publisherName);
        }

        return repoAudioBooksByPublisher;

    }

    public void deleteAudioBook(Long audioBookId) {

        if (!audioBookRepository.existsById(audioBookId)) {
            throw new ApiException(ExceptionStatus.AUDIO_BOOK_NOT_FOUND, String.valueOf(audioBookId));
        }

        audioBookRepository.deleteById(audioBookId);
        log.info("Deleted audio book with id '" + audioBookId + "'");

    }

}
