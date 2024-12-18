package com.comp.compmanager.View;

import com.comp.compmanager.DAO.GameDAO;
import com.comp.compmanager.entities.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class GameView {

    private final ViewManager viewManager;


    public GameView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public AnchorPane getView() {
        AnchorPane layout = new AnchorPane();

        Label label = new Label("Game View");
        AnchorPane.setTopAnchor(label, 10.0);
        AnchorPane.setLeftAnchor(label, 10.0);
        layout.getChildren().add(label);


        //TABLE
        TableView<Game> table = new TableView();

        //"ID" col getting id data
        TableColumn<Game, String> id_column = new TableColumn<>("ID");
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));

        //"Name" col getting name data
        TableColumn<Game, String> name_column = new TableColumn<>("Name");
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));


        //combine all cols
        table.getColumns().addAll(id_column, name_column);

        //adds all col data to ObservableList for JavaFX
        GameDAO gameDAO = new GameDAO();
        List<Game> game = gameDAO.getAllGames();
        ObservableList<Game> observableList = FXCollections.observableArrayList(game);
        table.setItems(observableList);

        //TEXT FIELDS
        //name
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Name...");



        //BUTTON BAR
        ButtonBar buttonBar = new ButtonBar();
        //general dimensions
        buttonBar.setPadding(new Insets(8));
        //buttons
        Button deleteGameBtn = new Button("Delete Selected");
        Button addGameBtn = new Button("Add New Player");
        Button editGameBtn = new Button("Edit selected");

        //add buttons to bar
        buttonBar.getButtons().addAll(deleteGameBtn, addGameBtn, editGameBtn);

        //add button functionality via Lambda expression
        deleteGameBtn.setOnAction(e -> {  gameDAO.deleteGame(table.getSelectionModel().getSelectedItem());
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        });

        addGameBtn.setOnAction(e -> {
            if (nameTextField.getText() != "" ) {

                gameDAO.addGame(new Game(nameTextField.getText()));
                table.getItems().clear();
                table.getItems().addAll(gameDAO.getAllGames());

                //reset text fields after use
                nameTextField.setText("");
            }
            else {
                System.out.println("NAME FIELD CANT EMPTY");
            }

            table.refresh();
        });

        editGameBtn.setOnAction(e -> {
            if (nameTextField.getText() != "") {



                table.getSelectionModel().getSelectedItem().setName(nameTextField.getText());

                gameDAO.updateGame(table.getSelectionModel().getSelectedItem());

                //reset text fields after use
                nameTextField.setText("");

            }
            else {
                System.out.println("NAME FIELD CANT EMPTY");
            }

            table.refresh();

        });

        //DISABLAR KNAPPAR OCH TEXTFIELDS OM MAN INTE ÄR ADMIN
        if (!viewManager.isAdmin()) {
            nameTextField.setDisable(true);
            buttonBar.setDisable(true);
        }


        VBox vBox = new VBox(10,table, nameTextField, buttonBar);
        vBox.setPadding(new Insets(10));
        vBox.setPrefWidth(820);
        vBox.setPrefHeight(400);
        AnchorPane.setTopAnchor(vBox, 30.0);
        AnchorPane.setLeftAnchor(vBox, 10.0);
        AnchorPane.setRightAnchor(vBox, 10.0);
        AnchorPane.setBottomAnchor(vBox, 10.0);

        layout.getChildren().add(vBox);

        return layout;



    }


}

