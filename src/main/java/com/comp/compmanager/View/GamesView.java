package com.comp.compmanager.View;
import com.comp.compmanager.DAO.GamesDAO;
import com.comp.compmanager.entities.Games;
import com.comp.compmanager.entities.Teams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class GamesView {
    private final ViewManager viewManager;

    public GamesView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public AnchorPane getView() {
        AnchorPane layout = new AnchorPane();

        Label label = new Label("Games View");
        AnchorPane.setTopAnchor(label, 10.0);
        AnchorPane.setLeftAnchor(label, 10.0);
        layout.getChildren().add(label);

        // Skapa en TableView
        TableView<Games> table = new TableView<>();

        TableColumn<Games, String> id_col = new TableColumn<>("Game ID");
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Games, String> name_col = new TableColumn<>("Game name");
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//        TableColumn<Games, String> lastname_col = new TableColumn<>("Last name");
//        lastname_col.setCellValueFactory(new PropertyValueFactory<>("lastname"));
//
//        TableColumn<Games, String> address_col = new TableColumn<>("Address");
//        address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
//
//        TableColumn<Games, String> zip_col = new TableColumn<>("ZIP-code");
//        zip_col.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
//
//        TableColumn<Games, String> city_col = new TableColumn<>("City");
//        city_col.setCellValueFactory(new PropertyValueFactory<>("city"));
//
//        TableColumn<Games, String> country_col = new TableColumn<>("Country");
//        country_col.setCellValueFactory(new PropertyValueFactory<>("country"));
//
//        TableColumn<Games, String> email_col = new TableColumn<>("E-mail");
//        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));


        table.getColumns().addAll(id_col, name_col);

        //adds all col data to ObservableList for JavaFX
        GamesDAO gamesDAO = new GamesDAO();
        List<Games> games = gamesDAO.getAllGames();
        ObservableList<Games> observableList = FXCollections.observableArrayList(games);
        table.setItems(observableList);


//        //BUTTON BAR
//        ButtonBar buttonBar = new ButtonBar();
//        if (!viewManager.isAdmin()) {
//            buttonBar.setDisable(true);
//        }
//        //general dimensions
//        buttonBar.setPadding(new Insets(8));
//        //buttons
//        Button deleteGameBtn = new Button("Delete Game");
//        Button addGameBtn = new Button("Add Game");
//        //add buttons to bar
//        buttonBar.getButtons().addAll(deleteGameBtn, addGameBtn);
//        //add button functionality via Lambda expression
//        deleteGameBtn.setOnAction(e -> {
//             Games selectedGame = table.getSelectionModel().getSelectedItem();
//              if (selectedGame != null) {
//              gamesDAO.deleteGame(games.get(0));
//              table.getItems().remove(games.get(0));
//              } else {
////          Visa ett varningsmeddelande om inget lag är valt
//              System.out.println("No game selected");
//              }
//        });
//
//        // add/lägga till knapp för att lägga till nya Teams i tabellen och sen uppdatera tabellen samt en popupfönster
//        addGameBtn.setOnAction(e -> {
//            // Skapa en ny Stage för popup fönster
//            Stage popupStage = new Stage();
//            popupStage.setTitle("Add New Game");
//
//            // Skapa layout för popup
//            VBox popupLayout = new VBox(10);
//            popupLayout.setPadding(new Insets(15));
//
//            // Skapa input-fält för lagets namn
//            Label nameLabel = new Label("Game Name:");
//            TextField nameField = new TextField();
//
//            // Spara-knapp
//            Button saveButton = new Button("Save");
//            saveButton.setOnAction(event -> {
//                String gameName = nameField.getText();
//                if (!gameName.isEmpty()) {
//                    // Skapa ett nytt team-objekt och lägg till det i listan
//                    Games newGame = new Games();
//                    newGame.setName(gameName);
//                    GamesDAO.addGames(newGame); // Lägg till i databasen
//                    table.getItems().add(newGame); // Uppdatera tabellen
//                    popupStage.close(); // Stäng popupen
//                } else {
//                    // Om namnet är tomt, visa ett varningsmeddelande
//                    System.out.println("Game name can't be empty!");
////                Alert alert = new Alert(Alert.AlertType.WARNING, "Game name can't be empty!", ButtonType.OK);
////                    alert.showAndWait();
//                }
//            });

//            // Lägg till komponenter i layouten
//            popupLayout.getChildren().addAll(nameLabel, nameField, saveButton);
//
//            // Visa popup-rutan
//            Scene popupScene = new Scene(popupLayout, 300, 200);
//            popupStage.setScene(popupScene);
//            popupStage.show();
//        });


        // Layout med VBox
        VBox vBox = new VBox(10, table/*, buttonBar*/);
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
}