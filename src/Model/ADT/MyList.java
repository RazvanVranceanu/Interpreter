package Model.ADT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyList<V> implements IList<V> {
    private final List<V> elems;

    public MyList(ArrayList<V> elems) {
        this.elems = new ArrayList<>();
    }

    public MyList(){
        this.elems = new ArrayList<>();
    }

    @Override
    public void append(V newValue) {
        elems.add(newValue);
    }

    @Override
    public int size() {
        return elems.size();
    }

    @Override
    public V get(int i) {
        return elems.get(i);
    }

    @Override
    public Iterator<V> iterator() {
        return elems.iterator();
    }

    @Override
    public String toString(){
        StringBuilder result= new StringBuilder("[ ");
        int position=0;
        for(V value:elems)
        {
            if (position==0) result.append("").append(value);
            else result.append(" , ").append(value);
            position++;
        }
        result.append(" ]");
        return result.toString();
    }
}
