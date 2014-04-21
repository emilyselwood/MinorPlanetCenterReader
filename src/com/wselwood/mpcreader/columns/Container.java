package com.wselwood.mpcreader.columns;

/**
 * A container class to hold values as we work.
 *
 * Created by wselwood on 14/04/14.
 */
public class Container<T> {

    T value = null;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void reset() {
        value = null;
    }

    @Override
    public String toString() {
        return "Container{" +
                "value=" + value +
                '}';
    }
}
