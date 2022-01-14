package ro.unibuc.fmi.javabookstoreproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;
import ro.unibuc.fmi.javabookstoreproject.model.Book;
import ro.unibuc.fmi.javabookstoreproject.repository.BookRepository;

import java.util.List;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void createBook(Book book) {

        List<Book> repoBooks = bookRepository.findAll();

        for (Book repoBook : repoBooks) {
            if (repoBook.getIsbn().equals(book.getIsbn())) {
                throw new ApiException(ExceptionStatus.BOOK_ALREADY_EXISTS, repoBook.getIsbn());
            }
        }

        bookRepository.save(book);
        log.info("Created " + book);

    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(
                () -> new ApiException(ExceptionStatus.BOOK_NOT_FOUND, String.valueOf(bookId)));
    }

    public List<Book> getBooks() {

        List<Book> repoBooks = bookRepository.findAll();

        if (repoBooks.isEmpty()) {
            throw new ApiException(ExceptionStatus.NO_BOOKS_FOUND, "all books");
        }

        return repoBooks;

    }

    public List<Book> getBooksByGenre(String genre) {

        List<Book> repoBooksByGenre = bookRepository.findAllByGenre(genre);

        if (repoBooksByGenre.isEmpty()) {
            throw new ApiException(ExceptionStatus.NO_BOOKS_FOUND, genre + " genre");
        }

        return repoBooksByGenre;

    }

    public List<Book> getBooksByAuthor(String authorFirstName, String authorLastName) {

        List<Book> repoBooksByAuthor = bookRepository.findAllByAuthor(authorFirstName, authorLastName);

        if (repoBooksByAuthor.isEmpty()) {
            throw new ApiException(ExceptionStatus.NO_BOOKS_FOUND, "author " + authorFirstName + " " + authorLastName);
        }

        return repoBooksByAuthor;

    }

    public List<Book> getBooksByPublisher(String publisherName) {

        List<Book> repoBooksByPublisher = bookRepository.findAllByPublisher(publisherName);

        if (repoBooksByPublisher.isEmpty()) {
            throw new ApiException(ExceptionStatus.NO_BOOKS_FOUND, "publisher " + publisherName);
        }

        return repoBooksByPublisher;

    }

    public void deleteBook(Long bookId) {

        if (!bookRepository.existsById(bookId)) {
            throw new ApiException(ExceptionStatus.BOOK_NOT_FOUND, String.valueOf(bookId));
        }

        bookRepository.deleteById(bookId);
        log.info("Deleted book with id '" + bookId + "'");

    }

}
