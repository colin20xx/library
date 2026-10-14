package net.nco.sklad.repository;

import net.nco.sklad.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Integer> {
}
