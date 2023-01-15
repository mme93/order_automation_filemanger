package de.mameie.filemanager.document.controller;

import de.mameie.filemanager.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> postFile(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity(this.documentService.saveFile(file),HttpStatus.OK);
    }

    @GetMapping("/download")
    public String getFile() throws IOException {
        return this.documentService.getFile("", "");
    }

    @GetMapping("/files")
    public ResponseEntity<List<String>> getFiles() throws IOException {
        return new ResponseEntity<>(this.documentService.getFiles(""), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteFile() throws IOException {
        return new ResponseEntity(this.documentService.deleteFile(""), HttpStatus.OK);
    }

}
