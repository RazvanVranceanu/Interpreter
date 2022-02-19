package Repository;

import Exceptions.ControllerException;
import Exceptions.MyException;
import Model.PrgState;
import Model.Statement.IStmt;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prg_lst);
    public PrgState getCrtPrg();
    void printPrgState(PrgState prg) throws ControllerException;
    void logPrgStateExec(PrgState prgState) throws MyException, IOException;
    void add(PrgState progState);
    IStmt getOriginalProgram();

    PrgState getProgramStateWithId(int currentId);
}
