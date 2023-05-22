package com.br.saude.infrastructure.support;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String prefix = "ID-";
        String suffix = UUID.randomUUID().toString().toUpperCase();
        return (prefix + suffix).substring(16);
    }
}