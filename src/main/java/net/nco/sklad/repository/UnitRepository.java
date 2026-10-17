package net.nco.sklad.repository;

import net.nco.sklad.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
}
