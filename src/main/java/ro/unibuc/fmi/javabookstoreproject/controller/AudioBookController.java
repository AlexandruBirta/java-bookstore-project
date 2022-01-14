package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.javabookstoreproject.api.AudioBookApi;
import ro.unibuc.fmi.javabookstoreproject.model.AudioBook;
import ro.unibuc.fmi.javabookstoreproject.service.AudioBookService;

import java.util.List;

@RestController
public class AudioBookController implements AudioBookApi {

    private final AudioBookService audioBookService;

    @Autowired
    public AudioBookController(AudioBookService audioBookService) {
        this.audioBookService = audioBookService;
    }

    @Override
    public void createAudioBook(AudioBook audioBook) {
        audioBookService.createAudioBook(audioBook);
    }

    @Override
    public AudioBook getAudioBookById(Long audioBookId) {
        return audioBookService.getAudioBookById(audioBookId);
    }

    @Override
    public List<AudioBook> getAudioBooks() {
        return audioBookService.getAudioBooks();
    }

    @Override
    public List<AudioBook> getAudioBooksByGenre(String genre) {
        return audioBookService.getAudioBooksByGenre(genre);
    }

    @Override
    public List<AudioBook> getAudioBooksByAuthor(String authorFirstName, String authorLastName) {
        return audioBookService.getAudioBooksByAuthor(authorFirstName, authorLastName);
    }

    @Override
    public List<AudioBook> getAudioBooksByNarrator(String narratorFirstName, String narratorLastName) {
        return audioBookService.getAudioBooksByNarrator(narratorFirstName, narratorLastName);
    }

    @Override
    public List<AudioBook> getAudioBooksByPublisher(String publisherName) {
        return audioBookService.getAudioBooksByPublisher(publisherName);
    }

    @Override
    public void deleteAudioBook(Long audioBookId) {
        audioBookService.deleteAudioBook(audioBookId);
    }

}
