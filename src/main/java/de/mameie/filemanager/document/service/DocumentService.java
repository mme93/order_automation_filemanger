package de.mameie.filemanager.document.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DocumentService {



    private static String UPLOADED_FOLDER = "src/main/resources/upload/";

    public List<String> getFiles(String path) throws IOException {

        return null;
    }

    public String getFile(String path, String fileName) throws IOException {

        return "Test";
    }

    public String saveFile(MultipartFile file) {
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successfully uploaded - " + file.getOriginalFilename();
    }

    public boolean deleteFile(String path) {
       return true;
    }

}
