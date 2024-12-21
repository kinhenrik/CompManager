package com.comp.compmanager.View;

import com.comp.compmanager.DAO.PlayerDAO;
import com.comp.compmanager.entities.Player;
import com.comp.compmanager.entities.Teams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

        //FILTER DROPDOWN
        ComboBox filterDropdown = new ComboBox(new TeamView(viewManager).teamList());
        Button resetButton = new Button("Reset");
        filterDropdown.setPromptText("Filter by team...");
        filterDropdown.setPrefWidth(170);
        HBox dropdownBox = new HBox(10);
        dropdownBox.setAlignment(Pos.CENTER_RIGHT);
        dropdownBox.getChildren().addAll(filterDropdown, resetButton);
        filterDropdown.setOnAction(e -> {
            if (filterDropdown.getValue() != null) {
                table.setItems(playerList((Teams)filterDropdown.getValue()));
            }
        });
        resetButton.setOnAction(e -> {
            filterDropdown.getSelectionModel().clearSelection();
            table.setItems(observableList);
        });


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
        //add delete / add / edit buttons and actions

        deletePlayerBtn.setOnAction(e -> {
            Player selectedPlayer = table.getSelectionModel().getSelectedItem();
            if (selectedPlayer != null) {
                playerDAO.deletePlayer(selectedPlayer); // Använder den justerade delete-metoden
                observableList.remove(selectedPlayer); // Uppdatera tabellen
                table.refresh(); // Uppdatera GUI:t
            }
        });

        addPlayerBtn.setOnAction(e -> {
            if (!nameTextField.getText().isEmpty()) {
                Player newPlayer = new Player(nameTextField.getText(), surnameTextField.getText(), nicknameTextField.getText(), (Teams) teamsComboBox.getValue());
                playerDAO.addPlayer(newPlayer);
                observableList.add(newPlayer); // Lägg till spelaren i tabellen
                newPlayer.getTeam().getPlayers().add(newPlayer); // Uppdatera lagets lista
                nameTextField.clear();
                surnameTextField.clear();
                nicknameTextField.clear();
                teamsComboBox.getSelectionModel().clearSelection();
                table.refresh();
            } else {
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

            } else {
                System.out.println("NAME FIELD CANT EMPTY");
            }
            table.refresh();
        });

        //DISABLAR KNAPPAR OCH TEXTFIELDS OM MAN INTE ÄR ADMIN
        if (!viewManager.isAdmin()) {
            nameTextField.setDisable(true);
            surnameTextField.setDisable(true);
            nicknameTextField.setDisable(true);
            buttonBar.setDisable(true);
        }

        VBox vBox = new VBox(10,dropdownBox, table, nameTextField, surnameTextField, nicknameTextField, teamsComboBox, buttonBar);
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

    public ObservableList playerList(Teams team) {
        //Skapar en lista med alla players från databasen och gör om till en ObservableList så att den kan användas i en tabell eller dropdown-lista
        List<Player> player = PlayerDAO.getPlayerByTeam(team);
        ObservableList<Player> observableList = FXCollections.observableArrayList(player);
        return observableList;
    }
}

