package net.nco.sklad.repository;

import net.nco.sklad.entity.AppPDF;
import net.nco.sklad.entity.documents.DocumentsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppPDFRepository extends JpaRepository<AppPDF,Long> {

    public Optional<AppPDF> findByDocumentIdAndDocumentTypeAndFirstPDF(Long documentId, DocumentsType documentType, boolean firstPDF);
}
