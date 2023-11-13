package zerobase23.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import zerobase23.demo.service.WebService;


@Controller
public class WebController{

    private final WebService webService;


    public WebController(WebService webService) {
        this.webService = webService;

    }

    @GetMapping("/")
    public String createWeb(){
        for(int i= 0; i<20; i++) {
            webService.createWeb(i);
        }

        return "home";
    }


    @GetMapping("/viewData")
    public String boardList(Model model){

        model.addAttribute("list", webService.webList());

        return "index";
    }
}

