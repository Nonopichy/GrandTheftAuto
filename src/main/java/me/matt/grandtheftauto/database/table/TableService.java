package me.matt.grandtheftauto.database.table;

import java.util.List;

public interface TableService<T, K> {

    void createTable();

    boolean has(T t);

    boolean hasBy(K k);

    boolean hasById(short id);

    void add(T t);

    void insert(T t);

    void update(T t);

    void delete(K k);

    T get(K k);

    T getById(short id);

    int getSize();

    List<T> getAll();

}
