package Model.Statement;

import Exceptions.ExpExcept;
import Exceptions.FileExcept;
import Exceptions.HeapExcept;
import Exceptions.StmtExcept;
import Model.ADT.IDict;
import Model.PrgState;
import Model.Type.Type;

import java.io.FileNotFoundException;

public interface IStmt {
    PrgState execute(PrgState programState) throws StmtExcept, ExpExcept, FileExcept, FileNotFoundException, HeapExcept;
    IDict<String, Type> typeCheck(IDict<String,Type> typeEnv) throws Exception;
    IStmt deepCopy();

}
