package com.comp.compmanager.View;

import com.comp.compmanager.DAO.AdminDAO;
import com.comp.compmanager.entities.Admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class AdminView {
    private final ViewManager viewManager;
    private AdminDAO adminDAO = new AdminDAO();

    public AdminView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }


    public AnchorPane getView() {
        AnchorPane layout = new AnchorPane();

        Label label = new Label("Admin View");
        AnchorPane.setTopAnchor(label, 10.0);
        AnchorPane.setLeftAnchor(label, 10.0);
        layout.getChildren().add(label);

        // Skapa en TableView
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

        ObservableList<Admin> observableList = adminList();
        table.setItems(observableList);


        //BUTTON BAR
        ButtonBar buttonBar = new ButtonBar();
        //general dimensions
        buttonBar.setPadding(new Insets(8));
        //buttons
        Button deletePlayerBtn = new Button("Delete Admin");
        Button addPlayerBtn = new Button("Add Admin");
        //add buttons to bar
        buttonBar.getButtons().addAll(deletePlayerBtn, addPlayerBtn);
        //add button functionality via Lambda expression
        deletePlayerBtn.setOnAction(e -> {
            adminDAO.deleteAdmin(observableList.get(0));
            table.getItems().remove(observableList.get(0));
        });
        addPlayerBtn.setOnAction(e -> System.out.println("add someone"));

        //DISABLAR KNAPPAR OCH TEXTFIELDS OM MAN INTE ÄR ADMIN
        if (!viewManager.isAdmin()) {
            buttonBar.setDisable(true);
        }

        // Layout med VBox
        VBox vBox = new VBox(10, table, buttonBar);
        vBox.setPadding(new Insets(10));
        vBox.setPrefWidth(820);
        vBox.setPrefHeight(600);
        AnchorPane.setTopAnchor(vBox, 30.0);
        AnchorPane.setLeftAnchor(vBox, 10.0);
        AnchorPane.setRightAnchor(vBox, 10.0);
        AnchorPane.setBottomAnchor(vBox, 10.0);

        layout.getChildren().add(vBox);

        return layout;
    }

    public ObservableList adminList() {
        //Skapar en lista med alla admins från databasen och gör om till en ObservableList så att den kan användas i en tabell eller dropdown-lista
        List<Admin> admins = adminDAO.getAllAdmins();
        ObservableList<Admin> observableList = FXCollections.observableArrayList(admins);
        return observableList;
    }

}


