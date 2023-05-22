package com.br.saude;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.br.saude.filelink.model.FileLink;
import com.br.saude.filelink.service.IServiceFileLink;
import com.br.saude.infrastructure.model.PersistenceEntity;
import com.br.saude.infrastructure.repository.BaseRepository;
import com.br.saude.infrastructure.support.StringUtil;
import com.br.saude.user.model.User;

import java.io.File;
import java.util.ArrayList;

/*=========================ATENÇÃO=========================
 * As classes a seguir estão organizadas em ordem alfabética assim como seus respectivos métodos. Caso for fazer alguma alteração, continue com a organização!
 *  
*/
@RestController
@RequestMapping(path = "/api/v1/app")
public class Controller {

    @Autowired
    private IServiceFileLink serviceFileLink;

    @Autowired
    private ServerController serverController;

    private RestTemplate restTemplate = new RestTemplate();

    private static final String UPLOAD_FOLDER = "files/";

    @PostMapping("/public/upload")
    public FileLink handleFileUpload(@RequestParam("file") MultipartFile file, String parentId) {

        if (file.isEmpty()) {
            return null;
        }

        try {

            byte[] bytes = file.getBytes();

            String originalFilename = file.getOriginalFilename();
            System.out.println("File name" + originalFilename);
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS");
            String timestamp = formatter.format(LocalDateTime.now());

            String newFilename = timestamp + extension;

            String savedFile = ServerController.SERVER + "/" + UPLOAD_FOLDER + newFilename;

            Path path = Paths.get(UPLOAD_FOLDER + newFilename);

            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            Files.write(path, bytes);

            FileLink fillk = new FileLink();
            fillk.setFullPath(savedFile);
            fillk.setName(newFilename);
            fillk.setParentId(parentId);
            return serviceFileLink.save(fillk);
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}