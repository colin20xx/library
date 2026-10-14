package net.nco.sklad.repository;

import net.nco.sklad.entity.documents.DocumentsType;
import net.nco.sklad.entity.history.DocumentCommitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentCommitHistoryRepository extends JpaRepository<DocumentCommitHistory, Long> {
    List<DocumentCommitHistory> findByDocumentIdAndDocumentTypeOrderByRecordDateTimeDesc(Long documentId, DocumentsType documentType);
}
