package com.comp.compmanager.View;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginView{

    private final ViewManager viewManager;
    private Stage stage;
    private ComboBox dropdown;

    public LoginView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void createLoginWindow() {
        HBox loginDropdown = createDropdown();
        loginDropdown.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(loginDropdown, 0.0);
        AnchorPane.setRightAnchor(loginDropdown, 0.0);
        AnchorPane.setTopAnchor(loginDropdown, 50.0);

        HBox loginButtons = createButtons();
        loginButtons.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(loginButtons, 0.0);
        AnchorPane.setRightAnchor(loginButtons, 0.0);
        AnchorPane.setTopAnchor(loginButtons, 100.0);

        AnchorPane root = new AnchorPane(loginDropdown, loginButtons);
        stage = new Stage();
        stage.setTitle("Login");
        stage.initModality(Modality.APPLICATION_MODAL); // Gör så att andra fönstret inte går att klicka
        stage.setScene(new Scene(root, 400, 180));
        stage.isAlwaysOnTop();
        stage.showAndWait(); // Gör så att fönstret är öppet tills den blir tillsagd att stängas
    }

    private HBox createButtons() {
        Button loginButton = new Button("Login");
        Button cancelButton = new Button("Cancel");

        loginButton.setPrefWidth(80);
        cancelButton.setPrefWidth(80);

        loginButton.setOnAction(event -> {
            // Gör så att dropdown måste ha ett value innan man trycker login
            if (dropdown.getValue() != null) {
                viewManager.setAdmin(true);
                stage.close();
            }
        });

        cancelButton.setOnAction(event -> {
            viewManager.setAdmin(false);
            stage.close();
        });

        HBox loginBox = new HBox(10);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.getChildren().addAll(loginButton, cancelButton);

        return loginBox;
    }

    public HBox createDropdown() {
        // Skapar dropdown list med data från admin-listan i databasen (måste vara en observableList)
        ObservableList<String> observableList = new AdminView(viewManager).adminList();
        dropdown = new ComboBox(observableList);
        dropdown.setPromptText("Select admin...");
        dropdown.setPrefWidth(170);

        HBox dropdownBox = new HBox();
        dropdownBox.setAlignment(Pos.CENTER);
        dropdownBox.getChildren().addAll(dropdown);

        return dropdownBox;
    }

    //Getter för valet man gör i dropdown-listan så att den kan hämtas i ViewManager och visa vem man är inloggad som
    public Object getDropdownValue() {
        return dropdown.getValue();
    }

}
