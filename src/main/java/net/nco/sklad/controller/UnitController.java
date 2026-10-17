package net.nco.sklad.controller;

import net.nco.sklad.entity.Unit;
import net.nco.sklad.repository.UnitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unit")
public class UnitController {

    private final UnitRepository unitRepository;

    public UnitController(UnitRepository unitRepo) {
        this.unitRepository = unitRepo;
    }

    @GetMapping("/new-form")
    public String newForm(Model model) {
        model.addAttribute("unit", new Unit());
        return "/";
    }
}//end class
