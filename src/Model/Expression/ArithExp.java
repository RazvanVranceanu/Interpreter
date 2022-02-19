package Model.Expression;

import Exceptions.ExpExcept;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class ArithExp implements Exp{
    private final Exp exp1;
    private final Exp exp2;
    private final char op;


    public ArithExp(char op, Exp e1, Exp e2) {
        this.exp1 = e1;
        this.exp2 = e2;
        this.op = op;
    }

    @Override
    public Value evaluate(IDict<String, Value> symbolTable, HeapInterface heap) throws ExpExcept {
        Value val1=null, val2=null;

        try{
            val1=exp1.evaluate(symbolTable, heap);
        } catch (ExpExcept expExcept) {
            expExcept.printStackTrace();
        }

        if(!val1.getType().equals(new IntType()))
            throw new ExpExcept("1st operand is not of type int\n");

        try{
            val2=exp2.evaluate(symbolTable, heap);
        } catch (ExpExcept expExcept) {
            expExcept.printStackTrace();
        }
        if(!val2.getType().equals(new IntType()))
            throw new ExpExcept("2nd operand is not of type int\n");

        IntValue result = new IntValue(0);

        switch (op){
            case '+': result=new IntValue(((IntValue)val1).getValue()+((IntValue)val2).getValue());
            break;
            case '-': result=new IntValue(((IntValue)val1).getValue()-((IntValue)val2).getValue());
            break;
            case '*': result=new IntValue(((IntValue)val1).getValue()*((IntValue)val2).getValue());
            break;
            case '/': {
                if (((IntValue)val2).getValue()==0)
                    throw new ExpExcept("DIVISION BY 0\n");
                result=new IntValue(((IntValue)val1).getValue()/((IntValue)val2).getValue());
            }
            break;
            default: throw new ExpExcept("Invalid operator!\n");
        }
        return result;
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type t1, t2;
        t1 = exp1.typeCheck(typeEnv);
        t2 = exp2.typeCheck(typeEnv);

        if (!t1.equals(new IntType()))
            throw new ExpExcept("First operand is not of type int");
        if (!t2.equals(new IntType()))
            throw new ExpExcept("Second operand is not of type int");
        return new IntType();

    }

    @Override
    public String toString()
    {
        return " (" + exp1 + op + exp2 + ") ";
    }
}
