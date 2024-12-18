package com.comp.compmanager.View;

import com.comp.compmanager.DAO.MatchesDAO;
import com.comp.compmanager.DAO.TeamManagerDAO;
import com.comp.compmanager.entities.Matches;
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

        // Kolumn för Team & Winner
        TableColumn<Matches, String> winnerTeamColumn = new TableColumn<>("Team Winner");
        winnerTeamColumn.setCellValueFactory(new PropertyValueFactory<>("winnerTeam"));

        // Liknande kolumner för Player
        // Kolumner för Match ID
//        TableColumn<Matches, Integer> idPlayerColumn = new TableColumn<>("Match ID");
//        idPlayerColumn.setCellValueFactory(new PropertyValueFactory<>("matchId"));
//
//
//        TableColumn<Matches, String> datePlayerColumn = new TableColumn<>("Date");
//        datePlayerColumn.setCellValueFactory(new PropertyValueFactory<>("matchDate"));
//
//
//        TableColumn<Matches, String> player1Column = new TableColumn<>("Player 1");
//        player1Column.setCellValueFactory(new PropertyValueFactory<>("player1"));
//
//        TableColumn<Matches, String> player2Column = new TableColumn<>("Player 2");
//        player2Column.setCellValueFactory(new PropertyValueFactory<>("player2"));
//
//
//        TableColumn<Matches, String> typePlayerColumn = new TableColumn<>("Type");
//        typePlayerColumn.setCellValueFactory(new PropertyValueFactory<>("matchType"));
//
//        TableColumn<Matches, String> winnerPlayerColumn = new TableColumn<>("Player Winner");
//        winnerPlayerColumn.setCellValueFactory(new PropertyValueFactory<>("winnerPlayer"));

        // Lägg till kolumner i tabellen
        table.getColumns().addAll(idColumn, dateColumn, typeColumn,
                team1Column, team2Column, winnerTeamColumn
                /*,idPlayerColumn,datePlayerColumn,typePlayerColumn, player2Column,winnerPlayerColumn*/);

        // Lägg till data i tabellen// Omvandla lista till ObservableList för JavaFX
        MatchesDAO MatchesDAO = new MatchesDAO();
        List<Matches> matches = MatchesDAO.getAllMatches();
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
                MatchesDAO.deleteMatch(selectedMatch);
                // Ta bort laget från tabellen
                table.getItems().remove(selectedMatch);
                System.out.println("Match deleted!");
            } else {
                // Visa ett varningsmeddelande om inget lag är valt
                System.out.println("No team selected");
//              Alert alert = new Alert(Alert.AlertType.WARNING, "No team selected for deletion!", ButtonType.OK);
//              alert.showAndWait();
            }
        });

        // add/lägga till knapp för att lägga till nya natcher i tabellen och sen uppdatera tabellen samt en popupfönster
        addMatchBtn.setOnAction(e -> {
            Stage popupStage = new Stage();
            popupStage.setTitle("Add New Match");

            VBox popupLayout = new VBox(10);
            popupLayout.setPadding(new Insets(15));

            // Labels och TextFields för att lägga till en ny match
            Label typeLabel = new Label("Match Type:");
            TextField typeField = new TextField();

            Label dateLabel = new Label("Match Date (YYYY-MM-DD):");
            TextField dateField = new TextField();

            Label team1Label = new Label("Team 1 ID:");
            TextField team1Field = new TextField();

            Label team2Label = new Label("Team 2 ID:");
            TextField team2Field = new TextField();

            Label winnerLabel = new Label("Winner Team ID:");
            TextField winnerField = new TextField();

            Button saveButton = new Button("Add Match");
            saveButton.setOnAction(event -> {
                try {
                    if (!typeField.getText().isEmpty() && !dateField.getText().isEmpty()) {
                        Matches newMatch = new Matches();
                        newMatch.setMatchType(typeField.getText());
                        newMatch.setMatchDate(java.sql.Date.valueOf(dateField.getText()));

                        Teams team1 = TeamManagerDAO.getTeamByID(Integer.parseInt(team1Field.getText()));
                        Teams team2 = TeamManagerDAO.getTeamByID(Integer.parseInt(team2Field.getText()));
                        Teams winnerTeam = TeamManagerDAO.getTeamByID(Integer.parseInt(winnerField.getText()));

                        newMatch.setTeam1(team1);
                        newMatch.setTeam2(team2);
                        newMatch.setWinnerTeam(winnerTeam);

                        MatchesDAO.addMatch(newMatch);
                        table.getItems().add(newMatch); // Lägger till den nya matchen i tabellen
                        table.refresh();

                        popupStage.close();
                        System.out.println("Match added successfully!");
                    } else {
                        System.out.println("Fields can't be empty!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Error adding match. Check code");
                }
            });

            popupLayout.getChildren().addAll(
                    typeLabel, typeField,
                    dateLabel, dateField,
                    team1Label, team1Field,
                    team2Label, team2Field,
                    winnerLabel, winnerField,
                    saveButton
            );

            Scene popupScene = new Scene(popupLayout, 400, 400);
            popupStage.setScene(popupScene);
            popupStage.show();
        });

        // Edit knapp för att redigera befintliga matcher i tabellen och sen uppdatera tabellen samt en popupfönster
        editMatchBtn.setOnAction(e -> {
            Matches selectedMatch = table.getSelectionModel().getSelectedItem();

            if (selectedMatch != null) { // Säkerställ att en match är vald
                Stage popupStage = new Stage();
                popupStage.setTitle("Edit Match");

                VBox popupLayout = new VBox(10);
                popupLayout.setPadding(new Insets(15));

                // Labels och TextFields för att redigera matchens detaljer
                Label typeLabel = new Label("Match Type:");
                TextField typeField = new TextField(selectedMatch.getMatchType());

                Label dateLabel = new Label("Match Date (YYYY-MM-DD):");
                TextField dateField = new TextField(selectedMatch.getMatchDate().toString());

                Label team1Label = new Label("Team 1");
                TextField team1Field = new TextField(String.valueOf(selectedMatch.getTeam1().getId()));

                Label team2Label = new Label("Team 2:");
                TextField team2Field = new TextField(String.valueOf(selectedMatch.getTeam2().getId()));

                Label winnerLabel = new Label("Winner Team");
                TextField winnerField = new TextField(String.valueOf(selectedMatch.getWinnerTeam().getId()));

                Button saveButton = new Button("Save");
                saveButton.setOnAction(event -> {
                    try {
                        // Kontrollera att fälten inte är tomma
                        if (!typeField.getText().isEmpty() && !dateField.getText().isEmpty()) {
                            selectedMatch.setMatchType(typeField.getText());
                            selectedMatch.setMatchDate(java.sql.Date.valueOf(dateField.getText()));

                            // Hämta spelare med hjälp av ID
                            TeamManagerDAO TeamManagerDAO = new TeamManagerDAO();
                            Teams team1 = TeamManagerDAO.getTeamByID(Integer.parseInt(team1Field.getText()));
                            Teams team2 = TeamManagerDAO.getTeamByID(Integer.parseInt(team2Field.getText()));
                            Teams winnerTeam = TeamManagerDAO.getTeamByID(Integer.parseInt(winnerField.getText()));

                            selectedMatch.setTeam1(team1);
                            selectedMatch.setTeam2(team2);
                            selectedMatch.setWinnerTeam(winnerTeam);

                            // Uppdatera matchen i databasen
                            com.comp.compmanager.DAO.MatchesDAO.updateMatch(selectedMatch);
                            table.refresh();

                            System.out.println("Match updated successfully!");
                            popupStage.close();
                        } else {
                            System.out.println("Fields can't be empty!");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("Error updating match. check code ");
                    }
                });

                // Lägga till alla komponenter i layouten
                popupLayout.getChildren().addAll(typeLabel, typeField, dateLabel, dateField,
                        team1Label, team1Field, team2Label, team2Field,
                        winnerLabel, winnerField, saveButton
                );

                Scene popupScene = new Scene(popupLayout, 400, 400);
                popupStage.setScene(popupScene);
                popupStage.show();
            } else {
                System.out.println("No match selected!");
            }
        });

//        editMatchBtn.setOnAction(e -> {
//            Matches selectedMatch = table.getSelectionModel().getSelectedItem();
//
//            Stage popupStage = new Stage();
//            popupStage.setTitle("Edit Match");
//
//            VBox popupLayout = new VBox(10);
//            popupLayout.setPadding(new Insets(15));
//
//            Label nameLabel = new Label("Change new match:");
//            TextField nameField = new TextField();
//
//            Button saveButton = new Button("Save");
//            saveButton.setOnAction(event -> {
//                String UpdateMatch = nameField.getText();
//                if (!UpdateMatch.isEmpty()) {
//                    selectedMatch.setMatchType(UpdateMatch);
//                    MatchesDAO.updateMatch(selectedMatch);
//                    table.refresh();
//                    popupStage.close();
//                    System.out.println("Match updated");
//                }else {
//                    // Om namnet är tomt, visa ett varningsmeddelande
//                    System.out.println("Textfield can't be empty!");
////                Alert alert = new Alert(Alert.AlertType.WARNING, "Textfield can't be empty!", ButtonType.OK);
////                alert.showAndWait();
//                }
//            });
//            popupLayout.getChildren().addAll(nameLabel, nameField, saveButton);
//
//            Scene popupScene = new Scene(popupLayout, 300, 200);
//            popupStage.setScene(popupScene);
//            popupStage.show();
//
//        });

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
