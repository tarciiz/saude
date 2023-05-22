/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.br.saude.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.saude.infrastructure.repository.BaseRepository;
import com.br.saude.user.model.User;

/**
 *
 * @author vitor
 */
@Service
public interface IServiceUser extends BaseRepository<User> {

    public User findByLoginAndPassword(String login, String password);

    public User findByEmailAndPassword(String email, String password);

}
