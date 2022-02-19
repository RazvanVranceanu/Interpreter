package Model.Statement.Exam;

import Exceptions.ExpExcept;
import Exceptions.FileExcept;
import Exceptions.HeapExcept;
import Exceptions.StmtExcept;
import Model.ADT.HeapInterface;
import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.Expression.ArithExp;
import Model.Expression.Exp;
import Model.Expression.ValueExp;
import Model.PrgState;
import Model.Statement.CompStmt;
import Model.Statement.IStmt;
import Model.Statement.PrintStmt;
import Model.Type.Type;
import Model.Value.Value;

import java.io.FileNotFoundException;

public class SwitchStmt implements IStmt{

    private Exp switchExp, firstCaseExp, secondCaseExp;
    private IStmt firstCaseStmt, secondCaseStmt, defaultStmt;

    public SwitchStmt(Exp switchExp, Exp firstCaseExp, Exp secondCaseExp, IStmt firstCaseStmt, IStmt secondCaseStmt, IStmt defaultStmt) {
        this.switchExp = switchExp;
        this.firstCaseExp = firstCaseExp;
        this.secondCaseExp = secondCaseExp;
        this.firstCaseStmt = firstCaseStmt;
        this.secondCaseStmt = secondCaseStmt;
        this.defaultStmt = defaultStmt;
    }



    @Override
    public PrgState execute(PrgState programState) throws StmtExcept, ExpExcept, FileExcept, FileNotFoundException, HeapExcept {
        IStack<IStmt> executionStack = programState.getExeStack();
        IDict<String, Value> symbolTable = programState.getSymbolTable();
        HeapInterface heapTable = programState.getHeap();

        Value exp = switchExp.evaluate(symbolTable, heapTable);
        Value exp1 = firstCaseExp.evaluate(symbolTable, heapTable);
        Value exp2 = secondCaseExp.evaluate(symbolTable, heapTable);
        IStmt newStatement;
        if(exp.equals(exp1))
            newStatement = firstCaseStmt;
        else if(exp.equals(exp2))
            newStatement = secondCaseStmt;
        else
            newStatement = defaultStmt;
        executionStack.push(newStatement);
        programState.setExeStack(executionStack);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type type = switchExp.typeCheck(typeEnv);
        Type type1 = firstCaseExp.typeCheck(typeEnv);
        Type type2 = secondCaseExp.typeCheck(typeEnv);

        if (type.equals(type1) && type.equals(type2)) {
            firstCaseStmt.typeCheck(typeEnv.createCopy());
            secondCaseStmt.typeCheck(typeEnv.createCopy());
            defaultStmt.typeCheck(typeEnv.createCopy());
            return typeEnv;
        }
        else throw new RuntimeException("SwitchStmt: The expression types do not match");
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "SwitchStmt (" + switchExp +
                ")  case " + firstCaseExp +
                " : " + firstCaseStmt +
                ")  case "  + secondCaseExp +
                " : " + secondCaseStmt +
                ") default" + defaultStmt;
    }
}
