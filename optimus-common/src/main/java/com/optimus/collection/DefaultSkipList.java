package com.optimus.collection;

import java.util.concurrent.ThreadLocalRandom;

public class DefaultSkipList implements SkipList<Integer> {

    private Node head;

    private int capital;

    public DefaultSkipList(int cap) {
        this.capital = cap;
    }

    @Override
    public void insert(Integer data) {

    }

    @Override
    public void remove(Integer data) {

    }

    @Override
    public boolean exists(Integer data) {
        return false;
    }

    private int hash() {
        int level = 0;

        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 1; i < capital;i++) {
            if((random.nextInt() & 1) == 0) {
                level++;
            }
        }

        return level;
    }

    private class Node {

        Integer data;

        Node next;

        Node pre;

        Node down;

        Node(Integer data,Node next,Node pre,Node down) {
            this.data = data;
            this.next = next;
            this.pre = pre;
            this.down = down;
        }
    }
}
