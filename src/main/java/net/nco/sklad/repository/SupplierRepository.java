package net.nco.sklad.repository;

import net.nco.sklad.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    List<Supplier> findByOrderByNameAsc();
}
