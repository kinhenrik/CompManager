package com.comp.compmanager.View;

import com.comp.compmanager.DAO.MatchesDAO;
import com.comp.compmanager.DAO.PlayerDAO;
import com.comp.compmanager.DAO.TeamManagerDAO;
import com.comp.compmanager.entities.Matches;
import com.comp.compmanager.entities.Player;
import com.comp.compmanager.entities.Teams;
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

        // Kolumn för Game i tabellen
        // Kolumn för Game Name
        TableColumn<Matches, String> gameColumn = new TableColumn<>("Game");
//        gameColumn.setCellValueFactory(new PropertyValueFactory<>("matchGame"));
        gameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGame().getName()));

        // Kolumner för Teams
        TableColumn<Matches, String> team1Column = new TableColumn<>("Team 1");
        team1Column.setCellValueFactory(new PropertyValueFactory<>("team1"));

        TableColumn<Matches, Integer> team1ScoreColumn = new TableColumn<>("Team 1 Score");
        team1ScoreColumn.setCellValueFactory(new PropertyValueFactory<>("team1Score"));

        TableColumn<Matches, Integer> team2ScoreColumn = new TableColumn<>("Team 2 Score");
        team2ScoreColumn.setCellValueFactory(new PropertyValueFactory<>("team2Score"));

        TableColumn<Matches, String> team2Column = new TableColumn<>("Team 2");
        team2Column.setCellValueFactory(new PropertyValueFactory<>("team2"));

        // Kolumn för Team & Winner
        TableColumn<Matches, String> winnerTeamColumn = new TableColumn<>("Team Winner");
        winnerTeamColumn.setCellValueFactory(new PropertyValueFactory<>("winnerTeam"));

        // Liknande kolumner för Player

        TableColumn<Matches, String> player1Column = new TableColumn<>("Player 1");
        player1Column.setCellValueFactory(new PropertyValueFactory<>("player1"));

        TableColumn<Matches, Integer> player1ScoreColumn = new TableColumn<>("Player 1 Score");
        player1ScoreColumn.setCellValueFactory(new PropertyValueFactory<>("player1Score"));

        TableColumn<Matches, Integer> player2ScoreColumn = new TableColumn<>("Player 2 Score");
        player2ScoreColumn.setCellValueFactory(new PropertyValueFactory<>("player2Score"));

        TableColumn<Matches, String> player2Column = new TableColumn<>("Player 2");
        player2Column.setCellValueFactory(new PropertyValueFactory<>("player2"));

        TableColumn<Matches, String> winnerPlayerColumn = new TableColumn<>("Player Winner");
        winnerPlayerColumn.setCellValueFactory(new PropertyValueFactory<>("winnerPlayer"));

        // Lägg till kolumner i tabellen
        table.getColumns().addAll(idColumn, dateColumn, typeColumn, gameColumn,
                team1Column,team1ScoreColumn,team2ScoreColumn, team2Column, winnerTeamColumn
               ,player1Column,player1ScoreColumn,player2ScoreColumn, player2Column,
                winnerPlayerColumn);

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

            Label player1Label = new Label("Player 1 ID:");
            TextField player1Field = new TextField();
            Label player2Label = new Label("Player 2 ID:");
            TextField player2Field = new TextField();

            //man ser inte player labels och fields tills man väljer P vs P annars visas T vs T som standard
            player1Label.setVisible(false);
            player1Field.setVisible(false);
            player2Label.setVisible(false);
            player2Field.setVisible(false);
            //här är funtion för ändring av visual

            matchTypeComboBox.setOnAction(event -> {
                boolean isTeamMatch = matchTypeComboBox.getValue().equals("T vs T");
                team1Label.setVisible(isTeamMatch);
                team1Field.setVisible(isTeamMatch);
                team2Label.setVisible(isTeamMatch);
                team2Field.setVisible(isTeamMatch);

                player1Label.setVisible(!isTeamMatch);
                player1Field.setVisible(!isTeamMatch);
                player2Label.setVisible(!isTeamMatch);
                player2Field.setVisible(!isTeamMatch);

            });
            Button saveButton = new Button("Add Match");
            saveButton.setOnAction(event -> {
                Matches selectedMatch = new Matches();
                try {
                    String matchType = matchTypeComboBox.getValue();
                    if (!matchTypeComboBox.getValue().isEmpty() && !dateField.getText().isEmpty()) {
                        selectedMatch.setMatchType(matchTypeComboBox.getValue());
                        selectedMatch.setMatchDate(java.sql.Date.valueOf(dateField.getText()));

                        //hämtar teams via ID
                        if (matchType.equals("T vs T")) {
                        Teams team1 = TeamManagerDAO.getTeamByID(Integer.parseInt(team1Field.getText()));
                        Teams team2 = TeamManagerDAO.getTeamByID(Integer.parseInt(team2Field.getText()));

                        selectedMatch.setTeam1(team1);
                        selectedMatch.setTeam2(team2);

                        } else if (matchType.equals("P vs P")) {
                            Player player1 = PlayerDAO.getPlayerByID(Integer.parseInt(player1Field.getText()));
                            Player player2 = PlayerDAO.getPlayerByID(Integer.parseInt(player2Field.getText()));
                            selectedMatch.setPlayer1(player1);
                            selectedMatch.setPlayer2(player2);
                        }

                        MatchesDAO.addMatch(selectedMatch);
                        table.getItems().addAll(selectedMatch);// Lägger till den nya matchen i tabellen
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
                    player1Label,player1Field,player2Label,player2Field, saveButton);

            Scene popupScene = new Scene(popupLayout, 400, 400);
            popupStage.setScene(popupScene);
            popupStage.show();
        });

        // Edit knapp för att redigera befintliga matcher i tabellen och sen uppdatera tabellen samt en popupfönster
        // finns två olika funktiner för edit match med winner eller utan winner i layouten
        editMatchBtn.setOnAction(e -> {
            Matches selectedMatch = table.getSelectionModel().getSelectedItem();

            if (selectedMatch != null) { // Kontrollera att en match är vald
                Stage popupStage = new Stage();
                popupStage.setTitle("Edit Match");

                VBox popupLayout = new VBox(10);
                popupLayout.setPadding(new Insets(15));

                TextField typeField = new TextField(selectedMatch.getMatchType()); // matchtyp

                Label dateLabel = new Label("Match Date (YYYY-MM-DD):");
                TextField dateField = new TextField(selectedMatch.getMatchDate().toString());

                //skapar en egen layout för edit team och den innehåll
                VBox teamMatchLayout = new VBox(10);
                Label team1Label = new Label("Team 1 ID:");
                TextField team1Field = new TextField();
                Label team2Label = new Label("Team 2 ID:");
                TextField team2Field = new TextField();
//                Label winnerTeamLabel = new Label("Winner Team ID:");
//                TextField winnerTeamField = new TextField();

                //skapar en egen layout för edit player och dens innehåll
                VBox playerMatchLayout = new VBox(10);
                Label player1Label = new Label("Player 1 ID:");
                TextField player1Field = new TextField();
                Label player2Label = new Label("Player 2 ID:");
                TextField player2Field = new TextField();
//                Label winnerPlayerLabel = new Label("Winner Player ID:");
//                TextField winnerPlayerField = new TextField();

                // Om matchtyp är T vs T visas endast team layout annars visas player layout
                boolean isTeamMatch = "T vs T".equals(selectedMatch.getMatchType());
                team1Label.setVisible(isTeamMatch);
                team1Field.setVisible(isTeamMatch);
                team2Label.setVisible(isTeamMatch);
                team2Field.setVisible(isTeamMatch);
//                winnerTeamLabel.setVisible(isTeamMatch);
//                winnerTeamField.setVisible(isTeamMatch);

                player1Label.setVisible(!isTeamMatch);
                player1Field.setVisible(!isTeamMatch);
                player2Label.setVisible(!isTeamMatch);
                player2Field.setVisible(!isTeamMatch);
//                winnerPlayerLabel.setVisible(!isTeamMatch);
//                winnerPlayerField.setVisible(!isTeamMatch);

                if (isTeamMatch) {
                    team1Field.setText(String.valueOf(selectedMatch.getTeam1().getId()));
                    team2Field.setText(String.valueOf(selectedMatch.getTeam2().getId()));
//                    winnerTeamField.setText(selectedMatch.getWinnerTeam() != null ? String.valueOf(selectedMatch.getWinnerTeam().getId()) : "");
                } else {
                    player1Field.setText(String.valueOf(selectedMatch.getPlayer1().getId()));
                    player2Field.setText(String.valueOf(selectedMatch.getPlayer2().getId()));
//                    winnerPlayerField.setText(selectedMatch.getWinnerPlayer() != null ? String.valueOf(selectedMatch.getWinnerPlayer().getId()) : "");

                }

                Button saveButton = new Button("Save");
                saveButton.setOnAction(event -> {
                    try {
                        if (!typeField.getText().isEmpty() && !dateField.getText().isEmpty()) {
                            selectedMatch.setMatchType(typeField.getText());
                            selectedMatch.setMatchDate(java.sql.Date.valueOf(dateField.getText()));

                            if ("T vs T".equals(typeField.getText())) {
                                TeamManagerDAO teamManagerDAO = new TeamManagerDAO();
                                Teams team1 = teamManagerDAO.getTeamByID(Integer.parseInt(team1Field.getText()));
                                Teams team2 = teamManagerDAO.getTeamByID(Integer.parseInt(team2Field.getText()));
//                                Teams winnerTeam = teamManagerDAO.getTeamByID(Integer.parseInt(winnerTeamField.getText()));

                                selectedMatch.setTeam1(team1);
                                selectedMatch.setTeam2(team2);
//                                selectedMatch.setWinnerTeam(winnerTeam);

                                // Rensar spelarinformation för lagmatcher
                                selectedMatch.setPlayer1(null);
                                selectedMatch.setPlayer2(null);

                            } else if ("P vs P".equals(typeField.getText())) {
                                PlayerDAO playerDAO = new PlayerDAO();
                                Player player1 = playerDAO.getPlayerByID(Integer.parseInt(player1Field.getText()));
                                Player player2 = playerDAO.getPlayerByID(Integer.parseInt(player2Field.getText()));
//                                Player winnerPlayer = playerDAO.getPlayerByID(Integer.parseInt(winnerPlayerField.getText()));

                                selectedMatch.setPlayer1(player1);
                                selectedMatch.setPlayer2(player2);
//                                selectedMatch.setWinnerPlayer(winnerPlayer);

                                // Rensar laginformation för spelarmatcher
                                selectedMatch.setTeam1(null);
                                selectedMatch.setTeam2(null);
//                                selectedMatch.setWinnerTeam(null);
                            }

                            // Uppdatera matchen i databasen
                            MatchesDAO.updateMatch(selectedMatch);
                            table.refresh();
                            popupStage.close();
                            System.out.println("Match updated successfully!");
                        } else {
                            System.out.println("Fields can't be empty!");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("Error updating match. Check code.");
                    }
                });
                popupLayout.getChildren().addAll(dateLabel,dateField);

                //if sats för att visa rätt popuplayout efter val av matchtyp
                if (isTeamMatch){
                    teamMatchLayout.getChildren().addAll(team1Label,team1Field,team2Label,team2Field/*,winnerTeamLabel,winnerTeamField*/);
                    popupLayout.getChildren().add(teamMatchLayout);
                } else {
                    playerMatchLayout.getChildren().addAll(player1Label,player1Field,player2Label,player2Field/*,winnerPlayerLabel,winnerPlayerField*/);
                    popupLayout.getChildren().add(playerMatchLayout);
                }

                popupLayout.getChildren().add(saveButton);

                Scene popupScene = new Scene(popupLayout, 400, 400);
                popupStage.setScene(popupScene);
                popupStage.show();
            } else {
                System.out.println("No match selected!");
            }
        });

        registerResultBtn.setOnAction(e -> {
            // Hämta vald match från tabellen som man väljer
            Matches selectedMatch = table.getSelectionModel().getSelectedItem();

            if (selectedMatch != null) {
                // Skapar ett popup-layout
                Stage popupStage = new Stage();
                popupStage.setTitle("Register Results");

                VBox popupLayout = new VBox(10);
                popupLayout.setPadding(new Insets(15));

                // Labels och TextFields för att mata in resultaten
                Label team1Label, team2Label, player1Label, player2Label;
                TextField team1ScoreField, team2ScoreField, player1ScoreField, player2ScoreField;
                // if else sats vald matchtyp och vad som ska visas
                if (selectedMatch.getMatchType().equals("T vs T")) {
                    // För lagmatch (T vs T)
                    team1Label = new Label("Result for " + selectedMatch.getTeam1().getName() + ":");
                    team1ScoreField = new TextField();

                    team2Label = new Label("Result for " + selectedMatch.getTeam2().getName() + ":");
                    team2ScoreField = new TextField();

                    player1Label = null;
                    player2Label = null;
                    player1ScoreField = null;
                    player2ScoreField = null;
                } else {
                    // För spelar-match (P vs P)
                    player1Label = new Label("Result for " + selectedMatch.getPlayer1().getNickname() + ":");
                    player1ScoreField = new TextField();

                    player2Label = new Label("Result for " + selectedMatch.getPlayer2().getNickname() + ":");
                    player2ScoreField = new TextField();

                    team1Label = null;
                    team2Label = null;
                    team1ScoreField = null;
                    team2ScoreField = null;
                }

                Button saveButton = new Button("Save Results");
                saveButton.setOnAction(event -> {
                    try {
                        if (selectedMatch.getMatchType().equals("T vs T")) {
                            // Kontrollera att fälten inte är tomma för lagmatch
                       if (!team1ScoreField.getText().isEmpty() && !team2ScoreField.getText().isEmpty()) {
                           int team1Score = Integer.parseInt(team1ScoreField.getText());
                           int team2Score = Integer.parseInt(team2ScoreField.getText());
                         // Uppdaterar matchens resultat för lag
                         selectedMatch.setTeam1Score(team1Score);
                         selectedMatch.setTeam2Score(team2Score);

                       // Visa vinnaren baserat på resultaten
                          if (team1Score > team2Score) {
                              selectedMatch.setWinnerTeam(selectedMatch.getTeam1());
                          } else if (team2Score > team1Score) {
                              selectedMatch.setWinnerTeam(selectedMatch.getTeam2());
                          } else {
                               selectedMatch.setWinnerTeam(null); // Oavgjort
                          }
                       }
                        } else {
                    // Kontrollerar att fälten inte är tomma för spelarmatch
                        if (!player1ScoreField.getText().isEmpty() && !player2ScoreField.getText().isEmpty()) {
                            int player1Score = Integer.parseInt(player1ScoreField.getText());
                            int player2Score = Integer.parseInt(player2ScoreField.getText());

                       // Uppdaterar matchens resultat för spelare
                             selectedMatch.setPlayer1Score(player1Score);
                             selectedMatch.setPlayer2Score(player2Score);

                        // Visar vinnaren baserat på resultaten
                        if (player1Score > player2Score) {
                            selectedMatch.setWinnerPlayer(selectedMatch.getPlayer1());
                        } else if (player2Score > player1Score) {
                            selectedMatch.setWinnerPlayer(selectedMatch.getPlayer2());
                        } else {
                            selectedMatch.setWinnerPlayer(null); // Oavgjort
                        }
                     }
                    }
                 // Uppdaterar matchen i databasen
                    MatchesDAO.updateMatch(selectedMatch);

                 // Uppdaterar tabellen
                    table.refresh();
                    System.out.println("Results registered successfully!");
                    if (selectedMatch.getMatchType().equals("T vs T")) {
                        System.out.println("Result: " + selectedMatch.getTeam1().getName() + " " + selectedMatch.getTeam1Score() + " - " + selectedMatch.getTeam2Score() + " " + selectedMatch.getTeam2().getName());
                    if (selectedMatch.getWinnerTeam() != null) {
                        System.out.println("Congratulations for the winner team: " + selectedMatch.getWinnerTeam().getName());
                    } else {
                        System.out.println("No winner: Draw");
                    }
                    } else if (selectedMatch.getMatchType().equals("P vs P")) {
                        System.out.println("Result: " + selectedMatch.getPlayer1().getNickname() + " " + selectedMatch.getPlayer1Score() + " - " + selectedMatch.getPlayer2Score() + " " + selectedMatch.getPlayer2().getNickname());
                    if (selectedMatch.getWinnerPlayer() != null) {
                        System.out.println("Congratulations for the winner player: " + selectedMatch.getWinnerPlayer().getNickname());
                    } else {
                         System.out.println("No winner: Draw");
                    }
                }

                    popupStage.close();

                } catch (NumberFormatException ex) {
                      System.out.println("Invalid input! Scores must be numbers.");
                    }
                });

                // Lägg till komponenterna i layouten beroende på matchtyp
                if (selectedMatch.getMatchType().equals("T vs T")) {
                    popupLayout.getChildren().addAll(team1Label, team1ScoreField, team2Label, team2ScoreField, saveButton);
                } else {
                    popupLayout.getChildren().addAll(player1Label, player1ScoreField, player2Label, player2ScoreField, saveButton);
                }

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
