package Model.Statement.Exam;

import Model.ADT.IDict;
import Model.PrgState;
import Model.Statement.IStmt;
import Model.Type.Type;

public class SleepStmt implements IStmt {

    private int nr;

    public SleepStmt(int n) { nr=n;}

    @Override
    public PrgState execute(PrgState state){
        if (nr != 0)
            state.getExeStack().push(new SleepStmt(nr-1));
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv){
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
            return null;
    }

    @Override
    public String toString() {
        return "Sleep(" + nr + ')';
    }
}
