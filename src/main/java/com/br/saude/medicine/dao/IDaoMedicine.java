package com.br.saude.medicine.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.saude.medicine.model.Medicine;

@Repository
public interface IDaoMedicine extends JpaRepository<Medicine, String> {

        public abstract Optional<Medicine> findById(String id);

}
