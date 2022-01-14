package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.javabookstoreproject.api.BookApi;
import ro.unibuc.fmi.javabookstoreproject.model.Book;
import ro.unibuc.fmi.javabookstoreproject.service.BookService;

import java.util.List;

@RestController
public class BookController implements BookApi {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void createBook(Book book) {
        bookService.createBook(book);
    }

    @Override
    public Book getBookById(Long bookId) {
        return bookService.getBookById(bookId);
    }

    @Override
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return bookService.getBooksByGenre(genre);
    }

    @Override
    public List<Book> getBooksByAuthor(String authorFirstName, String authorLastName) {
        return bookService.getBooksByAuthor(authorFirstName, authorLastName);
    }

    @Override
    public List<Book> getBooksByPublisher(String publisherName) {
        return bookService.getBooksByPublisher(publisherName);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookService.deleteBook(bookId);
    }

}
