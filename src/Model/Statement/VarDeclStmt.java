package Model.Statement;

import Exceptions.ExpExcept;
import Exceptions.StmtExcept;
import Model.ADT.IDict;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class VarDeclStmt implements IStmt{
    private final Type type;
    private final String id;

    public VarDeclStmt(String id, Type type) {
        this.type = type;
        this.id = id;
    }

    @Override
    public PrgState execute(PrgState programState)  throws StmtExcept, ExpExcept {
        IDict<String, Value> symbolTable=programState.getSymbolTable();
        if (symbolTable.isDefined(id))
            throw new StmtExcept("\n");
        symbolTable.add(this.id,this.type.defaultValue());
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        typeEnv.add(id, type);
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return id+"<-"+type;
    }

    public IStmt deepCopy(){
        return new VarDeclStmt(this.id, this.type);
    }
}
