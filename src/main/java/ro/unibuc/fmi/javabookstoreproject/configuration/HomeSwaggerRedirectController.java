package ro.unibuc.fmi.javabookstoreproject.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeSwaggerRedirectController {

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/v3/api-docs/swagger-ui.html";
    }

}
