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

        //"Name" col getting name data
        TableColumn<Player, String> name_column = new TableColumn<>("Name");
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));

        //"Surname" col getting surname data
        TableColumn<Player, String> surname_column = new TableColumn<>("Surname");
        surname_column.setCellValueFactory(new PropertyValueFactory<>("surname"));

        //"Player" col getting nickname data
        TableColumn<Player, String> nickname_column = new TableColumn<>("Nickname");
        nickname_column.setCellValueFactory(new PropertyValueFactory<>("nickname"));

        //"Team" col getting team data
        TableColumn<Player, String> team_column = new TableColumn<>("Team");
        team_column.setCellValueFactory(new PropertyValueFactory<>("team"));

        //combine all cols
        table.getColumns().addAll(id_column, name_column, surname_column, nickname_column, team_column);

        //adds all col data to ObservableList for JavaFX
        List<Player> players = playerDAO.getAllPlayers();
        ObservableList<Player> observableList = FXCollections.observableArrayList(players);
        table.setItems(observableList);


        //TEXT FIELDS
        //name
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Name...");
        //surname
        TextField surnameTextField = new TextField();
        surnameTextField.setPromptText("Surname...");
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

        addPlayerBtn.setOnAction(e -> {
            if (nameTextField.getText() != "" ) {
                playerDAO.addPlayer(new Player(nameTextField.getText(), surnameTextField.getText(), nicknameTextField.getText(), teamTextField.getText()));
                table.getItems().add(new Player(nameTextField.getText(), surnameTextField.getText(),nicknameTextField.getText(), teamTextField.getText()));

                //reset text fields after use
                nameTextField.setText("");
                surnameTextField.setText("");
                nicknameTextField.setText("");
                teamTextField.setText("");
            }
            else {
                System.out.println("NAME FIELD CANT EMPTY");
            }
        });

        editPlayerBtn.setOnAction(e -> {
            if (nameTextField.getText() != "") {

                table.getSelectionModel().getSelectedItem().setName(nameTextField.getText());
                table.getSelectionModel().getSelectedItem().setSurname(surnameTextField.getText());
                table.getSelectionModel().getSelectedItem().setNickname(nicknameTextField.getText());
                table.getSelectionModel().getSelectedItem().setTeam(teamTextField.getText());
                playerDAO.updatePlayer(table.getSelectionModel().getSelectedItem());
                //TODO IDK HOW TO SHOW IT IN TABLE
                //reset text fields after use
                nameTextField.setText("");
                surnameTextField.setText("");
                nicknameTextField.setText("");
                teamTextField.setText("");
            }
            else {
                System.out.println("NAME FIELD CANT EMPTY");
            }

        });


        //TOOL-VBOX
        VBox toolVBox = new VBox(10, nameTextField, surnameTextField, nicknameTextField, teamTextField, buttonBar);
        toolVBox.setPadding(new Insets(8));


        //ADD ELEMENTS TO PANE
        AnchorPane pane = new AnchorPane(table);
        pane.setTopAnchor(table, 0.0);
        pane.setBottomAnchor(table, 0.0);
        pane.setLeftAnchor(table, 0.0);
        pane.getChildren().add(toolVBox);
        pane.setRightAnchor(toolVBox, 0.0);



        Scene scene = new Scene(pane, 800, 600);
        stage.setTitle("COMP MANAGER");
        stage.setScene(scene);
        stage.show();


    }


    public static void main(String[] args) {
        launch();
    }

}
