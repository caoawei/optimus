package com.optimus.collection;

public interface SkipList<T> {


    void insert(T data);

    void remove(T data);

    boolean exists(T data);

}
