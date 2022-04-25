package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import ro.unibuc.fmi.javabookstoreproject.api.BookApi;
import ro.unibuc.fmi.javabookstoreproject.model.Book;
import ro.unibuc.fmi.javabookstoreproject.model.Genre;
import ro.unibuc.fmi.javabookstoreproject.service.BookService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController implements BookApi {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String createBook(@ModelAttribute @Valid Book book) {
        bookService.createBook(book);
        return "create_book.html";
    }

    @Override
    public String createNewBook(Model model) {
        model.addAttribute("book", new Book());
        return "create_book.html";
    }

    @Override
    public String getBookById(Long bookId, Model model) {
        model.addAttribute("book", bookService.getBookById(bookId));
        return "get_book_by_id.html";
    }

    @Override
    public String getBooks(int pageIndex, int pageSize, Model model) {
        model.addAttribute("books", bookService.getBooks(pageIndex, pageSize).getContent());
        return "books.html";
    }

    @Override
    public String getBooksByGenre(Genre genre, Model model) {
        model.addAttribute("books", bookService.getBooksByGenre(genre));
        return "books.html";
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