package by.academy.it.travelcompany.controller.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class MenuController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        log.debug("home controller");
        return "home";
    }
}
