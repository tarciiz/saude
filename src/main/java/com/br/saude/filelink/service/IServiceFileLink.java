/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.br.saude.filelink.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.saude.filelink.model.FileLink;
import com.br.saude.infrastructure.repository.BaseRepository;

/**
 *
 * @author vitor
 */
@Service
public interface IServiceFileLink extends BaseRepository<FileLink> {
    public List<FileLink> findAllByParentId(String parentId);

}
