/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.saude.filelink.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.saude.infrastructure.exception.BusinessException;
import com.br.saude.infrastructure.support.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

import com.br.saude.ServerController;
import com.br.saude.filelink.dao.IDaoFileLink;
import com.br.saude.filelink.model.FileLink;

/**
 *
 * @author vitor
 */
@Service
public class ServiceFileLink implements IServiceFileLink {

    // OBJETO
    @Autowired
    private IDaoFileLink daoFileLink;

    @Autowired
    private HttpServletRequest request;

    // CONSTANTES

    // mensagem de erro se o FileLink for null;
    public final static String USUARIO_NULL = "Usuário null";

    // mensagem de erro se o FileLink já existir;
    public final static String USUARIO_EXISTE = "O Usuário já existe,";

    // mensagem de erro se o FileLink não existir no banco;
    public final static String USUARIO_NAO_EXISTE = "O Usuário não existe na base de dados";

    // mensagem de erro se o FileLink for inválido;
    public final static String USUARIO_INVALIDO = "Usuário inválido";

    @Override
    public FileLink save(FileLink filelink) {
        if (filelink == null) {
            throw new BusinessException(USUARIO_NULL);
        }

        return daoFileLink.save(filelink);
    }

    @Override
    public void delete(FileLink filelink) {
        if (filelink == null) {
            throw new BusinessException(USUARIO_NULL);
        } else {
            this.daoFileLink.delete(filelink);
            return;
        }
    }

    @Override
    public List<FileLink> getAll() {
        List<FileLink> filk = (List<FileLink>) this.daoFileLink.findAll();

        for (FileLink fl : filk) {
            if (fl == null || fl.getFullPath() == null)
                continue;
            fl.setFullPath(new ServerController().replaceServerName(fl.getFullPath(), request));
        }

        return filk;
    }

    @Override
    public FileLink findById(String id) {
        Optional<FileLink> filelink = daoFileLink.findById(id);
        FileLink fl = filelink.isPresent() ? filelink.get() : null;

        fl.setFullPath(new ServerController().replaceServerName(fl.getFullPath(), request));

        return fl;
    }

    @Override
    public List<FileLink> findAllByParentId(String parentId) {
        List<FileLink> filk = (List<FileLink>) daoFileLink.findAllByParentId(parentId);

        for (FileLink fl : filk) {
            fl.setFullPath(new ServerController().replaceServerName(fl.getFullPath(), request));

        }
        return filk;
    }

}
