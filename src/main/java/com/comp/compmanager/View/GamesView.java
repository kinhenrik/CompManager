package com.comp.compmanager.View;
import com.comp.compmanager.DAO.GamesDAO;
import com.comp.compmanager.DAO.PlayerDAO;
import com.comp.compmanager.DAO.TeamManagerDAO;
import com.comp.compmanager.entities.Games;
import com.comp.compmanager.entities.Player;
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

//SIMON WESTERLUNDS KLASS

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

        table.getColumns().addAll(id_col, name_col);

        //adds all col data to ObservableList for JavaFX
        GamesDAO gamesDAO = new GamesDAO();
        List<Games> games = gamesDAO.getAllGames();
        ObservableList<Games> observableList = FXCollections.observableArrayList(games);
        table.setItems(observableList);

        //BUTTON BAR
        ButtonBar buttonBar = new ButtonBar();
        if (!viewManager.isAdmin()) {
            buttonBar.setDisable(true);
        }
        //general dimensions
        buttonBar.setPadding(new Insets(8));
        //buttons
        Button deleteGameBtn = new Button("Delete Game");
        Button addGameBtn = new Button("Add Game");
        Button editGameBtn = new Button("Edit Game");
        //add buttons to bar
        buttonBar.getButtons().addAll(deleteGameBtn, addGameBtn, editGameBtn);
        //add button functionality via Lambda expression
        deleteGameBtn.setOnAction(e -> {
            // Hämta det valda laget från tabellen
            Games selectedGame = table.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                // Ta bort laget från databasen
                GamesDAO.deleteGame(selectedGame);
                // Ta bort laget från tabellen
                table.getItems().remove(selectedGame);
                System.out.println("game deleted!");
            } else {
                // Visa ett varningsmeddelande om inget lag är valt
                System.out.println("No game selected");
            }
        });

        // add/lägga till knapp för att lägga till nya Teams i tabellen och sen uppdatera tabellen samt en popupfönster
        addGameBtn.setOnAction(e -> {
            // Skapa en ny Stage för popup fönster
            Stage popupStage = new Stage();
            popupStage.setTitle("Add New Game");

            // Skapa layout för popup
            VBox popupLayout = new VBox(10);
            popupLayout.setPadding(new Insets(15));

            // Skapa input-fält för lagets namn
            Label nameLabel = new Label("Game Name:");
            TextField nameField = new TextField();

            // Spara-knapp
            Button saveButton = new Button("Save");
            saveButton.setOnAction(event -> {
                String gameName = nameField.getText();
                if (!gameName.isEmpty()) {
                    // Skapa ett nytt team-objekt och lägg till det i listan
                    Games newGame = new Games();
                    newGame.setName(gameName);
                    GamesDAO.addGame(newGame); // Lägg till i databasen
                    table.getItems().add(newGame); // Uppdatera tabellen
                    popupStage.close(); // Stäng popupen
                } else {
                    // Om namnet är tomt, visa ett varningsmeddelande
                    System.out.println("Game name can't be empty!");
                }
            });
            // Lägg till komponenter i layouten
            popupLayout.getChildren().addAll(nameLabel, nameField, saveButton);

            // Visa popup-rutan
            Scene popupScene = new Scene(popupLayout, 300, 200);
            popupStage.setScene(popupScene);
            popupStage.show();
        });

        // Knapp för att redigera ett befintligt spel
        editGameBtn.setOnAction(e -> {
            // Hämta det valda spelet från tabellen
            Games selectedGame = table.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                // Skapa en ny Stage för popup fönster
                Stage popupStage = new Stage();
                popupStage.setTitle("Edit Game");

                // Layout för popup
                VBox popupLayout = new VBox(10);
                popupLayout.setPadding(new Insets(15));

                // Skapa fält för att redigera spel
                Label nameLabel = new Label("Game Name:");
                TextField nameField = new TextField(selectedGame.getName());

                Button saveButton = new Button("Save");
                saveButton.setOnAction(event -> {
                    String updatedName = nameField.getText();
                    if (!updatedName.isEmpty()) {
                        // Uppdatera spelets namn
                        selectedGame.setName(updatedName);
                        // Uppdatera spelet i databasen
                        GamesDAO.updateGame(selectedGame);
                        // Uppdatera tabellen
                        table.refresh();
                        System.out.println("Game updated!");
                        popupStage.close();
                    } else {
                        // Visa ett varningsmeddelande om fältet är tomt
                        System.out.println("Game name can't be empty!");
                    }
                });

                // Lägg till fält och knapp till popup-layout
                popupLayout.getChildren().addAll(nameLabel, nameField, saveButton);

                // Visa popup-fönstret
                Scene popupScene = new Scene(popupLayout, 300, 200);
                popupStage.setScene(popupScene);
                popupStage.show();
            } else {
                // Visa ett varningsmeddelande om inget spel är valt
                System.out.println("No game selected for edit!");

            }
        });

        // Layout
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
    public ObservableList gamesObservableList() {
        //Skapar en lista med alla games från databasen och gör om till en ObservableList så att den kan användas i en tabell eller dropdown-lista
        List<Games> game = GamesDAO.getAllGames();
        ObservableList<Games> gamesObservableList = FXCollections.observableArrayList(game);
        return gamesObservableList;
    }
}