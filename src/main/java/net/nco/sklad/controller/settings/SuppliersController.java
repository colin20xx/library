package net.nco.sklad.controller.settings;

import net.nco.sklad.repository.SupplierRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuppliersController {

    private SupplierRepository supplierRepository;

    public SuppliersController(SupplierRepository suppRepository) {
        this.supplierRepository = suppRepository;

    }//constructor end

    @GetMapping("/settings/suppliers_list")
    public String suppliersList(Model model) {
        model.addAttribute("suppliers_list", supplierRepository.findAll());
        return "settings_suppliers";
    }
}//class end
