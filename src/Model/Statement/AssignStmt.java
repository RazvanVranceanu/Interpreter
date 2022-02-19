package Model.Statement;

import Exceptions.ExpExcept;
import Exceptions.StmtExcept;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStmt implements IStmt{
    private final String id;
    private final Exp expr;

    public AssignStmt(String id, Exp expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept {
        IDict<String, Value> symbolTable = programState.getSymbolTable();
        HeapInterface heap = programState.getHeap();
        if (!symbolTable.isDefined(id))
            throw new StmtExcept("Variable id is not declared ±\n");

        Value value=expr.evaluate(symbolTable,heap);
        if (!value.getType().equals(symbolTable.lookUp(id).getType()))
            throw new StmtExcept("Type of expression and type of variable do not match ±\n");
        symbolTable.update(id,value);
        return null;
    }

    @Override
    public String toString(){
        return id + "= " +expr;
    }

    public IStmt deepCopy(){
        return new AssignStmt(this.id, this.expr);
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type typeVar = (Type)typeEnv.lookUp(id);
        Type typeExp = expr.typeCheck(typeEnv);
        if (typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new StmtExcept("RHS and LFS have different types");
    }
}
