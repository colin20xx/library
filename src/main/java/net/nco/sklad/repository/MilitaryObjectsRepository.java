package net.nco.sklad.repository;

import net.nco.sklad.entity.ObjectsMilitary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilitaryObjectsRepository extends JpaRepository<ObjectsMilitary, Integer> {

    boolean existsByObjectName(String objectName);

}
