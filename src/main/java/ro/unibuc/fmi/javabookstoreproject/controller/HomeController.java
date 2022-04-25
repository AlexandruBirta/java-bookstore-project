package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.stereotype.Controller;
import ro.unibuc.fmi.javabookstoreproject.api.HomeApi;

@Controller
public class HomeController implements HomeApi {

    @Override
    public String getIndex() {
        return "index.html";
    }

}