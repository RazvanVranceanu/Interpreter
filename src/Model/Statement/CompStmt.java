package Model.Statement;

import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.PrgState;
import Model.Type.Type;

public class CompStmt implements IStmt{

    private final IStmt stmt1;
    private final IStmt stmt2;

    public CompStmt(IStmt stmt1, IStmt stmt2) {
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;

    }
    public String toString(){
        String display="";
        display+="(" + stmt1.toString() + " " + stmt2.toString() + ");";
        return display;
    }
    @Override
    public PrgState execute(PrgState programState) {
        IStack<IStmt> executionStack = programState.getExeStack();
        executionStack.push(stmt2);
        executionStack.push(stmt1);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        return stmt2.typeCheck(stmt1.typeCheck(typeEnv));
    }

    public IStmt deepCopy(){
        return new CompStmt(this.stmt1, this.stmt2);
    }
}
