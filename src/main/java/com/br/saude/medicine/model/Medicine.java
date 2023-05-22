/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.saude.medicine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.br.saude.infrastructure.model.PersistenceEntity;
import com.br.saude.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author vitor
 */

@Entity
@Table(name = "medicine")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(value = { "fileLinks" })
public class Medicine extends PersistenceEntity {

    String description;
    Double price;

}
