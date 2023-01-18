package de.mameie.filemanager.document.controller;

import de.mameie.filemanager.document.service.DocumentService;
import de.mameie.filemanager.document.service.WebDavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://services-meier.de")
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;
    private final WebDavService webDavService;

    @Autowired
    public DocumentController(DocumentService documentService, WebDavService webDavService) {
        this.documentService = documentService;
        this.webDavService = webDavService;
    }

    @GetMapping("/path")
    public void getPath() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/upload");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = reader.readLine()) != null){
            System.out.println(line);
        }
        reader.close();
    }
    @PostMapping(value = "/upload/single")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        if (this.documentService.saveFile(file)) {
            this.webDavService.saveFile(file.getOriginalFilename(), "kfz/");
        }
        return new ResponseEntity<>("File(s) uploaded successfully!", HttpStatus.OK);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploads(
            @RequestParam("file") List<MultipartFile> files
    ) {
        for (MultipartFile file : files) {
            if (this.documentService.saveFile(file)) {
                this.webDavService.saveFile(file.getOriginalFilename(), "kfz/");
            }
        }
        return new ResponseEntity<>("File(s) uploaded successfully!", HttpStatus.OK);
    }

    @PostMapping("/createFolder/{folder}")
    public ResponseEntity<String> createFolder(@PathVariable("folder") String folder) {
        this.webDavService.createFolder(folder);
        return new ResponseEntity<>("File(s) uploaded successfully!", HttpStatus.OK);
    }

    @GetMapping("/download")
    public String getFile() throws IOException {
        return this.documentService.getFile("", "");
    }

    @GetMapping("/files")
    public ResponseEntity<List<String>> getFiles() throws IOException {
        return new ResponseEntity<>(this.documentService.getFiles(""), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{path}/{filename}")
    public ResponseEntity<Boolean> deleteFile(@PathVariable("path") String path, @PathVariable("filename") String filename) throws IOException {
        this.webDavService.deleteFile(path);
        return new ResponseEntity(this.documentService.deleteFile(""), HttpStatus.OK);
    }

}
