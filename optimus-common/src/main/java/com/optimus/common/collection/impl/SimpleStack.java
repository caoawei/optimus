package com.optimus.common.collection.impl;

import com.optimus.common.collection.Stack;

/**
 * 此实现非线程安全的stack,功能简单
 * Created by Administrator on 2018/5/18.
 */
public class SimpleStack<T> implements Stack<T> {
    private static final int DEFAULT_CAP = 16;
    private Object[] element;
    private int size = 0; // 既是容量大小,也是栈顶索引.
    private int cap;

    public SimpleStack(int cap) {
        if(cap < 0) {
            cap = DEFAULT_CAP;
        }
        this.cap = cap;
        element = new Object[this.cap];
    }

    @Override
    public void push(T data) {
        ensureCap();
        this.element[size++] = data;
    }

    private void ensureCap() {
        if(this.size == element.length) {
            Object[] ndata = new Object[this.size << 1]; // 每次扩容为当前容量的两倍
            System.arraycopy(element,0,ndata,0,element.length);
            element = ndata;
        }
    }

    @Override
    public T pop() {
        if(this.size == 0) {
            return null;
        }
        T r = (T) this.element[--this.size];
        this.element[this.size] = null;
        return r;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
