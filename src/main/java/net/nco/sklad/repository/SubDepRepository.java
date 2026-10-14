package net.nco.sklad.repository;

import net.nco.sklad.entity.Department;
import net.nco.sklad.entity.SubDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubDepRepository extends JpaRepository<SubDepartment, Integer> {
    List<SubDepartment> findByIdDepartmentOrderById(Department idDepartment);

//    SubDepartment findByIdDepartmentAndIdSubDepartment(Integer idDepartment, Integer subDepartment);

//    @Query(value = "select * from sklad.sub_department where id_department = 9 and id_sub_department = 4", nativeQuery = true)
//    SubDepartment findSubDepartmentByIds();
}
