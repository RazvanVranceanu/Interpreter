package Model.Statement;

import Exceptions.ExpExcept;
import Exceptions.FileExcept;
import Exceptions.HeapExcept;
import Exceptions.StmtExcept;
import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.Expression.Exp;
import Model.Expression.RelationalExp;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

import java.io.FileNotFoundException;

public class WhileStmt implements IStmt{
    private final Exp expr;
    private final IStmt whileStmt;

    public WhileStmt(Exp expr, IStmt whileStmt) {
        this.expr = expr;
        this.whileStmt = whileStmt;
    }

    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept, FileExcept, FileNotFoundException, HeapExcept {
        IStack<IStmt> exeStack = programState.getExeStack();

        Value cond = null;

        try {
            cond = expr.evaluate(programState.getSymbolTable(), programState.getHeap());
        }
        catch (ExpExcept e){
            e.printStackTrace();
        }

        if (!cond.getType().equals(new BoolType()))
            throw new StmtExcept("Bool type required\n");

        boolean flag = ((BoolValue)cond).getValue();

        if (flag)
        {
            exeStack.push(this);
            exeStack.push(whileStmt);
        }

        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        whileStmt.typeCheck(typeEnv);
        Type typeExp=expr.typeCheck(typeEnv);
        System.out.println(expr instanceof RelationalExp);
        //System.out.println(typeExp.toString());
        if(!(typeExp.equals(new BoolType())))
        {
            throw new StmtExcept("Expression should be a bool");
        }
        return typeEnv;
    }

    @Override
    public String toString(){
        return " WHILE ( "+expr+" )"  + " {"+ whileStmt  +"}";
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(this.expr, this.whileStmt);
    }
}
