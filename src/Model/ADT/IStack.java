package Model.ADT;

import Exceptions.ADTexcept;

import java.util.List;

public interface IStack<T> {
    T pop() throws ADTexcept;
    void push(T value);
    boolean isEmpty();
    List<T> getValues();
    List<T> getAll();

    public List<T> toList();
}
