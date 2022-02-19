package Model.Statement.Exam;

import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.Expression.Exp;
import Model.Expression.RelationalExp;
import Model.Expression.VarExp;
import Model.PrgState;
import Model.Statement.AssignStmt;
import Model.Statement.CompStmt;
import Model.Statement.IStmt;
import Model.Statement.WhileStmt;
import Model.Type.IntType;
import Model.Type.Type;

import java.io.IOException;

public class ForStmt implements IStmt {
    private String var;
    private Exp initialization;
    private Exp condition;
    private Exp increment;
    private IStmt statement;

    public ForStmt(String var, Exp initialization, Exp condition, Exp increment, IStmt statement) {
        this.var = var;
        this.initialization = initialization;
        this.condition = condition;
        this.increment = increment;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) {
        IStack<IStmt> executionStack = state.getExeStack();
        IStmt newStatement =
                new CompStmt(
                        new AssignStmt(var, initialization),
                        new WhileStmt(new RelationalExp(new VarExp("v"), condition, "<"),
                                new CompStmt(statement,
                                        new AssignStmt(var, increment)
                                )
                        )
                );
        executionStack.push(newStatement);
        state.setExeStack(executionStack);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type type = initialization.typeCheck(typeEnv);
        Type type1 = condition.typeCheck(typeEnv);
        Type type2 = increment.typeCheck(typeEnv);

        if (type.equals(new IntType()) && type1.equals(new IntType()) && type2.equals(new IntType())) return typeEnv;
        else throw new RuntimeException("ForStmt: Invalid types");
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString(){
        return "for( " + var + "=" + initialization.toString() + "; " + var + "<" + condition.toString() + "; " + var + "=" + increment.toString() + " ) \n " + statement.toString();
    }
}
