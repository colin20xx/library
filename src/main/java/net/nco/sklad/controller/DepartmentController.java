package net.nco.sklad.controller;

import net.nco.sklad.entity.Department;
import net.nco.sklad.repository.DepartmentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/deps")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/units")
    public String units(Model model) {
        model.addAttribute("departments", departmentRepository.findAll(Sort.by(Sort.Direction.ASC, "sortingNumber")));
        return "deps/units";
    }


}
