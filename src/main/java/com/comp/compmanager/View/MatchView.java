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

        TableColumn<Matches, Integer> team1ScoreColumn = new TableColumn<>("Team 1 Score");
        team1ScoreColumn.setCellValueFactory(new PropertyValueFactory<>("team1Score"));

        TableColumn<Matches, Integer> team2ScoreColumn = new TableColumn<>("Team 2 Score");
        team2ScoreColumn.setCellValueFactory(new PropertyValueFactory<>("team2Score"));

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
                team1Column, team2Column,team1ScoreColumn,team2ScoreColumn, winnerTeamColumn
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
        Button registerResultBtn = new Button("Register Results");
        //add buttons to bar
        buttonBar.getButtons().addAll(deleteMatchBtn, addMatchBtn, editMatchBtn, registerResultBtn);
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
                System.out.println("No mactch selected");
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
//            TextField typeField = new TextField();
            ComboBox<String> matchTypeComboBox = new ComboBox<>();
            matchTypeComboBox.getItems().addAll("T vs T", "P vs P");
            matchTypeComboBox.setValue("T vs T");

            Label dateLabel = new Label("Match Date (YYYY-MM-DD):");
            TextField dateField = new TextField();

            Label team1Label = new Label("Team 1 ID:");
            TextField team1Field = new TextField();

            Label team2Label = new Label("Team 2 ID:");
            TextField team2Field = new TextField();

//            Label winnerLabel = new Label("Winner Team ID:");
//            TextField winnerField = new TextField();

            Button saveButton = new Button("Add Match");
            saveButton.setOnAction(event -> {
                try {
                    if (!matchTypeComboBox.getValue().isEmpty() && !dateField.getText().isEmpty()) {
                        Matches newMatch = new Matches();
                        newMatch.setMatchType(matchTypeComboBox.getValue());
                        newMatch.setMatchDate(java.sql.Date.valueOf(dateField.getText()));

                        Teams team1 = TeamManagerDAO.getTeamByID(Integer.parseInt(team1Field.getText()));
                        Teams team2 = TeamManagerDAO.getTeamByID(Integer.parseInt(team2Field.getText()));
//                        Teams winnerTeam = TeamManagerDAO.getTeamByID(Integer.parseInt(winnerField.getText()));

                        newMatch.setTeam1(team1);
                        newMatch.setTeam2(team2);
//                        newMatch.setWinnerTeam(winnerTeam);

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

            popupLayout.getChildren().addAll(typeLabel, matchTypeComboBox, dateLabel, dateField,
                    team1Label, team1Field, team2Label, team2Field,
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

        registerResultBtn.setOnAction(e -> {
            // Hämta vald match från tabellen
            Matches selectedMatch = table.getSelectionModel().getSelectedItem();

            if (selectedMatch != null) {
                // Skapa ett popup-fönster för att registrera resultat
                Stage popupStage = new Stage();
                popupStage.setTitle("Register Results");

                VBox popupLayout = new VBox(10);
                popupLayout.setPadding(new Insets(15));

                // Labels och TextFields för att mata in resultaten
                Label team1Label = new Label("Result for " + selectedMatch.getTeam1().getName() + ":");
                TextField team1ScoreField = new TextField();

                Label team2Label = new Label("Result for " + selectedMatch.getTeam2().getName() + ":");
                TextField team2ScoreField = new TextField();

                Button saveButton = new Button("Save Results");
                saveButton.setOnAction(event -> {
                    try {
                        // Kontrollera så att fälten inte är tomma
                        if (!team1ScoreField.getText().isEmpty() && !team2ScoreField.getText().isEmpty()) {
                            int team1Score = Integer.parseInt(team1ScoreField.getText());
                            int team2Score = Integer.parseInt(team2ScoreField.getText());

                            // Uppdatera matchens resultat
                            selectedMatch.setTeam1Score(team1Score);
                            selectedMatch.setTeam2Score(team2Score);

                            // visar vinnaren baserat på resultaten
                            if (team1Score > team2Score) {
                                selectedMatch.setWinnerTeam(selectedMatch.getTeam1());
                            } else if (team2Score > team1Score) {
                                selectedMatch.setWinnerTeam(selectedMatch.getTeam2());
                            } else {
                                selectedMatch.setWinnerTeam(null); // Oavgjort
                                System.out.println("Equals");
                            }

                            // Uppdatera matchen i databasen
                            MatchesDAO.updateMatch(selectedMatch);

                            // Uppdatera tabellen
                            table.refresh();

                            System.out.println("Results registered successfully!");
                            System.out.println("Result : " + selectedMatch.getTeam1().getName() + " " + selectedMatch.getTeam1Score() + " - " + selectedMatch.getTeam2Score() + " " + selectedMatch.getTeam2().getName());
                            System.out.println("Congratulations for the winner team! : " + selectedMatch.getWinnerTeam());
                            popupStage.close();
                        } else {
                            System.out.println("Scores can't be empty!");
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid input! Scores must be numbers.");
                    }
                });

                // Lägg till komponenter till layouten
                popupLayout.getChildren().addAll(team1Label, team1ScoreField, team2Label, team2ScoreField, saveButton);

                Scene popupScene = new Scene(popupLayout, 300, 200);
                popupStage.setScene(popupScene);
                popupStage.show();
            } else {
                System.out.println("No match selected!");
            }
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
