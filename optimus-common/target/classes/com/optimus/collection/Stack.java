package com.optimus.collection;

public interface Stack<T> {

    boolean push(T t);

    T offer();

    T pop();

    int size();

    boolean isEmpty();

    void clear();


}
