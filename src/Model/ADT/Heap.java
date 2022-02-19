package Model.ADT;

import Model.Value.Value;

import java.util.HashMap;
import java.util.Map;

public class Heap implements HeapInterface{
    private Map<Integer,Value> heap;
    private Integer freeAddr;

    public Heap(){

        heap = new HashMap<>();
        freeAddr = 1;
    }

    @Override
    public Value getValue(int addr) {
        return heap.get(addr);
    }

    @Override
    public void setHeap(int addr, Value val) {
        heap.put(addr,val);
    }

    @Override
    public int add(Value val) {
        int addr=freeAddr;
        setHeap(freeAddr,val);
        freeAddr++;
        return addr;
    }

    @Override
    public boolean exists(int addr) {
        return heap.containsKey(addr);
    }

    @Override
    public Map<Integer, Value> getHeap() {
        return heap;
    }


    @Override
    public void setHeap(HashMap<Integer, Value> newHeap) {
        heap=newHeap;
    }

    @Override
    public String toString(){
        StringBuilder result= new StringBuilder("[ ");
        int position=0;
        for(Integer address : heap.keySet())
        {
            if (position==0) result.append("").append(address).append(" -> ").append(heap.get(address));
            else result.append(" , ").append(address).append(" -> ").append(heap.get(address));
            position++;
        }
        result.append(" ]");
        return result.toString();
    }


    @Override
    public Iterable<Map.Entry<Integer, Value>> getAll() {
        return heap.entrySet();
    }
}
