package com.comp.compmanager;

import com.comp.compmanager.DAO.AdminDAO;
import com.comp.compmanager.entities.Admin;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        AdminDAO adminDAO = new AdminDAO();

        //JAVAFX

        TableView<Admin> table = new TableView<>();

        TableColumn<Admin, String> id_col = new TableColumn<>("ID");
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Admin, String> firstname_col = new TableColumn<>("First name");
        firstname_col.setCellValueFactory(new PropertyValueFactory<>("firstname"));

        TableColumn<Admin, String> lastname_col = new TableColumn<>("Last name");
        lastname_col.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        TableColumn<Admin, String> address_col = new TableColumn<>("Address");
        address_col.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Admin, String> zip_col = new TableColumn<>("ZIP-code");
        zip_col.setCellValueFactory(new PropertyValueFactory<>("zipcode"));

        TableColumn<Admin, String> city_col = new TableColumn<>("City");
        city_col.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<Admin, String> country_col = new TableColumn<>("Country");
        country_col.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<Admin, String> email_col = new TableColumn<>("E-mail");
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));


        table.getColumns().addAll(id_col, firstname_col, lastname_col, address_col, zip_col, city_col, country_col, email_col);

        //adds all col data to ObservableList for JavaFX
        List<Admin> admins = adminDAO.getAllAdmins();
        ObservableList<Admin> observableList = FXCollections.observableArrayList(admins);
        table.setItems(observableList);


        //BUTTON BAR
        ButtonBar buttonBar = new ButtonBar();
        //general dimensions
        buttonBar.setPadding(new Insets(8));
        //buttons
        Button deletePlayerBtn = new Button("Delete Player");
        Button addPlayerBtn = new Button("Add Player");
        //add buttons to bar
        buttonBar.getButtons().addAll(deletePlayerBtn, addPlayerBtn);
        //add button functionality via Lambda expression
        deletePlayerBtn.setOnAction(e -> {
            adminDAO.deleteAdmin(admins.get(0));
            table.getItems().remove(admins.get(0));
        });
        addPlayerBtn.setOnAction(e -> System.out.println("add someone"));

        //adds table to AnchorPane and makes it dynamically sized
        AnchorPane pane = new AnchorPane(table);
        pane.setTopAnchor(table, 0.0);
        pane.setBottomAnchor(table, 0.0);
        pane.setLeftAnchor(table, 0.0);
        pane.setRightAnchor(table, 0.0);
        pane.getChildren().add(buttonBar);

        Scene scene = new Scene(pane, 800, 300);
        stage.setTitle("Admins");
        stage.setScene(scene);
        stage.show();


    }


    public static void main(String[] args) {
        launch();
    }

}
