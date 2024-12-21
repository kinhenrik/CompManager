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

public class FilterView{

    private final ViewManager viewManager;
    private Stage stage;

    public FilterView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void createFilterWindow() {
        HBox filterButtons = createButtons();
        filterButtons.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(filterButtons, 0.0);
        AnchorPane.setRightAnchor(filterButtons, 0.0);
        AnchorPane.setTopAnchor(filterButtons, 100.0);

        AnchorPane root = new AnchorPane(filterButtons);
        stage = new Stage();
        stage.setTitle("Filter");
        stage.initModality(Modality.APPLICATION_MODAL); // Gör så att andra fönstret inte går att klicka
        stage.setScene(new Scene(root, 400, 180));
        stage.isAlwaysOnTop();
        stage.showAndWait(); // Gör så att fönstret är öppet tills den blir tillsagd att stängas
    }

    private HBox createButtons() {
        Button filterButton = new Button("Filter");
        Button cancelButton = new Button("Cancel");

        filterButton.setPrefWidth(80);
        cancelButton.setPrefWidth(80);

        filterButton.setOnAction(event -> {
            //if (mainDropdown.getValue() != null && secondaryDropdown.getValue() != null) {}
        });

        cancelButton.setOnAction(event -> {
            stage.close();
        });

        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(filterButton, cancelButton);
        return filterBox;
    }

}