package com.comp.compmanager;

import com.comp.compmanager.DAO.playerDAO;
import com.comp.compmanager.entities.Player;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //creates player and adds to DB
        playerDAO playerDAO = new playerDAO();
        Player spelare1 = new Player("Added player asd", "TestReam 7");

        //makes changes in TABLE
        spelare1.setTeam("changed team, 5");
        playerDAO.updatePlayer(spelare1);

        //list player via ID
        Player playerFromDatabase = playerDAO.getPlayerByID(1);
        System.out.println("Player with ID=" + playerFromDatabase.getId() + ": " + playerFromDatabase.getNickname());

        System.out.println("Number of players in database: " + playerDAO.getAllPlayers().size());

        //JavaFX
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

        //adds table to AnchorPane and makes it dynamically sized
        AnchorPane pane = new AnchorPane(table);
        pane.setTopAnchor(table, 0.0);
        pane.setBottomAnchor(table, 0.0);
        pane.setLeftAnchor(table, 0.0);
        pane.setRightAnchor(table, 0.0);

        Scene scene = new Scene(pane, 500, 300);
        stage.setTitle("Player");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
