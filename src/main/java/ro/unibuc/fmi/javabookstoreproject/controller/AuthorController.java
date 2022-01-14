package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.javabookstoreproject.api.AuthorApi;
import ro.unibuc.fmi.javabookstoreproject.model.Author;
import ro.unibuc.fmi.javabookstoreproject.service.AuthorService;

@RestController
public class AuthorController implements AuthorApi {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void createAuthor(Author author) {
        authorService.createAuthor(author);
    }

    @Override
    public Author getAuthorById(Long authorId) {
        return authorService.getAuthorById(authorId);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        authorService.deleteAuthor(authorId);
    }

}
