package Model.ADT;

import java.util.HashMap;
import java.util.Map;

public class Dict<Key, Value> implements IDict<Key, Value>{

    private final HashMap<Key, Value> elems;

    public Dict() {
        this.elems = new HashMap<>();
    }

    public Dict(HashMap<Key, Value> elems){
        this.elems = new HashMap<>();
    }

    @Override
    public void add(Key key, Value value) {
        elems.put(key, value);
    }

    @Override
    public void update(Key key, Value newValue) {
        elems.replace(key, newValue);
    }

    @Override
    public Value lookUp(Key key) {
        return elems.get(key);
    }

    @Override
    public boolean isDefined(Key key) {
        return elems.containsKey(key);
    }

    @Override
    public void set(Key key, Value val) {
        elems.put(key, val);
    }

    @Override
    public int size() {
        return elems.size();
    }

    @Override
    public HashMap<Key, Value> getContent() {
        return elems;
    }

    @Override
    public void setContent(HashMap<Key, Value> newHm) {
        elems.clear();
        elems.putAll(newHm);
    }

    @Override
    public IDict<Key, Value> createCopy() {
        Dict<Key,Value> newDict=new Dict<>();
        for (var key: elems.keySet())
        {
            newDict.elems.put(key,elems.get(key));
        }
        return newDict;
    }

    @Override
    public String toString(){
        StringBuilder result= new StringBuilder(" [");
        int position=0;
        for (Key key:elems.keySet()){
            if (position==0) result.append("").append(key).append(" → ").append(elems.get(key));
            else result.append(" , ").append(key).append(" → ").append(elems.get(key));
            position++;
        }
        result.append(" ]");
        return result.toString();
    }

    @Override
    public Iterable<Map.Entry<Key, Value>> getAll() {
        return elems.entrySet();
    }
}
