package com.br.saude.filelink.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.saude.filelink.model.FileLink;

@Repository
public interface IDaoFileLink extends JpaRepository<FileLink, String> {

        public abstract Optional<FileLink> findById(String id);

        public abstract List<FileLink> findAllByParentId(String parentId);

}
