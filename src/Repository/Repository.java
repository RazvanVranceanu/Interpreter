package Repository;

import Exceptions.ControllerException;
import Exceptions.MyException;
import Model.PrgState;
import Model.Statement.IStmt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{

    private final String logFilePath;
    private List<PrgState> programStates;
    private boolean firstTime = true;
    private IStmt originalProgram;

    public Repository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    public Repository(PrgState prgState, String logFilePath) {
        this.originalProgram = prgState.getOriginalProgram();
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
        programStates.add(prgState);
    }

    @Override
    public List<PrgState> getPrgList() {
        return programStates;
    }

    @Override
    public void setPrgList(List<PrgState> prg_lst) {
        this.programStates = prg_lst;
    }

    //useless
    //todo: remove it
    @Override
    public PrgState getCrtPrg() {
        return programStates.get(0);
    }

    @Override
    public void printPrgState(PrgState prg) throws ControllerException {
        System.out.print(prg.toString());
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException, IOException {
        try {
            File myFile = new File(logFilePath);
            myFile.createNewFile();

            if(firstTime){
                PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, false)));
                logFile.write(prgState.toString());
                logFile.close();
                firstTime = false;
            }
            else {
                PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
                logFile.write(prgState.toString());
                logFile.close();
            }
        }
        catch (IOException e){
            System.out.println("FILE ERROR: File path was not found\n");
        }
    }

    @Override
    public void add(PrgState prg) {
        programStates.add(prg);
    }

    @Override
    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public PrgState getProgramStateWithId(int currentId) {
        for(PrgState pr : programStates)
            if(pr.getThreadId() == currentId)
                return pr;
        return null;
    }
}
