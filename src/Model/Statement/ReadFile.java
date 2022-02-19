package Model.Statement;

import Exceptions.ExpExcept;
import Exceptions.FileExcept;
import Exceptions.StmtExcept;
import Model.ADT.FileTableInterface;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{
    private final Exp expr;
    private final String var;

    public ReadFile(Exp expr, String var) {
        this.expr = expr;
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept, FileExcept {
        IDict<String, Value> symbolTable=programState.getSymbolTable();
        HeapInterface heap = programState.getHeap();
        if (!symbolTable.isDefined(var))
        {
            throw new StmtExcept("Var"+var+"is not defined");
        }
        if (!symbolTable.lookUp(var).getType().equals(new IntType()))
        { throw new StmtExcept("Var" + var + "should be int");}
        Value value=expr.evaluate(symbolTable,heap);
        if (!value.getType().equals(new StringType()))
            throw new FileExcept("Expected a string");
        FileTableInterface FileTable=programState.getFileTable();
        StringValue file_name=(StringValue)value;
        if (!FileTable.isOpened(file_name))
            throw new FileExcept("The file is not opened");
        BufferedReader fileDescriptor=FileTable.getFileDescriptor(file_name);

        int reader;
        try{
            String line=fileDescriptor.readLine();
            if (line==null)
                reader=0;
            else
                reader=Integer.parseInt(line);
        }
        catch (IOException ioerror)
        {
            throw new FileExcept("File is unavailable for reading");
        }
        symbolTable.update(var,new IntValue(reader));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(this.expr, this.var);
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type typeVar = (Type) typeEnv.lookUp(var);
        if (!(typeVar instanceof IntType))
            throw new StmtExcept("Var type should be an int");
        Type typeExp = expr.typeCheck(typeEnv);
        if (!(typeExp instanceof  StringType))
            throw new StmtExcept("Expression should be a string");
        return typeEnv;
    }

    @Override
    public String toString() {
        return " READ FILE(" + var.toString() + ") ";
    }

}
