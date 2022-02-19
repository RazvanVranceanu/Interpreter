package Model.Expression;

import Exceptions.ExpExcept;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExp implements Exp{
    private final Value value;

    public ValueExp(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(IDict<String, Value> symbolTable, HeapInterface heapInterface) throws ExpExcept {
        return value;
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws Exception {
        return value.getType();
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
