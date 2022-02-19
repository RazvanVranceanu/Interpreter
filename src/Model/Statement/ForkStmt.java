package Model.Statement;

import Exceptions.ExpExcept;
import Exceptions.FileExcept;
import Exceptions.HeapExcept;
import Exceptions.StmtExcept;
import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.ADT.Stack;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

import java.io.FileNotFoundException;
import java.util.Map;

public class ForkStmt implements IStmt {
    IStmt forkStmt;

    public ForkStmt(IStmt forkStmtvar)
    {
        this.forkStmt=forkStmtvar;
    }

//    @Override
//    public PrgState execute_old(PrgState programState) throws StmtExcept, ExpExcept, FileExcept, FileNotFoundException, HeapExcept {
//        IDict<String, Value> symTableCopy = programState.getSymbolTable().createCopy();
//        IStack<IStmt> newExeStack = new Stack<>();
//        newExeStack.push(this.forkStmt);
//        PrgState newPrgState = new PrgState(newExeStack, symTableCopy, programState.getOutput(), programState.getFileTable(),programState.getHeap());
//        PrgState newPrgState = new PrgState(newExeStack, symTableCopy, programState.getOutput(), programState.getFileTable(), forkStmt, programState.getHeap());
//        System.out.println(newPrgState.toString());
//        newPrgState.setId();
//        return newPrgState;
//    }

    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept, FileExcept, FileNotFoundException, HeapExcept{
        //todo: vezi daca terbuie .createCopy()
        var symTable = programState.getSymbolTable().createCopy();
        var heap = programState.getHeap();
        var out = programState.getOutput();
        var fileTable = programState.getFileTable();
        IStack<IStmt> newStack = new Stack<>();
        IDict<String, Value> newSymTable = new Dict<String, Value>();
        for(Map.Entry<String, Value> entry : symTable.getContent().entrySet()) {
            newSymTable.update(entry.getKey(), entry.getValue());
        }
        return new PrgState(newStack, symTable, out, fileTable, heap, forkStmt);
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        forkStmt.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(forkStmt);
    }

    @Override
    public String toString(){
        return " FORK STMT(" + forkStmt.toString() +") ";
    }
}
