package Controller;

import Exceptions.*;
import Model.ADT.Dict;
import Model.ADT.IStack;
import Model.PrgState;
import Model.Statement.IStmt;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.refValue;
import Repository.IRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Controller {
    private final IRepository repository;
    private ExecutorService executor = Executors.newFixedThreadPool(2);;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public IRepository getRepository() {
        return this.repository;
    }

    public void oneStepForAllPrg(List<PrgState> prgStateList) throws InterruptedException, MyException {
        prgStateList.forEach(prg-> {
            try {
                repository.printPrgState(prg);
                repository.logPrgStateExec(prg);
            } catch (ControllerException | MyException | IOException e) {
                e.printStackTrace();
            }
        });
        List <Callable<PrgState>> callList = prgStateList.stream()
                .map((PrgState p)->(Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());
        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException | InterruptedException e) {
                            //System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            prgStateList.addAll(newPrgList);
        }
        catch(InterruptedException e)
        {
            throw new MyException(e.getMessage());
        }
        prgStateList.forEach(prg -> {
            try {
                repository.printPrgState(prg);
            } catch (ControllerException e) {
                e.printStackTrace();
            }
        });


        repository.setPrgList(prgStateList);
    }

    //    public void allSteps() throws ControllerException, ADTexcept, ExpExcept, StmtExcept, MyException, IOException {
//
//        PrgState prg = repository.getCrtPrg();
//        repository.printPrgState(prg);
//        repository.logPrgStateExec();
//        while (!prg.getExeStack().isEmpty()) {
//            try {
//                oneStep(prg);
//                repository.printPrgState(prg);
//                repository.logPrgStateExec();
//
//                // new
//                prg.getHeap().setHeap((HashMap<Integer, Value>) unsafeGarbageCollector(
//                        getAddrFromSymTable(prg.getSymbolTable().getContent().values()),
//                        prg.getHeap().getContent()));
//
//            } catch (ControllerException | ADTexcept | StmtExcept | ExpExcept | FileExcept | HeapExcept exception) {
//                throw new ControllerException(exception.getMessage());
//            }
//        }
//    }
    public void allSteps() throws InterruptedException, MyException {
        executor = Executors.newFixedThreadPool(2); //todo:

        // removing the completed programs
        List<PrgState> programStates = removeCompletedPrg(repository.getPrgList());
        while (programStates.size() > 0) {
            GarbageCollector.conservativeGarbageCollector(programStates);
            oneStepForAllPrg(programStates);
            programStates = removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(programStates);
    }

//    Map<Integer, Value> unsafeGarbageCollector(List<Integer> addresses, Map<Integer, Value> heap) {
//        return heap.entrySet().stream()
//                .filter(elem -> addresses.contains(elem.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }
//
//    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
//        return symTableValues.stream()
//                .filter(v -> v instanceof refValue)
//                .map(v -> {
//                    refValue v1 = (refValue) v;
//                    return v1.getAddress();
//                })
//                .collect(Collectors.toList());
//    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

//    public void printPrgState(PrgState prg) throws ControllerException {
//        repository.printPrgState(prg);
//    }

    public void executeOneStep() throws InterruptedException, MyException {
        List<PrgState> list = removeCompletedPrg(repository.getPrgList());
        GarbageCollector.conservativeGarbageCollector(list);
        oneStepForAllPrg(list);
        list = removeCompletedPrg(repository.getPrgList());
        repository.setPrgList(list);
        if (list.isEmpty()) {
            executor.shutdownNow();
        }
    }

    public void typecheckOriginalProgram() throws Exception {
        Dict<String, Type> typeEnvironment = new Dict<String, Type>();
        IStmt originalProgram = repository.getOriginalProgram();
        originalProgram.typeCheck(typeEnvironment);
    }

    @Override
    public String toString() {
        return repository.getOriginalProgram().toString();
    }
}
