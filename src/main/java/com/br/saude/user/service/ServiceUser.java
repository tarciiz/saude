/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.saude.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.saude.ServerController;
import com.br.saude.infrastructure.exception.BusinessException;
import com.br.saude.infrastructure.support.StringUtil;
import com.br.saude.user.dao.IDaoUser;
import com.br.saude.user.model.User;

import jakarta.persistence.EntityListeners;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author vitor
 */
@Service
public class ServiceUser implements IServiceUser {

    // OBJETO
    @Autowired
    private IDaoUser daoUser;
    @Autowired
    private HttpServletRequest request;

    // CONSTANTES

    // mensagem de erro se o User for null;
    public final static String USUARIO_NULL = "Usuário null";

    // mensagem de erro se o User já existir;
    public final static String USUARIO_EXISTE = "O Usuário já existe,";

    // mensagem de erro se o User não existir no banco;
    public final static String USUARIO_NAO_EXISTE = "O Usuário não existe na base de dados";

    // mensagem de erro se o User for inválido;
    public final static String USUARIO_INVALIDO = "Usuário inválido";

    @Override
    public User save(User user) {
        if (user == null) {
            throw new BusinessException(USUARIO_NULL);
        }

        // O user já existe e está atualizando o usuário
        String id = user.getId();
        if (id != null) {
            System.out.println("O usuario está sendo atualizado " + user.getId());
            // Busca o usuário salvo no banco para atualizar a senha somente se mudar

            // Verifica se a senha foi atualizada

            System.out.println("Senha " + user.getPassword());

            if (!user.getPassword().startsWith("password_")) {
                user.setPassword(StringUtil.toPasswordMD5(user.getPassword()));
                System.out.println("Senha " + user.getPassword());
            }
        } else {
            // O usuario está sendo inserido
            System.out.println("O usuario está sendo inserido");
            user.setPassword(StringUtil.toPasswordMD5(user.getPassword()));
        }

        User usr = daoUser.save(user);

        if (usr.getProfilePicture() != null) {

            usr.getProfilePicture()
                    .setFullPath(
                            new ServerController().replaceServerName(usr.getProfilePicture().getFullPath(),
                                    request));
        }

        return usr;
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            throw new BusinessException(USUARIO_NULL);
        } else {
            this.daoUser.delete(user);
            return;
        }
    }

    @Override
    public List<User> getAll() {
        List<User> usrs = (List<User>) this.daoUser.findAll();

        if (usrs == null)
            return null;

        for (User usr : usrs) {
            if (usr.getProfilePicture() != null) {

                usr.getProfilePicture()
                        .setFullPath(
                                new ServerController().replaceServerName(usr.getProfilePicture().getFullPath(),
                                        request));
            }
        }
        return usrs;
    }

    @Override
    public User findById(String id) {
        Optional<User> user = daoUser.findById(id);
        User usr = user.isPresent() ? user.get() : null;
        if (usr == null)
            return null;

        if (usr.getProfilePicture() != null) {
            usr.getProfilePicture()
                    .setFullPath(new ServerController().replaceServerName(
                            usr.getProfilePicture().getFullPath() != null ? usr.getProfilePicture().getFullPath() : "",
                            request));
        }

        return usr;

    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        Optional<User> user = daoUser.findByLoginAndPassword(login, password);
        User usr = user.isPresent() ? user.get() : null;
        if (usr == null)
            return null;

        if (usr.getProfilePicture() != null) {
            usr.getProfilePicture()
                    .setFullPath(
                            new ServerController().replaceServerName(usr.getProfilePicture().getFullPath(), request));
        }

        return usr;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        Optional<User> user = daoUser.findByEmailAndPassword(email, password);
        User usr = user.isPresent() ? user.get() : null;
        if (usr == null)
            return null;

        if (usr.getProfilePicture() != null) {
            usr.getProfilePicture()
                    .setFullPath(
                            new ServerController().replaceServerName(usr.getProfilePicture().getFullPath(), request));
        }
        return usr;
    }

}
