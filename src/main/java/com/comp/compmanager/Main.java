package com.comp.compmanager;

import com.comp.compmanager.DAO.SpelareDAO;
import com.comp.compmanager.entities.Spelare;
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

        //Skapar en spelare och lägger in tabellen i en databas
        SpelareDAO spelareDAO = new SpelareDAO();

        Spelare spelare1 = new Spelare("Tillagd spelare asd", "Testlag 4");
        if (spelareDAO.addPlayer(spelare1)) {
            System.out.println("Player '" + spelare1.getNickname() + "' from team '" + spelare1.getLag() + "' added to database.");
        } else {
            System.out.println("Player '" + spelare1.getNickname() + "' from team '" + spelare1.getLag() + "' could not be added to database.");
        }

        //Gör ändring på tabelldata
        spelare1.setLag("Ändrat lag 1");
        spelareDAO.updatePlayer(spelare1);

        //Lista spelare från ID eller mer specifikt
        Spelare playerFromDatabase = spelareDAO.getPlayerByID(1);
        System.out.println("Player with ID=" + playerFromDatabase.getId() + ": " + playerFromDatabase.getNickname());

        System.out.println("Number of players in database: " + spelareDAO.getAllPlayers().size());

        //Tar bort en spelare från databasen
        //spelareDAO.deletePlayer(spelare1);



        //JavaFX
        TableView<Spelare> tabell = new TableView();

        //Kolumn med namnet "ID" som hämtar data från variabeln "id"
        TableColumn<Spelare, String> id_column = new TableColumn<>("ID");
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));

        //Kolumn med namnet "Player" som hämtar data från variabeln "nickname"
        TableColumn<Spelare, String> name_column = new TableColumn<>("Player");
        name_column.setCellValueFactory(new PropertyValueFactory<>("nickname"));

        //Kolumn med namnet "Team" som hämtar data från variabeln "lag"
        TableColumn<Spelare, String> team_column = new TableColumn<>("Team");
        team_column.setCellValueFactory(new PropertyValueFactory<>("lag"));

        //Lägger till alla kolumner i en tabell
        tabell.getColumns().addAll(id_column, name_column, team_column);

        //Lägger till all data från kolumnerna till en ObservableList så att den kan sättas in i en JavaFX-tabell
        List<Spelare> players = spelareDAO.getAllPlayers();
        ObservableList<Spelare> observableList = FXCollections.observableArrayList(players);
        tabell.setItems(observableList);

        //Lägger till tabellen i en AnchorPane och fäster den i varje kant så att den skalas upp/ner med fönsterstorleken
        AnchorPane pane = new AnchorPane(tabell);
        pane.setTopAnchor(tabell, 0.0);
        pane.setBottomAnchor(tabell, 0.0);
        pane.setLeftAnchor(tabell, 0.0);
        pane.setRightAnchor(tabell, 0.0);

        Scene scene = new Scene(pane, 500, 300);
        stage.setTitle("Players");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}