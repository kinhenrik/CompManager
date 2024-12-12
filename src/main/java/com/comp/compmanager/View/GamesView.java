package com.comp.compmanager.View;
import com.comp.compmanager.DAO.AdminDAO;
import com.comp.compmanager.DAO.GamesDAO;
import com.comp.compmanager.entities.Admin;
import com.comp.compmanager.entities.Games;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

        TableColumn<Games, String> id_col = new TableColumn<>("id");
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Games, String> firstname_col = new TableColumn<>("Game name");
        firstname_col.setCellValueFactory(new PropertyValueFactory<>("name"));
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
//        //general dimensions
//        buttonBar.setPadding(new Insets(8));
//        //buttons
//        Button deletePlayerBtn = new Button("Delete Player");
//        Button addPlayerBtn = new Button("Add Player");
//        //add buttons to bar
//        buttonBar.getButtons().addAll(deletePlayerBtn, addPlayerBtn);
//        //add button functionality via Lambda expression
//        deletePlayerBtn.setOnAction(e -> {
//            adminDAO.deleteAdmin(admins.get(0));
//            table.getItems().remove(admins.get(0));
//        });
//        addPlayerBtn.setOnAction(e -> System.out.println("add someone"));

//
        // Layout med VBox
        VBox vBox = new VBox(10, table);
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


