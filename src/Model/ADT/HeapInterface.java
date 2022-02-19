package Model.ADT;

import Model.Value.Value;

import java.util.HashMap;
import java.util.Map;

public interface HeapInterface {
    Value getValue(int addr);
    void setHeap(int addr, Value val);
    int add(Value val);
    boolean exists(int addr);

    Map<Integer,Value> getHeap();
    void setHeap(HashMap<Integer,Value> newHeap);

    String toString();

    Iterable<Map.Entry<Integer, Value>> getAll();
}
