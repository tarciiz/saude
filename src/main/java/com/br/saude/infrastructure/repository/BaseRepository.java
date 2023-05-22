package com.br.saude.infrastructure.repository;

import org.springframework.data.mapping.PersistentEntity;
import org.springframework.stereotype.Repository;

import com.br.saude.infrastructure.model.PersistenceEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BaseRepository<T extends PersistenceEntity> {
    public abstract T save(T t);

    public abstract void delete(T t);

    public abstract List<T> getAll();

    public T findById(String t);
}
