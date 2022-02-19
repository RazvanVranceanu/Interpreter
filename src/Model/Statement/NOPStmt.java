package Model.Statement;

import Exceptions.ExpExcept;
import Exceptions.StmtExcept;
import Model.ADT.IDict;
import Model.PrgState;
import Model.Type.Type;

public class NOPStmt implements IStmt{
    public NOPStmt(){}

    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept {
        return null;
    }

    public IStmt deepCopy(){
        return new NOPStmt();
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        return typeEnv;
    }
}
