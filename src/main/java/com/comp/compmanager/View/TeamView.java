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


    private TeamManagerDAO teamManagerDAO = new TeamManagerDAO();

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

        TableColumn<Teams, String> name_col = new TableColumn<>("Team Name");
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));

        table.getColumns().addAll(id_col, name_col);

        ObservableList<Teams> teams = teamList();
        table.setItems(teams);

        //BUTTON BAR
        ButtonBar buttonBar = new ButtonBar();

        //DISABLAR KNAPPAR OM MAN INTE ÄR ADMIN
        if (!viewManager.isAdmin()) {
            buttonBar.setDisable(true);
        }

        //general dimensions
        buttonBar.setPadding(new Insets(8));
        //buttons
        Button deleteTeamsBtn = new Button("Delete Team");
        Button addTeamsBtn = new Button("Add Team");
        Button editTeamsBtn = new Button("Edit Team");
        //add buttons to bar
        buttonBar.getButtons().addAll(deleteTeamsBtn, addTeamsBtn, editTeamsBtn);
        //add button functionality via Lambda expression
        deleteTeamsBtn.setOnAction(e -> {
            // Hämta det valda laget från tabellen
            Teams selectedTeam = table.getSelectionModel().getSelectedItem();
            if (selectedTeam != null) {
                // Ta bort laget från databasen
                TeamManagerDAO.deleteTeam(selectedTeam);
                // Ta bort laget från tabellen
                table.getItems().remove(selectedTeam);
                System.out.println("Team deleted!");
            } else {
                // Visa ett varningsmeddelande om inget lag är valt
                System.out.println("No team selected");
//              Alert alert = new Alert(Alert.AlertType.WARNING, "No team selected for deletion!", ButtonType.OK);
//              alert.showAndWait();
            }
        });
//            teamManagerDAO.deleteTeam(teams.get(0));
//            table.getItems().remove(teams.get(0));
//        });


        addTeamsBtn.setOnAction(e -> {
            Stage popupStage = new Stage();
            popupStage.setTitle("Add New Team");

            VBox popupLayout = new VBox(10);
            popupLayout.setPadding(new Insets(15));

            Label nameLabel = new Label("Team Name:");
            TextField nameField = new TextField();

            Button saveButton = new Button("Add Team");
            saveButton.setOnAction(event -> {
                try {
                    if (!nameField.getText().isEmpty()) {
                        Teams newTeam = new Teams();
                        newTeam.setName(nameField.getText());

                        TeamManagerDAO.addTeam(newTeam); // Sparat team till databasen
                        table.getItems().add(newTeam); // Lägger till teamet i tabellen
                        table.refresh();

                        popupStage.close();
                        System.out.println("Team added successfully!");
                    } else {
                        System.out.println("Team name can't be empty!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Error adding team. Check code");
                }
            });

            popupLayout.getChildren().addAll(nameLabel, nameField, saveButton);

            Scene popupScene = new Scene(popupLayout, 300, 150);
            popupStage.setScene(popupScene);
            popupStage.show();
        });


        editTeamsBtn.setOnAction(e -> {
        Teams selectedTeam = table.getSelectionModel().getSelectedItem();

        if (selectedTeam != null) { // Säkerställer att ett team är valt
            Stage popupStage = new Stage();
            popupStage.setTitle("Edit Team");

            VBox popupLayout = new VBox(10);
            popupLayout.setPadding(new Insets(15));

            Label nameLabel = new Label("Team Name:");
            TextField nameField = new TextField(selectedTeam.getName());

            Button saveButton = new Button("Save Changes");
            saveButton.setOnAction(event -> {
                try {
                    if (!nameField.getText().isEmpty()) {
                        selectedTeam.setName(nameField.getText());

                        TeamManagerDAO.updateTeam(selectedTeam); // Uppdatera team i databasen
                        table.refresh(); // Uppdatera tabellen
                        popupStage.close();
                        System.out.println("Team updated successfully!");
                    } else {
                        System.out.println("Team name can't be empty!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Error updating team. Check code!");
                }
            });

            popupLayout.getChildren().addAll(nameLabel, nameField, saveButton);

            Scene popupScene = new Scene(popupLayout, 300, 150);
            popupStage.setScene(popupScene);
            popupStage.show();
        } else {
            System.out.println("No team selected!");
        }
    });

//                System.out.println("add someone"));
//            teamManagerDAO.addTeam(teams.get(0));
//            table.setItems(table.getItems());

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

    public ObservableList teamList() {
        //Skapar en lista med alla admins från databasen och gör om till en ObservableList så att den kan användas i en tabell eller dropdown-lista
        List<Teams> teams = teamManagerDAO.getAllTeams();
        ObservableList<Teams> observableList = FXCollections.observableArrayList(teams);
        return observableList;
    }
}
