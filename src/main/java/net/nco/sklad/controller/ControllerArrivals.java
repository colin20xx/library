package net.nco.sklad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerArrivals {

    @GetMapping("/arrivals")
    public String indexPage() {
        return "/arrivals_doc_form";
    }
}
