package de.mameie.filemanager.document.controller;

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


    private final WebDavService webDavService;

    @Autowired
    public DocumentController(WebDavService webDavService) {
        this.webDavService = webDavService;
    }


    @PostMapping(value = "/upload/single")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        this.webDavService.saveFile(file, "kfz/");
        return new ResponseEntity<>("File(s) uploaded successfully!", HttpStatus.OK);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploads(@RequestParam("file") List<MultipartFile> files) {
        for (MultipartFile file : files) {
            this.webDavService.saveFile(file, "kfz/");
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
        return "Test";
    }

    @GetMapping("/files")
    public ResponseEntity<List<String>> getFiles() throws IOException {
        return new ResponseEntity("Test", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{path}/{filename}")
    public ResponseEntity<Boolean> deleteFile(@PathVariable("path") String path, @PathVariable("filename") String filename) throws IOException {
        this.webDavService.deleteFile(path);
        return new ResponseEntity("Test", HttpStatus.OK);
    }

}
