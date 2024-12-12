package com.comp.compmanager.View;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewManager {
    private final Stage stage;
    private final AnchorPane root;
    private final MenuBar menuBar;

    public ViewManager(Stage stage) {
        this.stage = stage;
        this.root = new AnchorPane();
        this.menuBar = createMenuBar();
        initializeRoot();
    }

    private void initializeRoot() {
        // Lägg till menyfältet i root
        AnchorPane.setTopAnchor(menuBar, 0.0);
        AnchorPane.setLeftAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(menuBar, 0.0);
        root.getChildren().add(menuBar);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Välj vy");

        // Skapa menyval
        MenuItem mainMenuItem = new MenuItem("Main");
        MenuItem adminMenuItem = new MenuItem("Admin View");
        MenuItem teamMenuItem = new MenuItem("Team View");
        MenuItem playerMenuItem = new MenuItem("Player View");
        MenuItem gamesMenuItem = new MenuItem("Games View");

        // Lägg till valen i menyn
        menu.getItems().addAll(mainMenuItem, adminMenuItem, teamMenuItem, playerMenuItem, gamesMenuItem);
        menuBar.getMenus().add(menu);

        // Koppla menyval till vyhanteraren
        mainMenuItem.setOnAction(e -> showMainMenu());
        adminMenuItem.setOnAction(e -> showAdminView());
        teamMenuItem.setOnAction(e -> showTeamView());
        playerMenuItem.setOnAction(e -> showPlayerView());

        return menuBar;
    }
    // Skapa huvudmenyn
    public void showMainMenu() {
        AnchorPane mainMenu = new AnchorPane();
        setView(mainMenu);
    }
    // Skapa AdminView
    public void showAdminView() {
        AnchorPane adminView = new AdminView(this).getView();
        setView(adminView);
    }
    public void showGamesView() {
        AnchorPane gamesView = new GamesView(this).getView();
        setView(gamesView);
    }
    // Skapa TeamView
    public void showTeamView() {
        AnchorPane teamView = new TeamView(this).getView();
        setView(teamView);
    }

    // Skapa PlayerView
    public void showPlayerView() {
        AnchorPane playerView = new PlayerView(this).getView();
        setView(playerView);
    }

    // Ta bort allt under menyfältet och lägg till den nya vyn
    private void setView(AnchorPane newView) {

        root.getChildren().removeIf(node -> node != menuBar);
        AnchorPane.setTopAnchor(newView, 30.0); // Placera under menyn
        root.getChildren().add(newView);
    }
    // Starta scenen med huvudmenyn
    public void start() {

        Scene scene = new Scene(root, 850, 600);
        stage.setScene(scene);
        stage.setTitle("CompManager");
        stage.show();
        showMainMenu(); // Visa huvudmenyn först
    }
}
