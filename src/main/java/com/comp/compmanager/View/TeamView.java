package com.comp.compmanager.View;

import com.comp.compmanager.DAO.TeamManagerDAO;
import com.comp.compmanager.entities.Admin;
import com.comp.compmanager.entities.Teams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
        //general dimensions
        buttonBar.setPadding(new Insets(8));
        //buttons
        Button deleteTeamsBtn = new Button("Delete Team");
        Button addTeamsBtn = new Button("Add Team");
        //add buttons to bar
        buttonBar.getButtons().addAll(deleteTeamsBtn, addTeamsBtn);
        //add button functionality via Lambda expression
        deleteTeamsBtn.setOnAction(e -> {
            teamManagerDAO.deleteTeam(teams.get(0));
            table.getItems().remove(teams.get(0));
        });
        addTeamsBtn.setOnAction(e -> System.out.println("add someone"));
        teamManagerDAO.addTeam(teams.get(0));
        table.setItems(table.getItems());
        //DISABLAR KNAPPAR OM MAN INTE ÄR ADMIN
        if (!viewManager.isAdmin()) {
            buttonBar.setDisable(true);
        }


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