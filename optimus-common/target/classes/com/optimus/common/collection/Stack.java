package com.optimus.common.collection;

/**
 * Created by Administrator on 2018/5/18.
 */
public interface Stack<T> {

    void push(T data);

    T pop();

    int size();

    boolean isEmpty();
}
