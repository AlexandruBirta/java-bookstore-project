package ro.unibuc.fmi.javabookstoreproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;
import ro.unibuc.fmi.javabookstoreproject.model.Author;
import ro.unibuc.fmi.javabookstoreproject.repository.AuthorRepository;

import java.util.List;

@Slf4j
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void createAuthor(Author author) {

        List<Author> repoAuthors = authorRepository.findAll();

        for (Author repoAuthor : repoAuthors) {
            if (repoAuthor.getFirstName().equals(author.getFirstName()) &&
                    repoAuthor.getLastName().equals(author.getLastName())) {
                throw new ApiException(ExceptionStatus.AUTHOR_ALREADY_EXISTS, repoAuthor.getFirstName() + " " + repoAuthor.getLastName());
            }
        }

        authorRepository.save(author);
        log.info("Created " + author);

    }

    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId).orElseThrow(
                () -> new ApiException(ExceptionStatus.AUTHOR_NOT_FOUND, String.valueOf(authorId)));
    }

    public void deleteAuthor(Long authorId) {

        if (!authorRepository.existsById(authorId)) {
            throw new ApiException(ExceptionStatus.AUTHOR_NOT_FOUND, String.valueOf(authorId));
        }

        authorRepository.deleteById(authorId);
        log.info("Deleted author with id '" + authorId + "'");

    }

}
