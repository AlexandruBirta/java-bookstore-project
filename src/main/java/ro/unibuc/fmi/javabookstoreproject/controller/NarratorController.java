package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ro.unibuc.fmi.javabookstoreproject.api.NarratorApi;
import ro.unibuc.fmi.javabookstoreproject.model.Narrator;
import ro.unibuc.fmi.javabookstoreproject.service.NarratorService;

import javax.validation.Valid;

@Controller
public class NarratorController implements NarratorApi {

    private final NarratorService narratorService;

    @Autowired
    public NarratorController(NarratorService narratorService) {
        this.narratorService = narratorService;
    }

    @Override
    public void createNarrator(@Valid Narrator narrator) {
        narratorService.createNarrator(narrator);
    }

    @Override
    public Narrator getNarratorById(Long narratorId) {
        return narratorService.getNarratorById(narratorId);
    }

    @Override
    public void deleteNarrator(Long narratorId) {
        narratorService.deleteNarrator(narratorId);
    }

}
