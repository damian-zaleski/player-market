package pl.degath.application.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String toSwagger() {
        return "redirect:/swagger-ui/";
    }
}
