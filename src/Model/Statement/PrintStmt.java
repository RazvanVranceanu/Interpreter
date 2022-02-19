package Model.Statement;

import Exceptions.ExpExcept;
import Exceptions.StmtExcept;
import Model.Expression.Exp;
import Model.PrgState;
import Model.ADT.*;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStmt implements IStmt{
    private final Exp expr;

    public PrintStmt(Exp expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept {
        IList<Value> output=programState.getOutput();
        HeapInterface heap = programState.getHeap();
        IDict<String,Value> symbolTable=programState.getSymbolTable();
        output.append(expr.evaluate(symbolTable,heap));
        return null; //todo: return prog state for all
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        expr.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        return " OUT(" + expr + ") ";
    }

    public IStmt deepCopy(){
        return new PrintStmt(this.expr);
    }
}
