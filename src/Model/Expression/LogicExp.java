package Model.Expression;

import Exceptions.ExpExcept;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

import java.util.Objects;


public class LogicExp implements Exp{
    private final Exp exp1;
    private final Exp exp2;
    private final int operator;  //1->"and" ; 2->"or"

    public LogicExp(Exp exp1, Exp exp2, int operator) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    public LogicExp(Exp exp1, Exp exp2, String operator) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        if(Objects.equals(operator, "and"))
            this.operator = 1;
        else if(Objects.equals(operator, "or"))
            this.operator = 2;
        else this.operator = 1;
    }

    @Override
    public Value evaluate(IDict<String, Value> symbolTable, HeapInterface heap) throws ExpExcept {
        Value value1=exp1.evaluate(symbolTable, heap);
        if (!value1.getType().equals(new BoolType()))
            throw new ExpExcept("1st operand is not of type bool\n");
        Value value2=exp2.evaluate(symbolTable, heap);
        if (!value2.getType().equals(new BoolType()))
            throw new ExpExcept("2nd operand is not of type bool\n");

        Value result=switch(operator){
            case 1->new BoolValue(((BoolValue)value1).getValue()&&((BoolValue)value2).getValue());
            case 2->new BoolValue(((BoolValue)value1).getValue()||((BoolValue)value2).getValue());
            default -> new BoolValue(true);
        };
        return result;
    }
    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type t1, t2;
        t1 = exp1.typeCheck(typeEnv);
        t2 = exp2.typeCheck(typeEnv);

        if (!t1.equals(new BoolType()))
            throw new ExpExcept("First operand is not of type bool");
        if (!t2.equals(new BoolType()))
            throw new ExpExcept("Second operand is not of type bool");
        return new BoolType();

    }

    @Override
    public String toString(){
        return " (" +exp1+ " " + operator + " " + exp2 + ") ";
    }
}
