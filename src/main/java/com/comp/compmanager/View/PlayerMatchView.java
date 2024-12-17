//package com.comp.compmanager.View;
//
//import com.comp.compmanager.DAO.PlayerMatchDAO;
//import com.comp.compmanager.entities.PlayerMatches;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.util.List;
//
//public class PlayerMatchView {
//    private final ViewManager viewManager;
//
//    public PlayerMatchView(ViewManager viewManager) {
//        this.viewManager = viewManager;
//    }
//
//    public AnchorPane getView() {
//        AnchorPane layout = new AnchorPane();
//
//        Label label = new Label("Match View");
//        AnchorPane.setTopAnchor(label, 10.0);
//        AnchorPane.setLeftAnchor(label, 10.0);
//        layout.getChildren().add(label);
//
//
//        TableView<PlayerMatches> table = new TableView<>();
//        // Kolumner för Match ID
//        TableColumn<PlayerMatches, Integer> idPlayerColumn = new TableColumn<>("Match ID");
//        idPlayerColumn.setCellValueFactory(new PropertyValueFactory<>("playerMatchId"));
//
//        TableColumn<PlayerMatches, String> datePlayerColumn = new TableColumn<>("Date");
//        datePlayerColumn.setCellValueFactory(new PropertyValueFactory<>("playerMatchDate"));
//
//        TableColumn<PlayerMatches, String> typePlayerColumn = new TableColumn<>("Type");
//        typePlayerColumn.setCellValueFactory(new PropertyValueFactory<>("playerMatchType"));
//
//        TableColumn<PlayerMatches, String> player1Column = new TableColumn<>("Player 1");
//        player1Column.setCellValueFactory(new PropertyValueFactory<>("player1"));
//
//        TableColumn<PlayerMatches, String> player2Column = new TableColumn<>("Player 2");
//        player2Column.setCellValueFactory(new PropertyValueFactory<>("player2"));
//
//        TableColumn<PlayerMatches, String> winnerPlayerColumn = new TableColumn<>("Player Winner");
//        winnerPlayerColumn.setCellValueFactory(new PropertyValueFactory<>("winnerPlayer"));
//
//        // Lägg till kolumner i tabellen
//        table.getColumns().addAll(idPlayerColumn,datePlayerColumn,player1Column,player2Column,winnerPlayerColumn);
//
//        // Lägg till data i tabellen// Omvandla lista till ObservableList för JavaFX
//        PlayerMatchDAO playerMatchDAO = new PlayerMatchDAO();
//        List<PlayerMatches> playerMatches = playerMatchDAO.getAllPlayerMatches();
//        ObservableList<PlayerMatches> observablePlayerMatches = FXCollections.observableArrayList(playerMatches);
//        table.setItems(observablePlayerMatches);
//
//        //BUTTON BAR
//        ButtonBar buttonBar = new ButtonBar();
//        if (!viewManager.isAdmin()) {
//            buttonBar.setDisable(true);
//        }
//        //general dimensions
//        buttonBar.setPadding(new Insets(10));
//        //buttons
//        Button deleteMatchBtn = new Button("Delete Match");
//        Button addMatchBtn = new Button("Add Match");
//        Button editMatchBtn = new Button("Edit Match");
//        //add buttons to bar
//        buttonBar.getButtons().addAll(deleteMatchBtn, addMatchBtn, editMatchBtn);
//        //delete knapp för att ta bort lag från tabellen
//        deleteMatchBtn.setOnAction(e -> {
//            // Hämta det valda laget från tabellen
//            PlayerMatches selectedPlayerMatch = table.getSelectionModel().getSelectedItem();
//            if (selectedPlayerMatch != null) {
//                // Ta bort laget från databasen
//                playerMatchDAO.deletePlayerMatch(selectedPlayerMatch);
//                // Ta bort laget från tabellen
//                table.getItems().remove(selectedPlayerMatch);
//                System.out.println("Match deleted!");
//            } else {
//                // Visa ett varningsmeddelande om inget lag är valt
//                System.out.println("No match selected");
////              Alert alert = new Alert(Alert.AlertType.WARNING, "No team selected for deletion!", ButtonType.OK);
////              alert.showAndWait();
//            }
//        });
//
//        // add/lägga till knapp för att lägga till nya natcher i tabellen och sen uppdatera tabellen samt en popupfönster
//        addMatchBtn.setOnAction(e -> {
//            // Skapa en ny Stage för popup fönster
//            Stage popupStage = new Stage();
//            popupStage.setTitle("Add New Match");
//
//            // Skapa layout för popup
//            VBox popupLayout = new VBox(10);
//            popupLayout.setPadding(new Insets(15));
//
//            // Skapa input-fält för lagets namn
//            Label nameLabel = new Label("Match Name:");
//            TextField nameField = new TextField();
//
//            // Spara-knapp
//            Button saveButton = new Button("Save");
//            saveButton.setOnAction(event -> {
//                String matchPlayerName = nameField.getText();
//                if (!matchPlayerName.isEmpty()) {
//                    // Skapa ett nytt team objekt och lägg till det i listan
//                    PlayerMatches newPlayerMatch = new PlayerMatches();
//                    newPlayerMatch.setPlayerMatchType(matchPlayerName);
//                    // Lägg till i databasen
//                    playerMatchDAO.addPlayerMatch(newPlayerMatch);
//                    // Uppdatera tabellen
//                    table.getItems().add(newPlayerMatch);
//                    // Stäng popupen fönstret
//                    popupStage.close();
//                    System.out.println("New match added");
//                } else {
//                    // Om namnet är tomt, visa ett varningsmeddelande
//                    System.out.println("Match can't be empty!");
////                Alert alert = new Alert(Alert.AlertType.WARNING, "Team name can't be empty!", ButtonType.OK);
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
//
//        // Edit knapp för att redigera befintliga matcher i tabellen och sen uppdatera tabellen samt en popupfönster
//        editMatchBtn.setOnAction(e -> {
//            PlayerMatches selectedPlayerMatch = table.getSelectionModel().getSelectedItem();
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
//                String UpdatePlayerMatch = nameField.getText();
//                if (!UpdatePlayerMatch.isEmpty()) {
//                    selectedPlayerMatch.setPlayerMatchType(UpdatePlayerMatch);
//                    PlayerMatchDAO.updatePlayerMatch(selectedPlayerMatch);
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
//
//        // Layout med VBox
//        VBox vBox = new VBox(10, table, buttonBar);
//        vBox.setPadding(new Insets(10));
//        vBox.setPrefWidth(820);
//        vBox.setPrefHeight(400);
//        AnchorPane.setTopAnchor(vBox, 30.0);
//        AnchorPane.setLeftAnchor(vBox, 10.0);
//        AnchorPane.setRightAnchor(vBox, 10.0);
//        AnchorPane.setBottomAnchor(vBox, 10.0);
//
//        layout.getChildren().add(vBox);
//
//        return layout;
//    }
//}
