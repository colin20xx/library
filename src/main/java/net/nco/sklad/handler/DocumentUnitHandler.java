package net.nco.sklad.handler;

public interface DocumentUnitHandler {

    String getDocumentTitle();

    void addUnit(Long documentId, Long unitId);
}
