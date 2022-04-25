package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import ro.unibuc.fmi.javabookstoreproject.api.AuthorApi;
import ro.unibuc.fmi.javabookstoreproject.model.Author;
import ro.unibuc.fmi.javabookstoreproject.service.AuthorService;

import javax.validation.Valid;

@Controller
public class AuthorController implements AuthorApi {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public String createAuthor(@ModelAttribute @Valid Author author) {
        authorService.createAuthor(author);
        return "create_author.html";
    }

    @Override
    public String createAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "create_author.html";
    }

    @Override
    public String getAuthorById(Long authorId, Model model) {
        model.addAttribute("author", authorService.getAuthorById(authorId));
        return "get_author_by_id.html";
    }

    @Override
    public void deleteAuthor(Long authorId) {
        authorService.deleteAuthor(authorId);
    }

}
