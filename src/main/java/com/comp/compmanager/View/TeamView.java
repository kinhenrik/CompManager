package com.comp.compmanager.View;

import com.comp.compmanager.DAO.TeamManagerDAO;
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

public class TeamView {
    private final ViewManager viewManager;

public TeamView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

public AnchorPane getView() {
    AnchorPane layout = new AnchorPane();

    Label label = new Label("Team View");
    AnchorPane.setTopAnchor(label, 10.0);
    AnchorPane.setLeftAnchor(label, 10.0);
    layout.getChildren().add(label);

    // Skapa en TableView
    TableView<Teams> table = new TableView<>();

    TableColumn<Teams, Integer> id_col = new TableColumn<>("Team ID");
    id_col.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<Teams, String> name_col = new TableColumn<>("Team name");
    name_col.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Teams, String> games_col = new TableColumn<>("Game");
    games_col.setCellValueFactory(new PropertyValueFactory<>("games"));

    table.getColumns().addAll(id_col, name_col, games_col);

    // Lägg till data i tabellen
    TeamManagerDAO teamManagerDAO = new TeamManagerDAO();
    List<Teams> teams = teamManagerDAO.getAllTeams();
    ObservableList<Teams> observableList = FXCollections.observableArrayList(teams);
    table.setItems(observableList);

    //BUTTON BAR
    ButtonBar buttonBar = new ButtonBar();
    if (!viewManager.isAdmin()) {
        buttonBar.setDisable(true);
    }
    //general dimensions
    buttonBar.setPadding(new Insets(10));
    //buttons
    Button deleteTeamsBtn = new Button("Delete Team");
    Button addTeamsBtn = new Button("Add Team");
    Button editTeamsBtn = new Button("Edit Team");
    //add buttons to bar
    buttonBar.getButtons().addAll(deleteTeamsBtn, addTeamsBtn, editTeamsBtn);
    //delete knapp för att ta bort lag från tabellen
    deleteTeamsBtn.setOnAction(e -> {
    // Hämta det valda laget från tabellen
    Teams selectedTeam = table.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
    // Ta bort laget från databasen
            teamManagerDAO.deleteTeam(selectedTeam);
    // Ta bort laget från tabellen
            table.getItems().remove(selectedTeam);
            System.out.println("Team deleted!");
        } else {
    // Visa ett varningsmeddelande om inget lag är valt
        System.out.println("No team selected");
//  Alert alert = new Alert(Alert.AlertType.WARNING, "No team selected for deletion!", ButtonType.OK);
//      alert.showAndWait();
        }
    });

    // add/lägga till knapp för att lägga till nya Teams i tabellen och sen uppdatera tabellen samt en popupfönster
    addTeamsBtn.setOnAction(e -> {
    // Skapa en ny Stage för popup fönster
    Stage popupStage = new Stage();
        popupStage.setTitle("Add New Team");

    // Skapa layout för popup
    VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(15));

     // Skapa input-fält för lagets namn
    Label nameLabel = new Label("Team Name:");
        TextField nameField = new TextField();

    // Spara-knapp
    Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
         String teamName = nameField.getText();
             if (!teamName.isEmpty()) {
    // Skapa ett nytt team objekt och lägg till det i listan
                 Teams newTeam = new Teams();
                    newTeam.setName(teamName);
                 // Lägg till i databasen
                    teamManagerDAO.addTeam(newTeam);
                 // Uppdatera tabellen
                    table.getItems().add(newTeam);
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

    // Edit knapp för att redigera befintliga Teams i tabellen och sen uppdatera tabellen samt en popupfönster
    editTeamsBtn.setOnAction(e -> {
        Teams selectedTeam = table.getSelectionModel().getSelectedItem();

        Stage popupStage = new Stage();
        popupStage.setTitle("Edit Team");

        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(15));

        Label nameLabel = new Label("Change new team name:");
        TextField nameField = new TextField();

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            String UpdateTeamName = nameField.getText();
            if (!UpdateTeamName.isEmpty()) {
                selectedTeam.setName(UpdateTeamName);
                teamManagerDAO.updateTeam(selectedTeam);
                table.refresh();
                popupStage.close();
                System.out.println("Team updated");
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
