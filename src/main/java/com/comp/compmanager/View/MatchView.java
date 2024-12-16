package com.comp.compmanager.View;

import com.comp.compmanager.DAO.MatchesDAO;
import com.comp.compmanager.entities.Matches;
import javafx.beans.property.SimpleStringProperty;
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

public class MatchView {
    private final ViewManager viewManager;

    public MatchView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public AnchorPane getView() {
        AnchorPane layout = new AnchorPane();

        Label label = new Label("Match View");
        AnchorPane.setTopAnchor(label, 10.0);
        AnchorPane.setLeftAnchor(label, 10.0);
        layout.getChildren().add(label);


        TableView<Matches> table = new TableView<>();

        // Kolumner för Match ID
        TableColumn<Matches, Integer> idColumn = new TableColumn<>("Match ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("matchId"));

        // Kolumn för Match Date
        TableColumn<Matches, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("matchDate"));

        // Kolumn för Match Type
        TableColumn<Matches, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("matchType"));

        // Kolumner för Teams
        TableColumn<Matches, String> team1Column = new TableColumn<>("Team 1");
        team1Column.setCellValueFactory(new PropertyValueFactory<>("team1"));

        TableColumn<Matches, String> team2Column = new TableColumn<>("Team 2");
        team2Column.setCellValueFactory(new PropertyValueFactory<>("team2"));

//        TableColumn<Matches, String> player1Column = new TableColumn<>("Player 1");
//        player1Column.setCellValueFactory(new PropertyValueFactory<>("player1"));
//
//        TableColumn<Matches, String> player2Column = new TableColumn<>("Player 2");
//        player2Column.setCellValueFactory(new PropertyValueFactory<>("player2"));

        // Kolumn för Team Winner & Player Winner
        TableColumn<Matches, String> winnerTeamColumn = new TableColumn<>("Team Winner");
        winnerTeamColumn.setCellValueFactory(new PropertyValueFactory<>("winnerTeam"));

//        TableColumn<Matches, String> winnerPlayerColumn = new TableColumn<>("Player Winner");
//        winnerPlayerColumn.setCellValueFactory(new PropertyValueFactory<>("winnerPlayer"));

        // Lägg till kolumner i tabellen
        table.getColumns().addAll(idColumn, dateColumn, typeColumn,
                team1Column, team2Column,/* player1Column, player2Column,*/
                winnerTeamColumn/*, winnerPlayerColumn*/);

        // Lägg till data i tabellen// Omvandla lista till ObservableList för JavaFX
        MatchesDAO matchesDAO = new MatchesDAO();
        List<Matches> matches = matchesDAO.getAllMatches();
        ObservableList<Matches> observableMatches = FXCollections.observableArrayList(matches);
        table.setItems(observableMatches);


        //BUTTON BAR
        ButtonBar buttonBar = new ButtonBar();
        if (!viewManager.isAdmin()) {
            buttonBar.setDisable(true);
        }
        //general dimensions
        buttonBar.setPadding(new Insets(10));
        //buttons
        Button deleteMatchBtn = new Button("Delete Match");
        Button addMatchBtn = new Button("Add Match");
        Button editMatchBtn = new Button("Edit Match");
        //add buttons to bar
        buttonBar.getButtons().addAll(deleteMatchBtn, addMatchBtn, editMatchBtn);
        //delete knapp för att ta bort lag från tabellen
        deleteMatchBtn.setOnAction(e -> {
            // Hämta det valda laget från tabellen
            Matches selectedMatch = table.getSelectionModel().getSelectedItem();
            if (selectedMatch != null) {
                // Ta bort laget från databasen
                matchesDAO.deleteMatch(selectedMatch);
                // Ta bort laget från tabellen
                table.getItems().remove(selectedMatch);
                System.out.println("Team deleted!");
            } else {
                // Visa ett varningsmeddelande om inget lag är valt
                System.out.println("No team selected");
//              Alert alert = new Alert(Alert.AlertType.WARNING, "No team selected for deletion!", ButtonType.OK);
//              alert.showAndWait();
            }
        });

        // add/lägga till knapp för att lägga till nya natcher i tabellen och sen uppdatera tabellen samt en popupfönster
        addMatchBtn.setOnAction(e -> {
            // Skapa en ny Stage för popup fönster
            Stage popupStage = new Stage();
            popupStage.setTitle("Add New Match");

            // Skapa layout för popup
            VBox popupLayout = new VBox(10);
            popupLayout.setPadding(new Insets(15));

            // Skapa input-fält för lagets namn
            Label nameLabel = new Label("Match Name:");
            TextField nameField = new TextField();

            // Spara-knapp
            Button saveButton = new Button("Save");
            saveButton.setOnAction(event -> {
                String matchName = nameField.getText();
                if (!matchName.isEmpty()) {
                    // Skapa ett nytt team objekt och lägg till det i listan
                    Matches newMatch = new Matches();
                    newMatch.setMatchType(matchName);
                    // Lägg till i databasen
                    matchesDAO.addMatch(newMatch);
                    // Uppdatera tabellen
                    table.getItems().add(newMatch);
                    // Stäng popupen fönstret
                    popupStage.close();
                    System.out.println("New team added");
                } else {
                    // Om namnet är tomt, visa ett varningsmeddelande
                    System.out.println("Team name can't be empty!");
//                Alert alert = new Alert(Alert.AlertType.WARNING, "Team name can't be empty!", ButtonType.OK);
//                    alert.showAndWait();
                }
            });

            // Lägg till komponenter i layouten
            popupLayout.getChildren().addAll(nameLabel, nameField, saveButton);

            // Visa popup-rutan
            Scene popupScene = new Scene(popupLayout, 300, 200);
            popupStage.setScene(popupScene);
            popupStage.show();
        });

        // Edit knapp för att redigera befintliga matcher i tabellen och sen uppdatera tabellen samt en popupfönster
        editMatchBtn.setOnAction(e -> {
            Matches selectedMatch = table.getSelectionModel().getSelectedItem();

            Stage popupStage = new Stage();
            popupStage.setTitle("Edit Team");

            VBox popupLayout = new VBox(10);
            popupLayout.setPadding(new Insets(15));

            Label nameLabel = new Label("Change new match:");
            TextField nameField = new TextField();

            Button saveButton = new Button("Save");
            saveButton.setOnAction(event -> {
                String UpdateMatch = nameField.getText();
                if (!UpdateMatch.isEmpty()) {
                    selectedMatch.setMatchType(UpdateMatch);
                    MatchesDAO.updateMatch(selectedMatch);
                    table.refresh();
                    popupStage.close();
                    System.out.println("Match updated");
                }else {
                    // Om namnet är tomt, visa ett varningsmeddelande
                    System.out.println("Textfield can't be empty!");
//                Alert alert = new Alert(Alert.AlertType.WARNING, "Textfield can't be empty!", ButtonType.OK);
//                alert.showAndWait();
                }
            });
            popupLayout.getChildren().addAll(nameLabel, nameField, saveButton);

            Scene popupScene = new Scene(popupLayout, 300, 200);
            popupStage.setScene(popupScene);
            popupStage.show();

        });

        // Layout med VBox
        VBox vBox = new VBox(10, table, buttonBar);
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
