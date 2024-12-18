package com.comp.compmanager.View;

import com.comp.compmanager.DAO.PlayerDAO;
import com.comp.compmanager.entities.Player;
import com.comp.compmanager.entities.Teams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class PlayerView {

    private final ViewManager viewManager;
    private ComboBox teamsComboBox;

    public PlayerView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public AnchorPane getView() {
        AnchorPane layout = new AnchorPane();

        Label label = new Label("Player View");
        AnchorPane.setTopAnchor(label, 10.0);
        AnchorPane.setLeftAnchor(label, 10.0);
        layout.getChildren().add(label);

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
        PlayerDAO playerDAO = new PlayerDAO();
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

        //DROP DOWN
        ObservableList<Teams> teamsObservableList = new TeamView(viewManager).teamList();
        teamsComboBox = new ComboBox(teamsObservableList);
        teamsComboBox.setPromptText("Teams");

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
        deletePlayerBtn.setOnAction(e -> {
            playerDAO.deletePlayer(table.getSelectionModel().getSelectedItem());
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        });

        addPlayerBtn.setOnAction(e -> {
            if (nameTextField.getText() != "" ) {

                playerDAO.addPlayer(new Player(nameTextField.getText(), surnameTextField.getText(), nicknameTextField.getText(), (Teams)teamsComboBox.getSelectionModel().getSelectedItem()));
                //table.getItems().add(new Player(nameTextField.getText(), surnameTextField.getText(),nicknameTextField.getText(), (Teams)teamsComboBox.getSelectionModel().getSelectedItem()));
                table.getItems().clear();
                table.getItems().addAll(playerDAO.getAllPlayers());

                //reset text fields after use
                nameTextField.setText("");
                surnameTextField.setText("");
                nicknameTextField.setText("");
                teamsComboBox.setPromptText("Teams");
            }
            else {
                System.out.println("NAME FIELD CANT EMPTY");
            }

            table.refresh();
        });

        editPlayerBtn.setOnAction(e -> {
            if (nameTextField.getText() != "") {
                table.getSelectionModel().getSelectedItem().setName(nameTextField.getText());
                table.getSelectionModel().getSelectedItem().setSurname(surnameTextField.getText());
                table.getSelectionModel().getSelectedItem().setNickname(nicknameTextField.getText());
                table.getSelectionModel().getSelectedItem().setTeam((Teams)teamsComboBox.getSelectionModel().getSelectedItem());

                playerDAO.updatePlayer(table.getSelectionModel().getSelectedItem());

                //reset text fields after use
                nameTextField.setText("");
                surnameTextField.setText("");
                nicknameTextField.setText("");
                teamsComboBox.setPromptText("Teams");

            }
            else {
                System.out.println("NAME FIELD CANT EMPTY");
            }

            table.refresh();

        });

        //DISABLAR KNAPPAR OCH TEXTFIELDS OM MAN INTE ÄR ADMIN
        if (!viewManager.isAdmin()) {
            nameTextField.setDisable(true);
            surnameTextField.setDisable(true);
            nicknameTextField.setDisable(true);
            //teamTextField.setDisable(true);
            buttonBar.setDisable(true);
        }

        //TOOL-VBOX
//        VBox toolVBox = new VBox(10, nameTextField, surnameTextField, nicknameTextField, teamTextField, buttonBar);
//        toolVBox.setPadding(new Insets(8));

        VBox vBox = new VBox(10,table, nameTextField,surnameTextField, nicknameTextField, teamsComboBox/*teamTextField,*/ , buttonBar);
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

