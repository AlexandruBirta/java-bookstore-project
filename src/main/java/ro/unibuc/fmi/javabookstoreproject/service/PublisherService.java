package ro.unibuc.fmi.javabookstoreproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;
import ro.unibuc.fmi.javabookstoreproject.model.Publisher;
import ro.unibuc.fmi.javabookstoreproject.repository.PublisherRepository;

import java.util.List;

@Slf4j
@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public void createPublisher(Publisher publisher) {

        List<Publisher> repoPublishers = publisherRepository.findAll();

        for (Publisher repoPublisher : repoPublishers) {
            if (repoPublisher.getName().equals(publisher.getName())) {
                throw new ApiException(ExceptionStatus.PUBLISHER_ALREADY_EXISTS, repoPublisher.getName());
            }
        }

        publisherRepository.save(publisher);
        log.info("Created " + publisher);

    }

    public Publisher getPublisherById(Long publisherId) {
        return publisherRepository.findById(publisherId).orElseThrow(
                () -> new ApiException(ExceptionStatus.PUBLISHER_NOT_FOUND, String.valueOf(publisherId)));
    }

    public void deletePublisher(Long publisherId) {

        if (!publisherRepository.existsById(publisherId)) {
            throw new ApiException(ExceptionStatus.PUBLISHER_NOT_FOUND, String.valueOf(publisherId));
        }

        publisherRepository.deleteById(publisherId);
        log.info("Deleted publisher with id '" + publisherId + "'");

    }

}
