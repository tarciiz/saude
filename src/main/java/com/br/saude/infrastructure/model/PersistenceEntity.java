/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.saude.infrastructure.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import com.br.saude.filelink.model.FileLink;
import com.br.saude.infrastructure.support.RequestHeaderHolder;
import com.br.saude.user.model.User;
import com.br.saude.user.service.IServiceUser;
import com.br.saude.user.service.ServiceUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;

/**
 *
 * @author rocki.juliusW
 */
@Data
@MappedSuperclass
public class PersistenceEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parentId")
    @JsonIgnoreProperties(value = { "fileLinks", "name", "fullPath", "parentId", "updatedBy", "createdBy", "createdAt",
            "updatedAt" })
    private List<FileLink> fileLinks = new ArrayList<>();

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public String createdById;
    public String updatedById;

    @PreUpdate
    public void preUpdate() {
        String userId = RequestHeaderHolder.getFromHeaders("UserId");

        this.updatedById = userId;
        this.updatedAt = LocalDateTime.now();

    }

    @PrePersist
    public void prePersist() {
        String userId = RequestHeaderHolder.getFromHeaders("UserId");
        this.createdById = userId;
        this.updatedById = userId;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
