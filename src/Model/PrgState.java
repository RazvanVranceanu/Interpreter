package Model;

import Model.ADT.*;
import Model.Statement.IStmt;
import Model.Value.Value;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class PrgState {
    private IStack<IStmt> exeStack;
    private IDict<String,Value> symbolTable;
    private IList<Value> out;
    IStmt originalPrg;
    private FileTableInterface fileTable;
    private HeapInterface heap;


    private static int freeID=0;
    private int id;


    public synchronized int getFreeId(){ return freeID; }
    public synchronized void setFreeId(int newId) { freeID = newId; }

    public PrgState(IStack<IStmt> exeStack,
                    IDict<String,Value> symbolTable,
                    IList<Value> out,
                    FileTableInterface fileTab,
                    HeapInterface heap,
                    IStmt originalPrg){
        this.exeStack=exeStack;
        this.symbolTable=symbolTable;
        this.out=out;
        this.fileTable = fileTab;
        this.heap=heap;
        this.id = getFreeId();
        setFreeId(getFreeId() + 1);
        this.originalPrg = originalPrg.deepCopy();
        this.exeStack.push(this.originalPrg);
    }


    public IStack<IStmt> getExeStack(){return exeStack;}
    public IDict<String,Value> getSymbolTable(){return symbolTable;}
    public IList<Value> getOutput(){return out;}
    public List<Value> getOutputList() {
//        List<Value> content = null;
        List<Value> v = new ArrayList<>();
        for(int i = 0; i < out.size(); i++) {
            v.add(out.get(i));
        }
        return v;
    }
    public FileTableInterface getFileTable(){return fileTable;}

    public HeapInterface getHeap(){return heap;}
    public Integer getThreadId(){
        return id;
    }

    public void setExeStack(IStack<IStmt> newExeStack){exeStack=newExeStack;}
    public void setSymbolTable(IDict<String,Value> newSymbolTable){symbolTable=newSymbolTable;}
    public void setOutput(IList<Value> newOutput){out=newOutput;}
    public void setFileTable(FileTableInterface newFileTable){fileTable=newFileTable;}
    public void setHeap(HeapInterface newHeap){heap=newHeap;} //new

    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws Exception {
//        IStack<IStmt> stk=state.getExeStack();
//        if(stk.isEmpty()) throw new ControllerException("prgstate stack is empty");
//
//        IStmt crtStmt = stk.pop();
//        return crtStmt.execute(state);
        if (exeStack.isEmpty())
            throw new Exception("EXE STACK-> EMPTY!!\n");
        IStmt currentStatement=exeStack.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString(){
        String display="";
        display+="\n";
        display+="PROGRAM ID= " + id +"\n";
        display+="EXE STACK = " + exeStack + "\n";
        display+="SYM TABLE = " + symbolTable + "\n";
        display+="OUT= " + out +"\n";
        display+="FILE TABLE = " + fileTable + "\n";
        display+="HEAP= " + heap + "\n";  //new
        display+="\n________________________________________________________________\n";
        return display;
    }


    public IStmt getOriginalProgram() {
        return originalPrg;
    }
}
