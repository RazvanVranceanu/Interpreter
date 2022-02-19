package Model.Statement;

import Exceptions.ExpExcept;
import Exceptions.StmtExcept;
import Model.Expression.Exp;
import Model.PrgState;
import Model.ADT.*;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class IfStmt implements IStmt{
    private final Exp expr;
    private final IStmt ifStmt;
    private final IStmt elseStmt;

    public IfStmt(Exp expr, IStmt ifStmt, IStmt elseStmt) {
        this.expr = expr;
        this.ifStmt = ifStmt;
        this.elseStmt = elseStmt;
    }


    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept {
        IStack<IStmt> executionStack = programState.getExeStack();
        HeapInterface heap = programState.getHeap();
        Value condition=expr.evaluate(programState.getSymbolTable(),heap);
        if (!condition.getType().equals(new BoolType()))
            throw new StmtExcept("conditional expr is not a boolean ±\n");
        if (((BoolValue)condition).getValue())
            executionStack.push(ifStmt);
        else
            executionStack.push(elseStmt);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type typeExp = expr.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())){
            ifStmt.typeCheck(typeEnv.createCopy());
            elseStmt.typeCheck(typeEnv.createCopy());
            return typeEnv;
        }
        else {
            throw new StmtExcept("The condition of IF has not type BOOL");
        }
    }

    public String toString(){
        String display="";
        display+="IF (" + expr + ") THEN{ " + ifStmt+" ELSE { " + elseStmt+"} ";
        return display;
    }

    public IStmt deepCopy() {
        return new IfStmt(this.expr, this.ifStmt, this.elseStmt);
    }
}
