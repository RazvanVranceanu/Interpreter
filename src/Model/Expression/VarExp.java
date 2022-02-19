package Model.Expression;

import Exceptions.ExpExcept;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Type.Type;
import Model.Value.Value;

public class VarExp implements Exp{
    private final String variable;

    public VarExp(String variable) {
        this.variable = variable;
    }

    @Override
    public Value evaluate(IDict<String, Value> symbolTable, HeapInterface heapInterface) throws ExpExcept {
        return symbolTable.lookUp(variable);
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws Exception {
        return typeEnv.lookUp(variable);
    }

    @Override
    public String toString(){
        return variable;
    }
}
