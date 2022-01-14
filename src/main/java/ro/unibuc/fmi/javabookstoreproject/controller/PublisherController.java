package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.javabookstoreproject.api.PublisherApi;
import ro.unibuc.fmi.javabookstoreproject.model.Publisher;
import ro.unibuc.fmi.javabookstoreproject.service.PublisherService;

@RestController
public class PublisherController implements PublisherApi {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Override
    public void createPublisher(Publisher publisher) {
        publisherService.createPublisher(publisher);
    }

    @Override
    public Publisher getPublisherById(Long publisherId) {
        return publisherService.getPublisherById(publisherId);
    }

    @Override
    public void deletePublisher(Long publisherId) {
        publisherService.deletePublisher(publisherId);
    }

}
