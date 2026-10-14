package net.nco.sklad.controller;

import net.nco.sklad.repository.ObjectsMilitaryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings/om")
public class ObjectsMilitaryController {

    private final ObjectsMilitaryRepository objectsMilitaryRepository;

    public ObjectsMilitaryController(ObjectsMilitaryRepository objectsMilitaryRepo) {
        this.objectsMilitaryRepository = objectsMilitaryRepo;
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        objectsMilitaryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //TODO when save a list of objects, solve problem with comments adding
}
