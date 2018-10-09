package com.optimus.collection.impl;

import com.optimus.collection.Stack;

public class ArrayStack<E> implements Stack<E> {

    private int size;

    private Object[] elements;

    public ArrayStack(int cap) {
        elements = new Object[cap];
        this.size = 0;
    }

    @Override
    public boolean push(E e) {

        ensureCap();

        if(this.size == elements.length) {
            return false;
        }

        elements[size++] = e;
        return false;
    }

    @Override
    public E pop() {
        if(isEmpty()) {
            return null;
        }

        Object val = elements[--size];
        elements[size] = null;
        return (E) val;
    }

    @Override
    public E offer() {

        return isEmpty() ? null : (E) elements[size - 1];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        if(!isEmpty()) {
            for (int i = 0; i < size;i++) {
                elements[i] = null;
            }
        }
    }

    private void ensureCap() {
        if(this.size == elements.length) {
            Object[] newEls = new Object[elements.length << 1];
            System.arraycopy(elements,0,newEls,0,this.size);
            clear();
            this.elements = newEls;
        }
    }
}
