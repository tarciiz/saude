package com.br.saude.filelink.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.saude.filelink.model.FileLink;
import com.br.saude.filelink.service.IServiceFileLink;
import com.br.saude.infrastructure.support.ObjectUtils;
import com.br.saude.infrastructure.support.StringUtil;

/*=========================ATENÇÃO=========================
 * As classes a seguir estão organizadas em ordem alfabética assim como seus respectivos métodos. Caso for fazer alguma alteração, continue com a organização!
 *  
*/
@RestController
@RequestMapping(path = "/api/v1/app/filelink")
public class FileLinkController {
    // ---------------------------------------------------
    // ------------- FILE LINK -----------------------------
    // ---------------------------------------------------
    @Autowired
    private IServiceFileLink serviceFileLink;

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public boolean deletar(@PathVariable String id) {
        FileLink file = new FileLink();
        file.setId(id);
        serviceFileLink.delete(file);
        return true;
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public FileLink salvar(@RequestBody FileLink file) {
        // FileLink FileLinkr = (FileLink) gson.fromJson(FileLink, FileLink.class);
        return serviceFileLink.save(file);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public FileLink update(@RequestBody Map<String, Object> updates) {

        return ObjectUtils.updateOrSaveObject(updates, serviceFileLink);
    }

    @RequestMapping(path = "/all")
    public List<FileLink> get() {
        return serviceFileLink.getAll();
    }

    @RequestMapping(path = "/parent/{id}")
    public List<FileLink> get(@PathVariable String id) {
        return serviceFileLink.findAllByParentId(id);
    }

    @RequestMapping(path = "/find/{id}")
    public FileLink getFl(@PathVariable String id) {

        System.out.println("Id " + id);
        return (FileLink) this.serviceFileLink.findById(id);
    }
}