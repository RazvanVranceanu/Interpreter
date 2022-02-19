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
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt{

    private final Exp expr;

    public OpenRFile(Exp expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept, FileExcept {
        IDict<String, Value> symbolTable = programState.getSymbolTable();
        HeapInterface heap = programState.getHeap();
        Value value = expr.evaluate(symbolTable,heap);

        if (!value.getType().equals(new StringType()))
            throw new FileExcept("Expected a string for the file path\n");

        StringValue fileName = (StringValue) value;
        FileTableInterface FileTable = programState.getFileTable();
        if (FileTable.isOpened(fileName))
            throw new FileExcept("File" + fileName +"already opened");
        try {
            BufferedReader fileDescr = new BufferedReader(new FileReader(fileName.getValue()));
            FileTable.addFile(fileName, fileDescr);
        } catch (IOException ioerror) {
            throw new FileExcept("Unavailable file");
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type typeExp = expr.typeCheck(typeEnv);
        if (!(typeExp.equals(new StringType())))
            throw new StmtExcept("Expression should be a string");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(this.expr);
    }

    @Override
    public String toString() {
        return " OPEN FILE(" + expr.toString() + ") ";
    }
}
