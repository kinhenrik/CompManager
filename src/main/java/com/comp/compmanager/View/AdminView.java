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

//HENRIK KINNUNEN´S KLASS

public class AdminView {
    private final ViewManager viewManager;
    private AdminDAO adminDAO = new AdminDAO();

    public AdminView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public AnchorPane getView() {
        AnchorPane layout = new AnchorPane();

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
        table.setItems(observableList);


        //TEXT FIELDS
        //name
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Name...");
        //surname
        TextField surnameTextField = new TextField();
        surnameTextField.setPromptText("Surname...");
        //address
        TextField addressTextField = new TextField();
        addressTextField.setPromptText("Address...");
        //address
        TextField zipcodeTextField = new TextField();
        zipcodeTextField.setPromptText("Zip-code...");
        //city
        TextField cityTextField = new TextField();
        cityTextField.setPromptText("City...");
        //country
        TextField countryTextField = new TextField();
        countryTextField.setPromptText("Country...");
        //email
        TextField emailTextField = new TextField();
        emailTextField.setPromptText("E-mail...");


        //BUTTON BAR
        ButtonBar buttonBar = new ButtonBar();
        //general dimensions
        buttonBar.setPadding(new Insets(8));
        //buttons
        Button deleteAdminBtn = new Button("Delete Selected");
        Button addAdminBtn = new Button("Add New Admin");
        Button editAdminBtn = new Button("Edit selected");
        //add buttons to bar
        buttonBar.getButtons().addAll(deleteAdminBtn, addAdminBtn, editAdminBtn);

        deleteAdminBtn.setOnAction(e -> {
            Admin selectedAdmin = table.getSelectionModel().getSelectedItem();
            if (selectedAdmin != null) {
                adminDAO.deleteAdmin(selectedAdmin); // Använder den justerade delete-metoden
                observableList.remove(selectedAdmin); // Uppdatera tabellen
                table.refresh(); // Uppdatera GUI:t
            }
        });

        addAdminBtn.setOnAction(e -> {
            if (!nameTextField.getText().isEmpty()) {
                Admin newAdmin = new Admin(nameTextField.getText(), surnameTextField.getText(), addressTextField.getText(), Integer.parseInt(zipcodeTextField.getText()), cityTextField.getText(), countryTextField.getText(), emailTextField.getText());
                adminDAO.addAdmin(newAdmin);
                observableList.add(newAdmin); // Lägg till spelaren i tabellen
                nameTextField.clear();
                surnameTextField.clear();
                addressTextField.clear();
                zipcodeTextField.clear();
                cityTextField.clear();
                countryTextField.clear();
                emailTextField.clear();
                table.refresh();

            } else {
                System.out.println("NAME FIELD CANT EMPTY");
            }

            table.refresh();
        });

        editAdminBtn.setOnAction(e -> {
            if (nameTextField.getText() != "") {
                table.getSelectionModel().getSelectedItem().setFirstname(nameTextField.getText());
                table.getSelectionModel().getSelectedItem().setLastname(surnameTextField.getText());
                table.getSelectionModel().getSelectedItem().setAddress(addressTextField.getText());
                table.getSelectionModel().getSelectedItem().setZipcode(Integer.parseInt(zipcodeTextField.getText()));
                table.getSelectionModel().getSelectedItem().setCity(cityTextField.getText());
                table.getSelectionModel().getSelectedItem().setCountry(countryTextField.getText());
                table.getSelectionModel().getSelectedItem().setEmail(emailTextField.getText());

                adminDAO.updateAdmin(table.getSelectionModel().getSelectedItem());

                //reset text fields after use
                nameTextField.setText("");
                surnameTextField.setText("");
                addressTextField.setText("");
                zipcodeTextField.setText("");
                cityTextField.setText("");
                countryTextField.setText("");
                emailTextField.setText("");

            } else {
                System.out.println("NAME FIELD CANT EMPTY");
            }
            table.refresh();
        });

        //DISABLAR KNAPPAR OCH TEXTFIELDS OM MAN INTE ÄR ADMIN
        if (!viewManager.isAdmin()) {
            nameTextField.setDisable(true);
            surnameTextField.setDisable(true);
            addressTextField.setDisable(true);
            zipcodeTextField.setDisable(true);
            cityTextField.setDisable(true);
            countryTextField.setDisable(true);
            emailTextField.setDisable(true);
            buttonBar.setDisable(true);
        }

        // Layout med VBox
        VBox vBox = new VBox(10, table, buttonBar, nameTextField, surnameTextField, addressTextField, zipcodeTextField, cityTextField, countryTextField, emailTextField);
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