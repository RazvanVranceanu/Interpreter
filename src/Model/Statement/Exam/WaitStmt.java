package Model.Statement.Exam;

import Exceptions.ExpExcept;
import Exceptions.FileExcept;
import Exceptions.HeapExcept;
import Exceptions.StmtExcept;
import Model.ADT.IDict;
import Model.Expression.ValueExp;
import Model.PrgState;
import Model.Statement.CompStmt;
import Model.Statement.IStmt;
import Model.Statement.PrintStmt;
import Model.Type.Type;
import Model.Value.IntValue;

import java.io.FileNotFoundException;

public class WaitStmt implements IStmt {
    private int number;
    
    public WaitStmt(int number) { this.number = number; }

    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept, FileExcept, FileNotFoundException, HeapExcept {
        if (this.number!=0) programState.getExeStack().push(
                new CompStmt(
                        new PrintStmt(new ValueExp(new IntValue(this.number))),
                        new WaitStmt(this.number-1)));
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "WaitStatement{" + number + '}';
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
