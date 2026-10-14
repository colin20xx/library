package net.nco.sklad.repository;

import net.nco.sklad.entity.documents.DocumentsType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoDocument extends JpaRepository<DocumentsType, Integer> {
}
