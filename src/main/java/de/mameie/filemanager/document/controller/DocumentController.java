package de.mameie.filemanager.document.controller;

import de.mameie.filemanager.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }


    @GetMapping("/download")
    public String getFile() throws IOException {
        return this.documentService.getFile("","");
    }

    @GetMapping("/files")
    public ResponseEntity<List<String>> getFiles() throws IOException {
        return new ResponseEntity<>(this.documentService.getFiles(""), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Boolean> postFile() throws IOException {
        return new ResponseEntity<>(this.documentService.saveFile("src/main/resources/upload/","BlaBla.docx"),HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteFile() throws IOException {
        return new ResponseEntity(this.documentService.deleteFile(""),HttpStatus.OK);
    }

}
