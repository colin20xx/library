package net.nco.sklad.controller;

import jakarta.validation.Valid;
import net.nco.sklad.common.Commands;
import net.nco.sklad.common.DepartTypes;
import net.nco.sklad.entity.*;
import net.nco.sklad.entity.documents.DocumentsType;
import net.nco.sklad.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SettingsController {
    private final ObjectsMilitaryRepository objectsMilitaryRepository;
    private DepartmentRepository departmentRepository;
    private RepoDocument repoDocument;
    private Department tempDepartment;
    private RankRepository rankRepository;
    private SubDepRepository subDepRepository;
    private MilitaryObjectsRepository militaryObjectsRepository;
    private DenominationRepository denominationRepository;
    private NomenclatureRepository nomenclatureRepository;
    private String[] tempArrStr;
    private Commands commands;
    private SubDepartment subDepartmentTemp;
    private DepsTypesRepository depsTypesRepository;

    public SettingsController(DepartmentRepository departmentRepository, RepoDocument repoDocument, RankRepository rankRepo, SubDepRepository subDepRepo, MilitaryObjectsRepository milObjRepository, DenominationRepository denomRepo, NomenclatureRepository nomenclatureRepo, ObjectsMilitaryRepository objectsMilitaryRepository, DepsTypesRepository depsTypesRepo) {
        this.departmentRepository = departmentRepository;
        this.repoDocument = repoDocument;
        this.rankRepository = rankRepo;
        this.subDepRepository = subDepRepo;
        this.militaryObjectsRepository = milObjRepository;
        this.denominationRepository = denomRepo;
        this.nomenclatureRepository = nomenclatureRepo;
        this.objectsMilitaryRepository = objectsMilitaryRepository;
        this.depsTypesRepository = depsTypesRepo;
    }

    @GetMapping("/settings/deps")
    public String settingsDeps(Model model) {
        model.addAttribute("deps", departmentRepository.findByActiveDepTrue(Sort.by(Sort.Direction.ASC, "sortingNumber")));
        model.addAttribute("activeMenu", "deps");
        return "/settings_deps";
    }

    @GetMapping("/settings/{depId}")
    public String settingsDeps(@PathVariable int depId, Model model) {

        this.tempDepartment = departmentRepository.findById(depId).get();
        List<SubDepartment> subDepartmentList = subDepRepository.findByIdDepartmentOrderById(this.tempDepartment);

        model.addAttribute("dep", tempDepartment);
        model.addAttribute("subDeps", tempDepartment.getShortName());
        model.addAttribute("sub_dep_list", subDepartmentList);
        model.addAttribute("commands", Commands.values());
        return "/settings_dep_details";
    }

    @GetMapping("/settings/docs_list")
    public String settingsDocsList(Model model) {
        model.addAttribute("doc_obj", new DocumentsType());
        model.addAttribute("doc_list", repoDocument.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "/settings_docs_list";
    }

    @PostMapping("/settings/save_new_doc")
    public String settingsAddDocument(@ModelAttribute("doc_obj") DocumentsType newDoc, Model model) {
        repoDocument.save(newDoc);
        model.addAttribute("doc_obj", newDoc);
        model.addAttribute("doc_list", repoDocument.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "/settings_docs_list";
    }

    @GetMapping("/settings/deps/formaddnewdep")
    public String settingsAddDep(@RequestParam boolean disformed, Model model) {
        List<Rank> listRanks = rankRepository.findAll();
        model.addAttribute("ranks_list", listRanks);

        List<Department> depsList = departmentRepository.findByOrderBySortingNumberAsc();
        model.addAttribute("depsList", depsList);
        model.addAttribute("dep_types", depsTypesRepository.findAll());
        model.addAttribute("selected_rank", new Rank());
        Department newDep = new Department();
        newDep.setCreatedAtDate(LocalDate.now());
        newDep.setCreatedAtDate(LocalDate.now());
        model.addAttribute("new_dep", newDep);

        return "deps/form_add_new_dep";

    }

    @GetMapping("/settings/dep/formaddnewsubdep")
    public String settingsAddSubDep(@RequestParam int iddep, Model model) {
        this.tempDepartment = departmentRepository.findById(iddep).get();
        model.addAttribute("dep", tempDepartment);
        model.addAttribute("new_sub_dep", new SubDepartment());

        return "deps/form_add_new_sub_dep";
    }

    @PostMapping("/settings/deps/savenewdep")
    public String settingsSaveDep(@ModelAttribute("new_dep") Department department, Model model) {
        department.setCreatedAtDate(LocalDate.now());
        department.setCreatedAtTime(LocalTime.now());
        department.setShortName(department.getShortName().toUpperCase());
        department.setActiveDep(true);
        departmentRepository.save(department);

        model.addAttribute("deps", departmentRepository.findByActiveDepTrue(Sort.by(Sort.Direction.ASC, "sortingNumber")));

        return "/settings_deps";
    }

    @PostMapping("/settings/deps/savenewsubdep")
    public String settingsSaveDep(@ModelAttribute("new_sub_dep") SubDepartment subDepartment, @RequestParam int iddep) {
        subDepartment.setIdDepartment(departmentRepository.findById(iddep).get());
        subDepRepository.save(subDepartment);
        return "/settings_deps";
    }

    @GetMapping("/settings/dep/exec-cmd")
    public ResponseEntity<?> settingsExecCmd(@RequestParam String cmdType, @RequestParam int iddep) {
        switch (cmdType) {
            case "cmd_del_dep":
                tempDepartment = departmentRepository.findById(iddep).get();
                tempDepartment.setDeletedAt(LocalDateTime.now());
                tempDepartment.setActiveDep(false);
                departmentRepository.save(tempDepartment);
                break;
            case "cmd_change_status":
                tempDepartment = departmentRepository.findById(iddep).get();
                tempDepartment.setUpdatedAtDate(LocalDate.now());
                tempDepartment.setUpdatedAtTime(LocalTime.now());
                tempDepartment.setDisformed(!tempDepartment.isDisformed());
                departmentRepository.save(tempDepartment);
                break;
            default:
                break;
        }
        return ResponseEntity.ok(cmdType + " > " + iddep);
    }

    @GetMapping("/settings/deps/dep-exists")
    public ResponseEntity<?> settingsDepExists(@RequestParam String depTitle) {
        departmentRepository.findByShortName(depTitle);
        if (departmentRepository.findByShortName(depTitle) == null) {
            return ResponseEntity.notFound().build();

        } else {
            return ResponseEntity.ok(true);
        }
    }

    @GetMapping("/settings/military_objects")
    public String settingsMilitaryObjects(Model model) {
        model.addAttribute("mil_obj", militaryObjectsRepository.findAll(Sort.by(Sort.Direction.ASC, "objectName")));
        model.addAttribute("new_milit_obj", new ObjectsMilitary());
        return "settings_military_objects";
    }

    @GetMapping("/settings/denominations")
    public String settingsDenominations(Model model) {
        //model.addAttribute("denom_list", denominationRepository.findDenominationsNative(Sort.by(Sort.Direction.ASC, "denominationName")));
        List<ObjectsMilitary> omList = objectsMilitaryRepository.findAll();
        List<Object> denomList = denominationRepository.findDenominationsNative(Sort.by(Sort.Direction.ASC, "denomination_name"));

        model.addAttribute("om_list", omList);
        model.addAttribute("denomList", denomList);
        model.addAttribute("new_denom", new Denomination());
        return "settings_denominations";
    }//method end

    @PostMapping("/settings/denominations/save_new_denomination")
    public String saveNewDenomination(@ModelAttribute Denomination denomination, @RequestParam Integer om_id){
        System.out.println("om_id:" + om_id+" > "+ denomination.toString());
        denomination.setParentObjMil(objectsMilitaryRepository.findById(om_id).get());
        denominationRepository.save(denomination);
        return "redirect:/settings/denominations";
    }



    @PostMapping("/settings/sub-deps/change_dep_position")
    public ResponseEntity<String> settingsSubDepPosition(@RequestParam String iddep, String cmd_type) {
        System.out.println(iddep + " > " + cmd_type);
        commands = Commands.CMD_MOVE_UP_DEPARTMENT;

        // 1_9 > CMD_MOVE_UP_DEPARTMENT

        switch (commands){
            case CMD_MOVE_UP_DEPARTMENT:
                System.out.println(iddep + " > move up");
                break;
            case CMD_MOVE_DOWN_DEPARTMENT:
                System.out.println(iddep + " > move down");
                break;
                default:
                    System.out.println("uknown command");
                    break;
        };

//TODO        tempArrStr = split(iddep)



//        select * from sub_department where id_department=9 and id_sub_department=8;
//        UPDATE sklad.sub_department SET sorting_number = '9_5' WHERE id_sub_department = 8::integer



        return ResponseEntity.ok("ok");
    }


    @GetMapping("/settings/registerRespPersone")
    public ResponseEntity<String> registerRespPersone(@RequestParam String respPersone, Integer idSubDep) {
        subDepartmentTemp = subDepRepository.findById(idSubDep).get();
        subDepartmentTemp.setResponsiblePerson(respPersone);
        subDepRepository.save(subDepartmentTemp);

        return ResponseEntity.ok("ok");
    }

    @PostMapping("/settings/save_new_milit_obj")
    public String saveNewMilitaryObj(@Valid @ModelAttribute("new_milit_obj") ObjectsMilitary objectsMilitary, BindingResult bindingResult, Model model,
                                     RedirectAttributes ra) {
        if(militaryObjectsRepository.existsByObjectName(objectsMilitary.getObjectName())){
            bindingResult.rejectValue("objectName", "duplicate", "Об'єкт вже існує!");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("mil_obj", militaryObjectsRepository.findAll(Sort.by(Sort.Direction.ASC, "objectName")));
            return "settings_military_objects";
        }

        objectsMilitary.setCreatedAt(LocalDateTime.now());
        militaryObjectsRepository.save(objectsMilitary);

        ra.addFlashAttribute("successMessage","Об'єкт внесено!");
        return "redirect:/settings/military_objects";
    }//end method

    @PostMapping("/settings/save_new_milit_obj_list")
    public String saveNewMilitaryObjList(@RequestParam("objects_list")String objectsList,
                                         @RequestParam("objects_comment_list")String objectsCommentList  ,Model model) {
        if(objectsList.contains(",")){
            tempArrStr  = objectsList.split(",");
            if(objectsCommentList.length()>1) {
                String[] descArr = objectsCommentList.split(",");
            }
            if (tempArrStr.length>1){
                ObjectsMilitary objM;
                List<ObjectsMilitary> omList = new ArrayList<>();
                for (int i=0; i<tempArrStr.length; i++) {
                    objM = new ObjectsMilitary();
                    objM.setObjectName(tempArrStr[i].trim());

//                    objM.setDescription(descArr[i].trim());

                    objM.setCreatedAt(LocalDateTime.now());
                    omList.add(objM);
                }
                militaryObjectsRepository.saveAll(omList);
                omList.clear();

            }else{
                ObjectsMilitary om = new ObjectsMilitary();
                om.setObjectName(tempArrStr[0]);
                om.setCreatedAt(LocalDateTime.now());
                militaryObjectsRepository.save(om);
            }
        }
        model.addAttribute("new_milit_obj", new ObjectsMilitary());
        model.addAttribute("mil_obj", militaryObjectsRepository.findAll(Sort.by(Sort.Direction.ASC, "objectName")));
        return "settings_military_objects";
    }

    @GetMapping("/settings/objectMilitaryCRUD")
    public ResponseEntity<?> objectMilitaryAjaxCRUD(@RequestParam Integer idObject, String cmdType) {
        System.out.println(idObject + " > " + cmdType);
        commands = Commands.valueOf(cmdType.toUpperCase());
        switch (commands){
            case  CMD_DELETE:
                militaryObjectsRepository.deleteById(idObject);
                break;
            default: return ResponseEntity.notFound().build();
        };

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/settings/deps_types")
    public String depsTypes(Model model) {
        model.addAttribute("deps_types", depsTypesRepository.findAll());
        return "settings_deps_types";
    }

    @GetMapping("/settings/shtat")
    public String depsShtat(Model model) {
        model.addAttribute("activeMenu", "shtat");
        return "settings_shtat";
    }
}//end class
