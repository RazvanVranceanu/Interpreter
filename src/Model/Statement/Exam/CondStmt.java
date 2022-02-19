package Model.Statement.Exam;


import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.Expression.Exp;
import Model.Expression.VarExp;
import Model.PrgState;
import Model.Statement.AssignStmt;
import Model.Statement.IStmt;
import Model.Statement.IfStmt;
import Model.Type.BoolType;
import Model.Type.Type;

import java.io.IOException;

public class CondStmt implements IStmt {

    private String var;
    private Exp cond;
    private Exp trueCond;
    private Exp falseCond;

    public CondStmt(String var, Exp cond, Exp trueCond, Exp falseCond) {
        this.var = var;
        this.cond = cond;
        this.trueCond = trueCond;
        this.falseCond = falseCond;
    }


    @Override
    public PrgState execute(PrgState state) {
        IStack<IStmt> exeStack = state.getExeStack();
        IStmt newStatement = new IfStmt(cond, new AssignStmt(var, trueCond), new AssignStmt(var, falseCond));
        exeStack.push(newStatement);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type typev = new VarExp(var).typeCheck(typeEnv);
        Type type = cond.typeCheck(typeEnv);
        Type type1 = trueCond.typeCheck(typeEnv);
        Type type2 = falseCond.typeCheck(typeEnv);

        if (type.equals(new BoolType()) && type1.equals(typev) && type2.equals(typev)) return typeEnv;
        else throw new RuntimeException("CondStmt: Invalid types");
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return var + " = " + cond.toString() + " ? " + trueCond.toString() + " : " + falseCond.toString();
    }
}
