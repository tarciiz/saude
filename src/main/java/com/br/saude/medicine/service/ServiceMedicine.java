/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.saude.medicine.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.saude.infrastructure.exception.BusinessException;
import com.br.saude.infrastructure.support.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

import com.br.saude.ServerController;
import com.br.saude.medicine.dao.IDaoMedicine;
import com.br.saude.medicine.model.Medicine;

/**
 *
 * @author vitor
 */
@Service
public class ServiceMedicine implements IServiceMedicine {

    // OBJETO
    @Autowired
    private IDaoMedicine daoMedicine;

    @Autowired
    private HttpServletRequest request;

    // CONSTANTES

    // mensagem de erro se o Medicine for null;
    public final static String USUARIO_NULL = "Usuário null";

    // mensagem de erro se o Medicine já existir;
    public final static String USUARIO_EXISTE = "O Usuário já existe,";

    // mensagem de erro se o Medicine não existir no banco;
    public final static String USUARIO_NAO_EXISTE = "O Usuário não existe na base de dados";

    // mensagem de erro se o Medicine for inválido;
    public final static String USUARIO_INVALIDO = "Usuário inválido";

    @Override
    public Medicine save(Medicine medicine) {
        if (medicine == null) {
            throw new BusinessException(USUARIO_NULL);
        }

        return daoMedicine.save(medicine);
    }

    @Override
    public void delete(Medicine medicine) {
        if (medicine == null) {
            throw new BusinessException(USUARIO_NULL);
        } else {
            this.daoMedicine.delete(medicine);
            return;
        }
    }

    @Override
    public List<Medicine> getAll() {
        return (List<Medicine>) this.daoMedicine.findAll();
    }

    @Override
    public Medicine findById(String id) {
        Optional<Medicine> medicine = daoMedicine.findById(id);
        Medicine fl = medicine.isPresent() ? medicine.get() : null;

        return fl;
    }

}
