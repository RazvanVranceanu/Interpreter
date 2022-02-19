package Main;

import Controller.Controller;
import Exceptions.MyException;
import Model.ADT.*;
import Model.DTO.HeapEntry;
import Model.DTO.SymTableEntry;
import Model.PrgState;

import Model.Statement.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RunMainPageController{

    private PrgState lastMainProgram;
    private Controller controller;

    @FXML
    private TableView<HeapEntry> heapTableView;
    @FXML
    private TableColumn<HeapEntry, String> heapAddressColumn;
    @FXML
    private TableColumn<HeapEntry, String> heapValueColumn;
    @FXML
    private ListView<StringValue> fileTableListView;
    @FXML
    private TableView<SymTableEntry> symbolTableView;
    @FXML
    private TableColumn<SymTableEntry, String> symbolTableVariableColumn;
    @FXML
    private TableColumn<SymTableEntry, String> symbolTableValueColumn;
    @FXML
    private ListView<Value> outputListView;
    @FXML
    private ListView<Integer> programStateListView;
    @FXML
    private ListView<IStmt> executionStackListView;
    @FXML
    private TextField numberOfProgramStatesTextField;
    @FXML
    private Button executeOneStepButton;

    public RunMainPageController(Controller givenController){
        controller = givenController;
        this.lastMainProgram = null;
    }

    public void setController(Controller controller){
        this.controller = controller;
        populateProgramStateIdentifiers();
    }

    private void populateProgramStateIdentifiers() {
        List<PrgState> programStates = controller.getRepository().getPrgList();
        programStateListView.setItems(FXCollections.observableList(getProgramStateIds(programStates)));

        numberOfProgramStatesTextField.setText("" + programStates.size());
    }

    private List<Integer> getProgramStateIds(List<PrgState> programStateList) {
        return programStateList.stream().map(PrgState::getThreadId).collect(Collectors.toList());
    }

    @FXML
    public void initialize(){
        heapAddressColumn.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("address"));
        heapValueColumn.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("value"));
        symbolTableVariableColumn.setCellValueFactory(new PropertyValueFactory<SymTableEntry, String>("name"));
        symbolTableValueColumn.setCellValueFactory(new PropertyValueFactory<SymTableEntry, String>("value"));
        programStateListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        try {
            populateWidgets();
        }
        catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing to execute");
            alert.setHeaderText("There is nothing left to execute!");

            alert.showAndWait();
        }

    }

    @FXML
    private void executeOneStep() throws MyException {
        try {
            List<PrgState> states = controller.getRepository().getPrgList();
            if (states.size() > 0)
                controller.executeOneStep();
        }
        catch (InterruptedException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Execution Error");
            alert.setHeaderText("An execution error has occured!");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }

        try {
            populateWidgets();
        }
        catch(MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("All done");
            alert.setHeaderText("Nothing left to execute");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    private void populateWidgets() throws MyException {
        List<PrgState> states = controller.getRepository().getPrgList();
        PrgState currentProgram;
        if (states.size() == 0) {
            numberOfProgramStatesTextField.setText("0");
            if (lastMainProgram == null) {
                throw new MyException("Empty program");
            }
            else {
                currentProgram = lastMainProgram;
                lastMainProgram = null;
            }
        }
        else {
            lastMainProgram = states.get(0);
            currentProgram = states.get(0);
            numberOfProgramStatesTextField.setText("Program states: " + states.size());
        }

        populatePrgStateListView(states);
        programStateListView.getSelectionModel().selectIndices(0);
        populateExecutionStack(currentProgram);
        populateHeapTable(currentProgram);
        populateSymbolTable(currentProgram);
        populateOutput(currentProgram);
        populateFileTable(currentProgram);
        
    }


    private void populateHeapTable(PrgState currentProgramState) {
        HeapInterface heap = currentProgramState.getHeap();
        ArrayList<HeapEntry> entries = new ArrayList<HeapEntry>();
        for (Map.Entry<Integer, Value> entry: heap.getHeap().entrySet()) {
            entries.add(new HeapEntry(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(entries));
        heapTableView.refresh();
    }

    //todo: e bine?
    private void populateFileTable(PrgState currentProgramState) {
        FileTableInterface fileTable = currentProgramState.getFileTable();
        List<StringValue> list = new ArrayList<>(fileTable.getContent().keySet());
        fileTableListView.setItems(FXCollections.observableArrayList(list));
    }
    

    private void populateOutput(PrgState currentProgramState) {
        List<Value> output = currentProgramState.getOutputList();
        outputListView.setItems(FXCollections.observableList(output));
        outputListView.refresh();
    }

    private void populateSymbolTable(PrgState currentProgramState) {
        IDict<String, Value> symTable = currentProgramState.getSymbolTable();
        ArrayList<SymTableEntry> entries = new ArrayList<SymTableEntry>();
        for (Map.Entry<String, Value> entry: symTable.getContent().entrySet()) {
            entries.add(new SymTableEntry(entry.getKey(), entry.getValue()));
        }
        symbolTableView.setItems(FXCollections.observableArrayList(entries));
        symbolTableView.refresh();
    }

    private void populateExecutionStack(PrgState currentProgramState) {
        IStack<IStmt> stk = currentProgramState.getExeStack();
        executionStackListView.setItems(FXCollections.observableArrayList(stk.toList()));
        executionStackListView.refresh();
    }


private void populatePrgStateListView(List<PrgState> states) {
    List<Integer> stateIDs = states.stream().map(PrgState::getThreadId).collect(Collectors.toList());
    programStateListView.setItems(FXCollections.observableArrayList(stateIDs));
}

    @FXML
    void switchProgramState(MouseEvent event) {
        List<PrgState> states = controller.getRepository().getPrgList();
        int index = programStateListView.getSelectionModel().getSelectedIndex();
        PrgState program = states.get(index);
        populateExecutionStack(program);
        populateSymbolTable(program);
    }
}
