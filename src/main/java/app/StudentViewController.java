package app;

import controller.AppController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.StudentModel;

import java.util.Arrays;

public class StudentViewController {
    @FXML private TableView<StudentModel> table;
    @FXML private TableColumn<StudentModel, Number> idColumn;
    @FXML private TableColumn<StudentModel, String> nameColumn;
    @FXML private TextField searchFiled;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private Label status;
    private AppController appController;

    public StudentViewController(){

    }
    private StudentViewController(final AppController appController){
        this.appController = appController;
    }

    public void setAppController(final AppController appController){
        this.appController = appController;
        refreshTable();
    }

    @FXML
    private void initialize(){
        idColumn.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()));
        nameColumn.setCellValueFactory(c ->  new SimpleStringProperty(c.getValue().getName()));

    }

    private void refreshTable(){
        table.getItems().setAll(appController.showAll());
        status.setText("Loaded "+ table.getItems().size()+" Students");
    }

    @FXML
    private void onAdd(){
        try{
            var s = appController.add(this.nameField.getText());
            nameField.clear();
            refreshTable();
            status.setText("Added: " + s.getName()+" ID: "+s.getId());
        }catch (IllegalArgumentException e){
            showError(e.getMessage());
        }

    }

    @FXML
    private void onSearch(){
        String q = searchFiled.getText();
        var nameList = appController.showByName(q);
        table.getItems().setAll(nameList);
        status.setText("Search result: "+ nameList.size());
    }

    @FXML
    private void onReset(){
        searchFiled.clear();
        refreshTable();
    }

    @FXML
    private void onShowById(){
        int id = Integer.parseInt(this.idField.getText().trim());
        var opt = appController.showById(id);
        if(opt.isPresent()){
            table.getItems().setAll(opt.get());
            status.setText("Id founded: "+id);
        }else{
            status.setText("Id: "+id+" Not found: ");
            showInfo("Id: "+id+" not found");
        }
    }



    @FXML
    private void onDelete(){
        var selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null){
            showInfo("Select a row first!");
            return;
        }
        if (appController.remove(selectedItem.getId())){
            refreshTable();
            status.setText("Deleted ID "+selectedItem.getId());
        }
    }

    private void showWarn(final String msg){
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }

    private void showError(final String msg){
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }

    private void showInfo(final String msg){
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

}
