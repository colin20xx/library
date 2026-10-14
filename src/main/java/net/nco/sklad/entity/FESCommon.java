package net.nco.sklad.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "fes_common", schema = "sklad")
public class FESCommon {

    @Id
    @Column(name = "id_record", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_record;

    private String account;         //    План рахунків	string
    private String finSource;       //    Джерела фінансування	string
    private String kekv;            //    КЕКВ	int
    private String serviceName;     //    Служба	string
    private String department;      //    Підрозділи	string
    private String placeHolder;     //    Місця зберігання	string
    private Long code;                  //    Код	long
    private String nomenclature;          //    ТМЦ	string
    private String partia;          //    Партія	string
    private String misure;          //    Од. виміру	string
    private String converted;       //    converted	string
    private String serialNumber;    //    Заводський №	string
    private Date dateIn;         //    Дата введення	date
    private String status;          //    Статус	string
    private String nomenclatureCode;   //    Код номенклатури	long
    private String passportNumber;  //    № паспорта	string
    private String documentType;    //    Документ	string
    private String reason;          //    Підстава №	string
    private Date reasonDate;            //    Підстава дата	date
    private String confirmation;    //    Підтвердження №	string
    private Date confirmationDate;    //    Підтвердження дата	date
    private String comments;        //    Примітка	string
    private String expenceCode;             //    Код видатків	string
    private String budgetSubProgram;        //    Бюджетна підпрограма	string
    private Double price;                   //    Ціна	double
    private Double quantityBegin;           //    К-ть на початок	double(6,5)
    private Double sumBegin;            //    Сума на початок	double
    private Double quantityDebet;       //    К-ть ДТ	double
    private Double sumDebet;            //    Сума ДТ	double
    private Double quantityCredit;      //    К-ть КТ	double
    private Double sumCredit;           //    Сума КТ	double
    private Double quantityEnd;         //    К-ть на кінець	double
    private Double sumEnd;              //    Сума на кінець	double
    private Double sumUsage;            //    Сума зносу	double
    private Double overpricedUsage;     //    Переоцінений знос	double
    private Double overpricedCost;      //    Переоцінена вартість	double
    private Double costEnd;             //    Залишкова вартісь	double
    private String furnisher;           //    Постачальник	string
    private String nomenclator;         //    Номенклатор	string

    public FESCommon() {}

}
