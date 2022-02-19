package Model.Expression;

import Exceptions.ExpExcept;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;


public class InvertExp implements Exp{
    private Exp exp;

    public InvertExp(Exp e) {exp=e;}


    @Override
    public Value evaluate(IDict<String, Value> symTable, HeapInterface heap) throws ExpExcept {
        BoolValue b = (BoolValue) exp.evaluate(symTable,heap);
        return !b.getValue() ? new BoolValue(true) : new BoolValue(false);
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws Exception {
        return exp.typeCheck(typeEnv);
    }

    @Override
    public String toString() {
        return "!(" + exp.toString() + ')';
    }
}
