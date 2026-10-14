package net.nco.sklad.services;

import jakarta.annotation.PostConstruct;
import net.nco.sklad.entity.AppPDF;
import net.nco.sklad.entity.documents.DocumentsType;
import net.nco.sklad.repository.AppPDFRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {

    private final AppPDFRepository appPDFRepository;

    @Value("${sklad.upload-dir}")
    private String uploadDir;

    public FileStorageService(AppPDFRepository appPDFRepo) {
        this.appPDFRepository = appPDFRepo;
    }

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(uploadDir));
    }

    public String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString();

        Path destination = Paths.get(uploadDir).resolve(fileName);
        Files.copy(file.getInputStream(), destination);

        return destination.toString();
    }

    public Optional<AppPDF> getFirstPDF(Long idDoc, DocumentsType documentsType, boolean firstPDF){
        return appPDFRepository.findByDocumentIdAndDocumentTypeAndFirstPDF(idDoc, documentsType, firstPDF);
    }


}//end class
