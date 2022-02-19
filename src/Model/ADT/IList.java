package Model.ADT;

import java.util.Iterator;

public interface IList<V> {
        void append(V newValue);
        int size();
        V get(int i);
        Iterator<V> iterator();
}
