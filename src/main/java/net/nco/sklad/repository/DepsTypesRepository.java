package net.nco.sklad.repository;

import net.nco.sklad.entity.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepsTypesRepository extends JpaRepository<DepartmentType, Integer> {
}
