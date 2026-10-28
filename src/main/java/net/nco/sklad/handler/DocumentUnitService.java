package net.nco.sklad.handler;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DocumentUnitService {
    private final Map<String, DocumentUnitHandler> handlers;

    public DocumentUnitService(List<DocumentUnitHandler> handlersList) {
        this.handlers = handlersList.stream().collect(
                Collectors.toMap(
                        DocumentUnitHandler::getDocumentTitle, Function.identity()
                )
        );
        System.out.println("[sklad]--->" + getClass() + ", size:" + handlers.size());
    }

    public void addUnit(String docTitle, Long documentId, Long unitId){

//        DocumentUnitHandler handler = handlers.get(documentType.toUpperCase(Locale.ROOT);
        DocumentUnitHandler handler = handlers.get(docTitle);
        if(handler == null){
            throw new IllegalArgumentException("No handler for title " + docTitle);
        }
        handler.addUnit(documentId, unitId);
    }
}//end class
