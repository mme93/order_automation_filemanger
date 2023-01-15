package de.mameie.filemanager.document.controller;

import de.mameie.filemanager.document.service.DocumentService;
import de.mameie.filemanager.document.service.WebDavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;
    private final WebDavService webDavService;

    @Autowired
    public DocumentController(DocumentService documentService, WebDavService webDavService) {
        this.documentService = documentService;
        this.webDavService = webDavService;
    }
    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity(this.webDavService.saveFile("src/main/resources/upload/","PassFoto.jpg"),HttpStatus.OK);
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
