package Model.Statement;

import Exceptions.ExpExcept;
import Exceptions.FileExcept;
import Exceptions.StmtExcept;
import Model.ADT.FileTableInterface;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt{
    private final Exp expr;

    public CloseRFile(Exp expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept, FileExcept {
        IDict<String, Value> symbolTable = programState.getSymbolTable();
        HeapInterface heap = programState.getHeap();
        Value value = expr.evaluate(symbolTable,heap);

        if (!value.getType().equals(new StringType()))
            throw new FileExcept("Expected a string for the file path");

        StringValue fileName = (StringValue) value;
        FileTableInterface FileTable = programState.getFileTable();

        if (!FileTable.isOpened(fileName))
            throw new FileExcept("File is not opened");

        BufferedReader fileDescriptor = FileTable.getFileDescriptor(fileName);


        try {
            fileDescriptor.close();

        } catch (IOException ioerror) {
            throw new FileExcept("Unavailable file");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(this.expr);
    }

    @Override
    public String toString() {
        return " CLOSE FILE(" + expr.toString() + ") ";
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type typeExp = expr.typeCheck(typeEnv);
        if (!(typeExp.equals(new StringType())))
            throw new StmtExcept("Expression should be a string");
        else
            return typeEnv;
    }
}
