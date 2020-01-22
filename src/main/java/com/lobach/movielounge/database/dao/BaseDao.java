package com.lobach.movielounge.database.dao;

public interface BaseDao<T, K> {
    public boolean add(T object);
    public boolean update(T object);
    public boolean remove(T object);

    public T get(K key);
}
