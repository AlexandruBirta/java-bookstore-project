package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import ro.unibuc.fmi.javabookstoreproject.api.PublisherApi;
import ro.unibuc.fmi.javabookstoreproject.model.Publisher;
import ro.unibuc.fmi.javabookstoreproject.service.PublisherService;

import javax.validation.Valid;

@Controller
public class PublisherController implements PublisherApi {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Override
    public String createPublisher(@ModelAttribute @Valid Publisher publisher) {
        publisherService.createPublisher(publisher);
        return "create_publisher.html";
    }

    @Override
    public String createPublisher(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "create_publisher.html";
    }

    @Override
    public String getPublisherById(Long publisherId, Model model) {
        model.addAttribute("publisher", publisherService.getPublisherById(publisherId));
        return "get_publisher_by_id.html";
    }

    @Override
    public void deletePublisher(Long publisherId) {
        publisherService.deletePublisher(publisherId);
    }

}
