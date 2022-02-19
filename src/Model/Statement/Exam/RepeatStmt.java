package Model.Statement.Exam;

import Model.ADT.IDict;
import Model.Expression.Exp;
import Model.Expression.InvertExp;
import Model.PrgState;
import Model.Statement.CompStmt;
import Model.Statement.IStmt;
import Model.Statement.WhileStmt;
import Model.Type.BoolType;
import Model.Type.Type;

import java.io.IOException;

public class RepeatStmt implements IStmt {
    private final IStmt repeatThis;
    private final Exp untilThis;

    public RepeatStmt(IStmt repeat, Exp until) {
        this.repeatThis = repeat;
        this.untilThis = until;
    }

    @Override
    public PrgState execute(PrgState state)  {
        IStmt newRepeat = new CompStmt(repeatThis, new WhileStmt(new InvertExp(untilThis), repeatThis));
        state.getExeStack().push(newRepeat);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type typeExp = untilThis.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            repeatThis.typeCheck(typeEnv.createCopy());
            return typeEnv;
        } else {
            System.out.println(untilThis.toString());
            throw new RuntimeException("RepeatStmt: Expression must be bool type");
        }
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return " repeat " + repeatThis.toString() +
                " until " + untilThis.toString();
    }
}
