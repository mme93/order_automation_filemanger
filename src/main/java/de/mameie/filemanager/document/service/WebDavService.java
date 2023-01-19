package de.mameie.filemanager.document.service;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebDavService {

    @Value("${webDav.user}")
    private String user;

    @Value("${webDav.password}")
    private String password;

    @Value("${webDav.url}")
    private String url;

    private static String UPLOADED_FOLDER = "src/main/resources/upload/";

    public List<String> getFiles(String path) throws IOException {
        List<String> fileNames = new ArrayList<>();
        Sardine sardine = SardineFactory.begin(this.user, "!" + this.password);
        List<DavResource> resources = sardine.list(this.url + path);
        for (DavResource res : resources) {
            fileNames.add(res.getName());
        }
        return fileNames;
    }

    public String getFile(String path, String fileName) throws IOException {
        Sardine sardine = SardineFactory.begin(this.user, "!" + this.password);
        InputStream downloadStream = sardine.get(this.url + path + fileName);
        return "Test";
    }
    public boolean createFolder(String path){
        try{
            Sardine sardine = SardineFactory.begin(this.user, "!" + this.password);
            sardine.createDirectory(this.url+path);
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean saveFile(MultipartFile file, String rootFolder) {

        try {
            Sardine sardine = SardineFactory.begin();
            sardine.setCredentials(this.user, "!" + this.password);
            sardine.enablePreemptiveAuthentication("http://212.227.165.166/webdav/");
            if(!sardine.exists(url+rootFolder)){
                System.err.println("Not Exist");
                return false;
            }
            InputStream fis = file.getInputStream();
            String fileName= file.getOriginalFilename();
            //Replace all " "
            fileName = fileName.replaceAll("\\s", "");
            fileName = fileName.replaceAll("ä", "ae");
            fileName = fileName.replaceAll("ö", "oe");
            fileName = fileName.replaceAll("ü", "ue");
            fileName = fileName.replaceAll("Ä", "AE");
            fileName = fileName.replaceAll("Ö", "OE");
            fileName = fileName.replaceAll("Ü", "UE");
            fileName = fileName.replaceAll("ß", "ss");
            sardine.put(url+rootFolder+fileName, fis);
            fis.close();
        }catch (IOException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }
    /*
    public boolean saveFile(String fileName, String rootFolder) {
        if (!new File(this.UPLOADED_FOLDER + fileName).exists()) {
            System.err.println("No, file found: " + this.UPLOADED_FOLDER + fileName);
            return false;
        }
        try {
            Sardine sardine = SardineFactory.begin();
            sardine.setCredentials(this.user, "!" + this.password);
            sardine.enablePreemptiveAuthentication("http://212.227.165.166/webdav/");
            if(!sardine.exists(url+rootFolder)){
                System.err.println("Not Exist");
                return false;
            }
            File file = new File(this.UPLOADED_FOLDER + fileName);
            InputStream fis = new FileInputStream(file);
            //Replace all " "
            fileName = fileName.replaceAll("\\s", "");
            fileName = fileName.replaceAll("ä", "ae");
            fileName = fileName.replaceAll("ö", "oe");
            fileName = fileName.replaceAll("ü", "ue");
            fileName = fileName.replaceAll("Ä", "AE");
            fileName = fileName.replaceAll("Ö", "OE");
            fileName = fileName.replaceAll("Ü", "UE");
            fileName = fileName.replaceAll("ß", "ss");
            sardine.put(url+rootFolder+fileName, fis);
            fis.close();
        }catch (IOException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }
     */

    public boolean deleteFile(String path) {
        String paths=this.url+"kfz/";
        try {
            Sardine sardine = SardineFactory.begin(this.user, "!" + this.password);
            List<String>files= new ArrayList<>();
            List<DavResource> resources = sardine.list(paths);

            for (DavResource res : resources) {
                files.add(res.getName());
                if(res.getName().contains(".")){
                    sardine.delete(paths+res.getName());
                }
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
