package com.comp.compmanager.View;

import com.comp.compmanager.DAO.PlayerDAO;
import com.comp.compmanager.entities.Player;
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
//        VBox toolVBox = new VBox(10, nameTextField, surnameTextField, nicknameTextField, teamTextField, buttonBar);
//        toolVBox.setPadding(new Insets(8));

        VBox vBox = new VBox(10, nameTextField,surnameTextField, nicknameTextField, teamTextField, buttonBar);
        vBox.setPadding(new Insets(10));
        AnchorPane.setTopAnchor(vBox, 30.0);
        AnchorPane.setLeftAnchor(vBox, 10.0);
        AnchorPane.setRightAnchor(vBox, 10.0);
        AnchorPane.setBottomAnchor(vBox, 10.0);

        layout.getChildren().add(vBox);

        return layout;

    }
}
