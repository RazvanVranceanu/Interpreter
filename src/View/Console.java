package View;

import Controller.Controller;
import Exceptions.*;
import Model.ADT.Dict;
import Model.PrgState;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Console {
//    Controller controller;
//    Scanner scanner;
//    int ProgramNumber;
//
//    Dict<Integer, Method> stateOption;
//    Dict<Integer, Method> programRuntimeCommands;
//
//    public Console(Controller controller) throws NoSuchMethodException {
//        this.controller = controller;
//        this.stateOption = new Dict<>();
//        this.programRuntimeCommands = new Dict<>();
//
//        this.stateOption.add(1, Console.class.getDeclaredMethod("runMainMenu"));
//        this.stateOption.add(2, Console.class.getDeclaredMethod("runProgram"));
//
//        this.programRuntimeCommands.add(1, Console.class.getDeclaredMethod("uiExecuteOneStep"));
//        this.programRuntimeCommands.add(2, Console.class.getDeclaredMethod("uiExecuteAllSteps"));
//
//        this.scanner = new Scanner(System.in);
//    }
//    public int runMainMenu(){
//        System.out.println("====================\n");
//        System.out.println("0. Exit\n");
//        System.out.println("1. Run example 1\n");
//        System.out.println("2. Run example 2\n");
//        System.out.println("3. Run example 3\n");
//        System.out.println("====================\n");
//
//        int option = this.inputCommand();
//        this.ProgramNumber = option;
//        return option == 0 ? 0 : 2;
//    }
//
//    public int runProgram() throws InvocationTargetException, IllegalAccessException {
//        System.out.println("====================\n");
//        System.out.println("0. Exit\n");
//        System.out.println("1. Execute one step\n");
//        System.out.println("2. Execute all steps\n");
//        System.out.println("3. Back to main menu\n");
//        System.out.println("====================\n");
//
//        int option = this.inputCommand();
//        while (option == 1 || option == 2) {
//            this.programRuntimeCommands.lookUp(option).invoke(this);
//            option = this.inputCommand();
//        }
//        return option == 0 ? 0 : 1;
//    }
//
//    public void run() throws InvocationTargetException, IllegalAccessException {
//        // 0 - quit | 1 - main menu | 2 - run program
//
//        int state = 1;
//        while (state != 0) {
//            state = (int) this.stateOption.lookUp(state).invoke(this);
//        }
//    }
//
//    private void uiExecuteOneStep() {
//        try {
//            PrgState prg = this.controller.getPrgFromId(this.ProgramNumber);
////            this.displayProgramState(this.controller.oneStep(prg));
//            this.controller.oneStep(prg);
//            this.controller.printPrgState(prg);
//        }
//        catch (ControllerException | ADTexcept | ExpExcept | StmtExcept | FileExcept e) {
//            System.out.println(e.getMessage());
////            e.printStackTrace();
//        }
//    }
//
//    private void uiExecuteAllSteps() {
//        try {
////            PrgState prg = this.controller.getPrgFromId(this.ProgramNumber);
//            this.controller.allSteps(this.ProgramNumber);
//        } catch (ControllerException | ADTexcept | ExpExcept | StmtExcept e) {
//            System.out.println(e.getMessage());
////            e.printStackTrace();
//        }
//    }
//
//    private int inputCommand() {
//        System.out.print(">");
//        String input = this.scanner.nextLine();
//        return Integer.parseInt(input);
//    }
//
//    public void displayProgramState(PrgState programState) {
//        System.out.println(programState.toString());
//    }
}
