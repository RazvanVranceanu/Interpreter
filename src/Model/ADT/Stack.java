package Model.ADT;

import Exceptions.ADTexcept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stack<T> implements IStack<T> {
    private final ArrayList<T> elems;

    public Stack(ArrayList<T> elems) {
        this.elems = new ArrayList<>();
    }

    public Stack() {elems = new ArrayList<>();}

    @Override
    public T pop() throws ADTexcept {
        if(elems.isEmpty())
            throw new ADTexcept(" EXE STACK IS EMPTY!\n");
        T topOfTheStack = elems.get(elems.size() - 1);
        elems.remove(elems.size() - 1);
        return topOfTheStack;
    }

    @Override
    public void push(T value) {
        elems.add(value);
    }

    @Override
    public boolean isEmpty() {
        return elems.isEmpty();
    }

    @Override
    public List<T> getValues() {
        return elems.subList(0, elems.size());
    }

    @Override
    public String toString(){
        StringBuilder result= new StringBuilder("[ ");
        for (int position=elems.size()-1;position>=0;position--)
        {
            if (position==elems.size()-1) result.append("").append(elems.get(position));
            else result.append("|").append(elems.get(position));
        }
        result.append(" ]");
        return result.toString();
    }

    @Override
    public List<T> getAll() {
        return elems;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>(elems);
        Collections.reverse(list);
        return list;
    }
}
