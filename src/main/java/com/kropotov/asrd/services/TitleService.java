package com.kropotov.asrd.services;

public interface TitleService<T, ID> extends CrudService<T, ID> {
    T getByTitle(String title);
}
