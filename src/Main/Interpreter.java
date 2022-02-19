package Main;

import Controller.Controller;
import JUnitTests.TestingExpressions;
import Model.ADT.*;
import Model.Expression.*;
import Model.PrgState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.refType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import View.ExitCmd;
import View.RunExampleCmd;
import View.TextMenuCls;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Interpreter extends Application{

    @Override
    public void start(Stage stage) throws Exception{

        try {
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("SelectProgram.fxml"));
            Scene scene = new Scene(root,1000,800);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Select program");
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
