package Model.Expression;

import Exceptions.ExpExcept;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class RelationalExp implements Exp{
    private Exp expr1;
    private Exp expr2;
    private String op;

    public RelationalExp(Exp e1,Exp e2,String op)
    {
        this.expr1=e1;
        this.expr2=e2;
        this.op=op;
    }

    @Override
    public Value evaluate(IDict<String, Value> symbolTable, HeapInterface heap) throws ExpExcept {
        Value value1=null;
        Value value2=null;
        try{ value1=expr1.evaluate(symbolTable, heap);}
        catch (ExpExcept except){except.printStackTrace();}
        if (!value1.getType().equals(new IntType()))
            throw new ExpExcept("The first value given is not an int\n");
        try{ value2=expr2.evaluate(symbolTable, heap);}
        catch (ExpExcept except) { except.printStackTrace();}
        if(!value2.getType().equals(new IntType()))
            throw new ExpExcept("The second value given is not an int\n");

        int nr1=((IntValue)value1).getValue();
        int nr2=((IntValue)value2).getValue();

        return switch(op){
            case "<"-> new BoolValue(nr1<nr2);
            case "<="->new BoolValue(nr1<=nr2);
            case "==" -> new BoolValue(nr1==nr2);
            case "!=" -> new BoolValue(nr1!=nr2);
            case ">"->new BoolValue (nr1>nr2);
            case ">="-> new BoolValue(nr1>=nr2);
            default ->throw new ExpExcept("Invalid operand"+op);
        };

    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type t1, t2;
        t1 = expr1.typeCheck(typeEnv);
        t2 = expr2.typeCheck(typeEnv);

        if (!t1.equals(new IntType()))
            throw new ExpExcept("First operand is not of type int");
        if (!t2.equals(new IntType()))
            throw new ExpExcept("Second operand is not of type int");
        return new BoolType();

    }

    @Override
    public String toString(){
        return " (" + this.expr1.toString() + this.op+this.expr2.toString() + ") ";
    }

}
