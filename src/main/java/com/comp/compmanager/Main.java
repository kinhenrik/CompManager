package com.comp.compmanager;

import com.comp.compmanager.DAO.PlayerDAO;
import com.comp.compmanager.entities.Player;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        /*
        //creates player and adds to DB
        PlayerDAO playerDAO = new PlayerDAO();
        Player spelare1 = new Player("Added player asd", "TestReam 7");

        //makes changes in TABLE
        spelare1.setTeam("changed team, 5");
        playerDAO.updatePlayer(spelare1);

        //list player via ID
        Player playerFromDatabase = playerDAO.getPlayerByID(1);
        System.out.println("Player with ID=" + playerFromDatabase.getId() + ": " + playerFromDatabase.getNickname());

        System.out.println("Number of players in database: " + playerDAO.getAllPlayers().size());
        */

        PlayerDAO playerDAO = new PlayerDAO();


        //JAVAFX


        //TABLE
        TableView<Player> table = new TableView();

        //"ID" col getting id data
        TableColumn<Player, String> id_column = new TableColumn<>("ID");
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));

        //"Player" col getting nickname data
        TableColumn<Player, String> name_column = new TableColumn<>("Player");
        name_column.setCellValueFactory(new PropertyValueFactory<>("nickname"));

        //"Team" col getting team data
        TableColumn<Player, String> team_column = new TableColumn<>("Team");
        team_column.setCellValueFactory(new PropertyValueFactory<>("team"));

        //combine all cols
        table.getColumns().addAll(id_column, name_column, team_column);

        //adds all col data to ObservableList for JavaFX
        List<Player> players = playerDAO.getAllPlayers();
        ObservableList<Player> observableList = FXCollections.observableArrayList(players);
        table.setItems(observableList);


        //TEXT FIELDS
        //nickname
        TextField nicknameTextField = new TextField();

        nicknameTextField.setPromptText("Nickname...");
        //team
        TextField teamTextField = new TextField();
        teamTextField.setPromptText("Team...");


        //BUTTON BAR
        ButtonBar buttonBar = new ButtonBar();
        //general dimensions
        buttonBar.setPadding(new Insets(8));
        //buttons
        Button deletePlayerBtn = new Button("Delete Selected");
        Button addPlayerBtn = new Button("Add New Player");
        Button editPlayerBtn = new Button("Edit selected");
        //add buttons to bar
        buttonBar.getButtons().addAll(deletePlayerBtn, addPlayerBtn, editPlayerBtn);
        //add button functionality via Lambda expression
        deletePlayerBtn.setOnAction(e -> {  playerDAO.deletePlayer(table.getSelectionModel().getSelectedItem());
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        });

        addPlayerBtn.setOnAction(e -> { playerDAO.addPlayer(new Player(nicknameTextField.getText(), teamTextField.getText()));
            table.getItems().add(new Player(nicknameTextField.getText(), teamTextField.getText()));
        });

        editPlayerBtn.setOnAction(e -> { table.getSelectionModel().getSelectedItem().setNickname(nicknameTextField.getText());
            table.getSelectionModel().getSelectedItem().setTeam(teamTextField.getText());
            playerDAO.updatePlayer(table.getSelectionModel().getSelectedItem());
            //TODO IDK HOW TO SHOW IT IN TABLE

        });


        //TOOL-VBOX
        VBox toolVBox = new VBox(10, nicknameTextField, teamTextField, buttonBar);
        toolVBox.setPadding(new Insets(8));


        //ADD ELEMENTS TO PANE
        AnchorPane pane = new AnchorPane(table);
        pane.setTopAnchor(table, 0.0);
        pane.setBottomAnchor(table, 0.0);
        pane.setLeftAnchor(table, 0.0);
        pane.getChildren().add(toolVBox);
        pane.setRightAnchor(toolVBox, 0.0);



        Scene scene = new Scene(pane, 610, 400);
        stage.setTitle("Player");
        stage.setScene(scene);
        stage.show();


    }


    public static void main(String[] args) {
        launch();
    }

}
