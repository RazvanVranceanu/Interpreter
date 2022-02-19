package Model.Expression;

import Exceptions.ExpExcept;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.Value;

public class MULExp implements Exp{
    private Exp ex1;
    private Exp ex2;

    public MULExp(Exp ex1, Exp ex2) {
        this.ex1 = ex1;
        this.ex2 = ex2;
    }

    @Override
    public Value evaluate(IDict<String, Value> table, HeapInterface heap) throws ExpExcept {
        return new ArithExp('-', new ArithExp('*', ex1, ex2), new ArithExp('+',ex1,ex2)).evaluate(table,heap);
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type t1,t2;
        t1 = ex1.typeCheck(typeEnv);
        t2 = ex2.typeCheck(typeEnv);

        if(t1.equals(new IntType()) && t2.equals(new IntType())) return new IntType();
        else throw new ExpExcept("MUL Expressions should be of type int");
    }

    @Override
    public String toString() {
        return "MULExpression{" + ex1.toString() +", "+ ex2.toString() + '}';
    }

}
