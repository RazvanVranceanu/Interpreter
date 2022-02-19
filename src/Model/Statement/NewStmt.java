package Model.Statement;

import Exceptions.*;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.Type;
import Model.Type.refType;
import Model.Value.Value;
import Model.Value.refValue;

import java.io.FileNotFoundException;

public class NewStmt implements IStmt{
    public final String name;
    private final Exp expr;

    public NewStmt(String name, Exp expr) {
        this.name = name;
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept, FileExcept, FileNotFoundException, HeapExcept {
        IDict<String, Value> symbolTable=programState.getSymbolTable();
        if(!symbolTable.isDefined(name))
            throw new StmtExcept("Name not found\n");
        refValue refVal = (refValue) symbolTable.lookUp(name);

        Value value = null;

        try {
            value = expr.evaluate(symbolTable,programState.getHeap());
        } catch (ExpExcept expressionExcep) {
            expressionExcep.printStackTrace();
        }

        if(!value.getType().equals(refVal.getLocationType()))
            try {
                throw new HeapExcept("Type is not matching\n");
            } catch (HeapExcept e) {
                e.printStackTrace();
            }

        HeapInterface heap = programState.getHeap();
        int addr = heap.add(value);
        symbolTable.update(name,new refValue(addr,refVal.getLocationType()));

        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type typeVar= (Type) typeEnv.lookUp(name);
        Type typeExp = expr.typeCheck(typeEnv);
        if (typeVar.equals(new refType(typeExp)))
            return typeEnv;
        else
            throw new StmtExcept("NEW STMT: RHS and LFS have different types");
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(name, expr);
    }


    @Override
    public String toString(){
        return " NEW STMT (" + this.name +"," + this.expr.toString() + ") ";
    }
}
