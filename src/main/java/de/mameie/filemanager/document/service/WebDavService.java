package de.mameie.filemanager.document.service;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public boolean saveFile(String path, String fileName) {
        if (!new File(path + fileName).exists()) {
            System.err.println("No, file found: " + path + fileName);
            return false;
        }
        try {
            Sardine sardine = SardineFactory.begin();
            sardine.setCredentials(this.user, "!" + this.password);
            sardine.enablePreemptiveAuthentication("http://212.227.165.166/webdav/");
            InputStream fileStream = new FileInputStream(path + fileName);
            sardine.put(this.url + "/document/" + fileName, fileStream);
            return true;
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }

    }

    public boolean deleteFile(String path) {
        try {
            Sardine sardine = SardineFactory.begin(this.user, "!" + this.password);
            sardine.delete(this.url + path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
