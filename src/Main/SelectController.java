package Main;

import Controller.Controller;
import Model.ADT.*;
import Model.ADT.Stack;
import Model.Expression.*;
import Model.PrgState;
import Model.Statement.*;
import Model.Statement.Exam.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.refType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class SelectController {
//    private List<IStmt> programStatements;
//    private RunMainPageController mainWindowController;

    @FXML
    private ListView<Controller> programListView;

    @FXML
    private Button executeButton;


    @FXML
    public void startProgram(ActionEvent event) {
        Controller controller = programListView.getSelectionModel().getSelectedItem();
        try {
            controller.typecheckOriginalProgram();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("runMainPage.fxml"));
            RunMainPageController execController = new RunMainPageController(controller);
            loader.setController(execController);
            AnchorPane executorRoot = loader.load();
            Scene executorScene = new Scene(executorRoot, 1000, 600);
            Stage executorStage = new Stage();
            executorStage.setScene(executorScene);
            executorStage.setTitle("Program Execution");
            executorStage.show();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("IOException Error");
            alert.setHeaderText("IOException Error!");
            alert.setContentText(e.toString());
            alert.showAndWait();
            e.printStackTrace();
        }
         catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception Error");
            alert.setHeaderText("Exception Error!");
            alert.setContentText(e.toString());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        programListView.setItems(getControllerList());
        programListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    private ObservableList<Controller> getControllerList() {
        MyList<IStmt> statements = getStmtList();
        LinkedList<Controller> list = new LinkedList<Controller>();
        for (int i = 0; i < statements.size(); ++i) {
            IStack<IStmt> stack = new Stack<>();
            PrgState prg = new PrgState(stack, new Dict<String, Value>(),  new MyList<Value>(), new FileTable(), new Heap(), statements.get(i));

            Repository repo = new Repository(prg,"dateOut" + Integer.toString(i + 1) + ".txt");
            Controller controller = new Controller(repo);
            list.add(controller);
        }

        return FXCollections.observableArrayList(list);
    }

    private MyList<IStmt> getStmtList() {
        IStmt ex1 = runExample1();
        IStmt ex2 = runExample2();
        IStmt ex3 = runExample3();
        IStmt ex4 = runExample4();
        IStmt ex5 = runExample5();
        IStmt ex6 = runExample6();
        IStmt ex7 = runExample7();
        IStmt ex8 = runExample8();
        IStmt ex9 = forkExample();
        IStmt ex10 = SwitchExample();
        IStmt ex11 = ForExample();
        IStmt ex12 = repeatUntilExample();
        IStmt ex13 = sleepExample();
        IStmt ex14 = conditionalExample();
        IStmt ex15 = conditionalExample2();
        IStmt ex16 = waitExample();
        IStmt ex17 = MULExample();

        MyList<IStmt> statementList = new MyList<IStmt>();
        statementList.append(ex1);
        statementList.append(ex2);
        statementList.append(ex3);
        statementList.append(ex4);
        statementList.append(ex5);
        statementList.append(ex6);
        statementList.append(ex7);
        statementList.append(ex8);
        statementList.append(ex9);
        statementList.append(ex10);
        statementList.append(ex11);
        statementList.append(ex12);
        statementList.append(ex13);
        statementList.append(ex14);
        statementList.append(ex15);
        statementList.append(ex16);
        statementList.append(ex17);

        return statementList;
    }

    private static IStmt MULExample(){
        /*
        v1=2;v2=3; (if (v1) then print(MUL(v1,v2)) else print (v1))
        The final Out should be {1}
         */
        return new CompStmt(new VarDeclStmt("v1", new IntType()),
                new CompStmt(new VarDeclStmt("v2", new IntType()),
                        new CompStmt(new AssignStmt("v1", new ValueExp(new IntValue(2))),
                                new CompStmt(new AssignStmt("v2", new ValueExp(new IntValue(3))),
                                        new IfStmt(
                                                new RelationalExp(new VarExp("v1"), new ValueExp(new IntValue(0)), "!="),
                                                new PrintStmt(new MULExp(new VarExp("v1"), new VarExp("v2"))),
                                                new PrintStmt(new VarExp("v1")))))));
    }
    
    private static IStmt waitExample(){
        /*
        v=20; wait(10);print(v*10)
        The final Out should be {10,9,8,7,6,5,4,3,2,1,200}
         */
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new WaitStmt(10),
                                new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))));
    }
    

    private static IStmt conditionalExample2(){
        /*
        bool b;
        int c;
        b=true;
        c=b?100:200; print(c);
        c= (false)?100:200; print(c);
        The final Out should be {100,200}
         */
        return new CompStmt(new VarDeclStmt("b", new BoolType()),
                new CompStmt(new VarDeclStmt("c", new IntType()),
                        new CompStmt(new AssignStmt("b", new ValueExp(new BoolValue(true))),
                                new CompStmt(new CondStmt("c", new VarExp("b"), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                        new CompStmt(new PrintStmt(new VarExp("c")),
                                                new CompStmt(new CondStmt("c", new ValueExp(new BoolValue(false)), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                        new PrintStmt(new VarExp("c"))))))));
    }

    private static IStmt conditionalExample(){
        /*
        Ref int a; Ref int b; int v;
        new(a,0); new(b,0);
        wh(a,1); wh(b,2);
        v=(rh(a)<rh(b))?100:200;
        print(v);
        v= ((rh(b)-2)>rh(a))?100:200; print(v);
        The final Out should be {100,200}
         */
        return new CompStmt(new VarDeclStmt("a", new refType(new IntType())),
                new CompStmt(new VarDeclStmt("b", new refType(new IntType())),
                        new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(0))),
                                        new CompStmt(new NewStmt("b", new ValueExp(new IntValue(0))),
                                                new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(1))),
                                                        new CompStmt(new WriteHeapStmt("b", new ValueExp(new IntValue(2))),
                                                                new CompStmt(new CondStmt(
                                                                        "v",
                                                                        new RelationalExp(new ReadHeapExp(new VarExp("a")), new ReadHeapExp(new VarExp("b")), "<"),
                                                                        new ValueExp(new IntValue(100)),
                                                                        new ValueExp(new IntValue(200))),
                                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                                new CompStmt(new CondStmt(
                                                                                        "v",
                                                                                        new RelationalExp(
                                                                                                new ArithExp('-', new ReadHeapExp(new VarExp("b")), new ValueExp(new IntValue(2))),
                                                                                                new ReadHeapExp(new VarExp("b")),
                                                                                                ">"),
                                                                                        new ValueExp(new IntValue(100)),
                                                                                        new ValueExp(new IntValue(200))),
                                                                                        new PrintStmt(new VarExp("v"))))))))))));
    }

    private static IStmt sleepExample(){
        /*
        v=10;
        (fork(v=v-1;v=v-1;print(v)); sleep(10);print(v*10)
        The final Out should be {8,100}
         */
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                        new CompStmt(new ForkStmt(new CompStmt(
                                new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))),
                                new CompStmt(new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("v"))))),
                                new CompStmt(new SleepStmt(10),
                                        new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10))))))));
    }

    private static IStmt repeatUntilExample(){
        /*
        v=0;
        (repeat (fork(print(v);v=v-1);v=v+1) until v==3); x=1;y=2;z=3;w=4;
        print(v*10)
        The final Out should be {0,1,2,30}
         */
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),
                        new CompStmt(new RepeatStmt(
                                new CompStmt(new ForkStmt(
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                        new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))),
                                new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(3)), "==")),
                                new CompStmt(new VarDeclStmt("x", new IntType()),
                                        new CompStmt(new VarDeclStmt("y", new IntType()),
                                                new CompStmt(new VarDeclStmt("z", new IntType()),
                                                        new CompStmt(new VarDeclStmt("w", new IntType()),
                                                                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(1))),
                                                                        new CompStmt(new AssignStmt("y", new ValueExp(new IntValue(2))),
                                                                                new CompStmt(new AssignStmt("z", new ValueExp(new IntValue(3))),
                                                                                        new CompStmt(new AssignStmt("w", new ValueExp(new IntValue(4))),
                                                                                                new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))))))))))));
    }

    private IStmt ForExample(){
        /*
        Ref int a; new(a,20);
        (for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a))); print(rh(a))
        The final Out should be {0,1,2,20}
         */
        return new CompStmt(new VarDeclStmt("a", new refType(new IntType())),
                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(
                                        new ForStmt(
                                            "v",
                                            new ValueExp(new IntValue(0)),
                                            new ValueExp(new IntValue(3)),
                                            new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))),
                                            new ForkStmt(
                                                    new CompStmt(
                                            new PrintStmt(new VarExp("v")),
                                            new AssignStmt("v", new ArithExp('*', new VarExp("v"), new ReadHeapExp(new VarExp("a"))))
                                            ))),
                                    new PrintStmt(new ReadHeapExp(new VarExp("a")))))));
    }

    private IStmt SwitchExample() {
        /*
        a=1;b=2;c=5;
        (switch(a*10)
        (case (b*c) : print(a);print(b)) (case (10) : print(100);print(200)) (default : print(300)));
        print(300)
        The final Out should be {1,2,300}
         */
        return new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new VarDeclStmt("c", new IntType()),
                                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(1))),
                                        new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(2))),
                                                new CompStmt(new AssignStmt("c", new ValueExp(new IntValue(5))),
                                                        new CompStmt(
                                                                new SwitchStmt(
                                                                        new ArithExp('*', new VarExp("a"), new ValueExp(new IntValue(10))),
                                                                        new ArithExp('*', new VarExp("b"), new VarExp("c")),
                                                                        new ValueExp(new IntValue(10)),
                                                                        new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                                                        new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                                                                        new PrintStmt(new ValueExp(new IntValue(300)))),
                                                                new PrintStmt(new ValueExp(new IntValue(300)))))
                                        )))));
    }



    public static IStmt forkExample(){
        /* int v;
         Ref int a;
         v=10;
         new(a,22);

         fork(wH(a,30);v=32;print(v);print(rH(a)));
         print(v);print(rH(a))
        At the end:
        Id=1
        SymTable_1={v->10,a->(1,int)}
        Id=10
        SymTable_10={v->32,a->(1,int)}
        Heap={1->30}
        Out={10,30,32,30}
        */
        return new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new VarDeclStmt("a",new refType(new IntType())),
                        new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a",new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(
                                                new WriteHeapStmt("a",new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));
    }

    private static IStmt runExample8(){
        return new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-',
                                        new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
    }

    private static IStmt runExample7(){
        return new CompStmt(new VarDeclStmt("v", new refType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new refType(new refType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));
//        return new CompStmt(new VarDeclStmt("x",new refType(new IntType())),
//                new CompStmt(new NewStmt("x",new ValueExp(new IntValue(20))),
//                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("x"))),
//                                new CompStmt(new WriteHeapStmt("x", new ValueExp(new IntValue(20))),
//                                        new PrintStmt(new ArithExp('+', new ReadHeapExp(new VarExp("x")), new ValueExp(new IntValue(5))))))));
    }

    private static IStmt runExample6() {

        return new CompStmt(new VarDeclStmt("v", new refType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExp(new VarExp("v")), new ValueExp((new IntValue(5)))))))));
    }

    private static IStmt runExample5() {

        return new CompStmt(new VarDeclStmt("v", new refType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new refType(new refType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5))
                                                )))))));
    }

    private static IStmt runExample4() {
        return new CompStmt(new VarDeclStmt("v", new refType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new refType(new refType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));
    }

    private static IStmt runExample3() {
        return new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(
                new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a",
                new ValueExp(new BoolValue(true))), new CompStmt(new IfStmt(new VarExp("a"),
                new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v",
                new ValueExp(new IntValue(3)))), new PrintStmt(
                new VarExp("v"))))));
    }

    private static IStmt runExample2() {
        return new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(
                                new IntValue(2)), new ArithExp('*',new ValueExp(new IntValue(3)),
                                new ValueExp(new IntValue(5))))), new CompStmt(new AssignStmt("b",
                                new ArithExp('+', new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(
                                new VarExp("b"))))));
    }

    private static IStmt runExample1() {
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(
                        new IntValue(2))), new PrintStmt(new VarExp("v"))));
    }
}
