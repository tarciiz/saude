package com.br.saude.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.saude.user.model.User;

@Repository
public interface IDaoUser extends JpaRepository<User, String> {

    public abstract Optional<User> findById(String id);

    public abstract Optional<User> findByLoginAndPassword(String login,
            String password);

    public abstract Optional<User> findByEmailAndPassword(String email,
            String password);
}
