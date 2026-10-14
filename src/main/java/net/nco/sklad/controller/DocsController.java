package net.nco.sklad.controller;

import jakarta.validation.Valid;
import net.nco.sklad.dto.InvoiceDTO;
import net.nco.sklad.entity.DepSubdepPair;
import net.nco.sklad.entity.Department;
import net.nco.sklad.entity.Documents;
import net.nco.sklad.entity.Supplier;
import net.nco.sklad.entity.documents.Invoice;
import net.nco.sklad.entity.documents.InvoiceMovement;
import net.nco.sklad.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class DocsController {

    private final AktTechConditionRepository aktTechConditionRepository;
    private SubDepRepository subDepRepository;
    private DocumentTypeRepository documentTypeRepository;
    private SupplierRepository supplierRepository;
    private DepartmentRepository departmentRepository;
    private DocumentsRepository documentsRepository;
    private InvoiceRepository invoiceRepository;
    private Department tempDep;
    private LocalDateTime tempDateTime;
    private long tempLong;
    private boolean tempBoolean;
    private InvoiceMovement im;
    private Invoice invoice;
    private InvoiceDTO invoiceDTO;
    private String tempStr, tempQuery;
    private JdbcTemplate jdbcTemplate;

    public DocsController(DocumentTypeRepository docRepository,
                          SupplierRepository suppRepository,
                          DepartmentRepository depRepository,
                          DocumentsRepository docsRepo,
                          SubDepRepository subDepRepository,
                          InvoiceRepository invcRepository,
                          JdbcTemplate jdbcTemp, AktTechConditionRepository aktTechConditionRepository) {
        this.documentTypeRepository = docRepository;
        this.supplierRepository = suppRepository;
        this.departmentRepository = depRepository;
        this.documentsRepository = docsRepo;
        this.invoiceRepository = invcRepository;
        this.jdbcTemplate = jdbcTemp;
        this.aktTechConditionRepository = aktTechConditionRepository;
    }

    @GetMapping("/docs")
    public String docs(Model model) {
//        model.addAttribute("docs", documentsRepository.findAll());
//        model.addAttribute("invoices_list", invoiceRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))) ;
        model.addAttribute("invoices_list", invoiceRepository.findAllInvoiceDTO("", "", ""));
        return "docs/docs";
    }

    @GetMapping("/new_doc_form")
    public String newDocForm(Model model) {
        List<Department> depList = departmentRepository.findByOrderBySortingNumberAsc();
        List<Supplier> suppList = supplierRepository.findByOrderByNameAsc();

        model.addAttribute("supp_list", suppList);
        model.addAttribute("dep_list", departmentRepository.findAllDepartmentsWithSubDepartments());
        model.addAttribute("doc_list", documentTypeRepository.findAll());
        model.addAttribute("new_doc", new Documents());

        return "/docs/new_doc_form";
    }
//
//    @PostMapping("/doc_content_form")
//    public String docContentForm(@Valid @ModelAttribute("new_invoice") Documents newDoc, BindingResult result, Model model,
//                                 @RequestParam Integer sender, @RequestParam Integer receiver)
//    {
//        System.out.println("------------>" + sender + " " + receiver);
//
//        //select * from sub_department where id_department = 9 and id_sub_department = 4;
//        //newDoc.setCreatedAt(LocalDateTime.now());
//        //documentsRepository.save(newDoc);
//        model.addAttribute("new_doc_created", newDoc);
//        return "/docs/add_goods_form";
//    }

    @GetMapping("/doc/checkDocNumberAndDocType")
    @ResponseBody
    public boolean checkDocNumberAndDocType(@RequestParam("id_doc_type") Integer idDocType,
                                           @RequestParam("doc_number") String docNumber, Model model) {

        switch (idDocType) {
            case 1: tempBoolean = invoiceRepository.existsByNumber(docNumber) ; break;
            case 7: tempBoolean = aktTechConditionRepository.existsByNumber(docNumber) ; break;
            default: tempBoolean = false;
        }
//        tempLong = documentsRepository.countNativeByNumberAndCond(docNumber, idDocType);

        return tempBoolean;
    }

    @GetMapping("/new_invoice_form")
    public String newInvoiceForm(Model model, @RequestParam("doc_type") String docType) {
        List<Department> depList = departmentRepository.findByOrderBySortingNumberAsc();
        List<Supplier> suppList = supplierRepository.findByOrderByNameAsc();

        model.addAttribute("supp_list", suppList);
        model.addAttribute("dep_list", departmentRepository.findAllDepartmentsWithSubDepartments());
        invoice = new Invoice();
        invoice.setDocumentsType(documentTypeRepository.findIdByTitle(docType));
//        invoice.setDocType(1);
        model.addAttribute("new_invoice", invoice);
        model.addAttribute("id_doc_type", documentTypeRepository.findIdByTitle(docType).getId());
        return "/docs/new_invoice_form";
    }

    @PostMapping("/doc/save_invoice_and_get_content_form")
    public String saveInvoiceAndGetContentForm(@Valid @ModelAttribute("new_invoice") Invoice invoice,
                                               @RequestParam(name = "serder_ids") String senderIDS,
                                               @RequestParam(name = "receiver_ids") String receiverIDS,
                                               BindingResult result,
                                               Model model) {
//        System.out.println(in);
        invoice.setSender(new DepSubdepPair(Integer.parseInt(senderIDS.split("_")[0]),Integer.parseInt(senderIDS.split("_")[1])));
        invoice.setReceiver(new DepSubdepPair(Integer.parseInt(receiverIDS.split("_")[0]),Integer.parseInt(receiverIDS.split("_")[1])));
        invoice.setCreatedAt(LocalDateTime.now());
        invoiceRepository.save(invoice);
        return "redirect:add_goods_form/" + invoice.getId();
    }

    @GetMapping("doc/add_goods_form/{idInvoice}")
    public String addGoodsToDoc(Model model, @PathVariable Long idInvoice){
        invoiceDTO = invoiceRepository.findInvoiceDTOById(idInvoice);
        model.addAttribute("invoice", invoiceDTO);

        im = new InvoiceMovement();
        im.setInvoice(invoiceRepository.getOne(idInvoice));
        im.setOperationDate(invoiceDTO.getDocumentDate().toLocalDateTime());
        model.addAttribute("im", im);
        return "/docs/add_goods_form";
    }

    @GetMapping("docs/get-last-number")
    public ResponseEntity<String> getLastNumber(Model model, @RequestParam("doc_type") int docType) {
//        tempStr = documentTypeRepository.findIdByTitle(docType).getEnglishTitle();
        tempStr = documentTypeRepository.findById(docType).getEnglishTitle();
        System.out.println("select number from sklad."+ tempStr +" order by "+ tempStr +".id desc fetch first 1 rows only");
        tempQuery = jdbcTemplate.queryForObject("select number from sklad."+ tempStr +" order by "+ tempStr +".id desc fetch first 1 rows only", String.class);
        return ResponseEntity.ok((Integer.parseInt(tempQuery.split("/")[0])+1) + "/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy")) );
    }



}// end class
