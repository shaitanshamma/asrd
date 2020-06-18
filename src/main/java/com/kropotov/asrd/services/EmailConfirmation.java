package com.kropotov.asrd.services;

public interface EmailConfirmation {

    void addValuePassAndEmail(Integer password, String emailVerified);

    void removeAll();

    boolean findByPassAndEmail(Integer password, String emailVerified);

    boolean isEmpty();
}
