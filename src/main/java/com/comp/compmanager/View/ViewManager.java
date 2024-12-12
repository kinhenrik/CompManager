package com.comp.compmanager.View;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

//        Image backgroundImage = new Image(getClass().getResource("/images/bild2.png").toExternalForm());
//        BackgroundImage bgImage = new BackgroundImage(
//            backgroundImage,
//            BackgroundRepeat.NO_REPEAT,
//            BackgroundRepeat.NO_REPEAT,
//            BackgroundPosition.CENTER,
//        new BackgroundSize(100, 100, false, false, true, true)
//        );
//        root.setBackground(new Background(bgImage));
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

        // Lägg till valen i menyn
        menu.getItems().addAll(mainMenuItem, adminMenuItem, teamMenuItem, playerMenuItem);
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

        Label welcomeLabel = new Label("--- || Welcome to || ---");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: beige;");
        // Placerering av etiketten i mitten av skärmen
        AnchorPane.setTopAnchor(welcomeLabel, 80.0);
        AnchorPane.setLeftAnchor(welcomeLabel, (stage.getWidth() - welcomeLabel.getWidth()) / 3);
        AnchorPane.setRightAnchor(welcomeLabel, (stage.getWidth() - welcomeLabel.getWidth()) / 3);


        // Ladda bilden
        Image backgroundImage = new Image(getClass().getResource("/images/bild1.png").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);

        // Anpassa bildens egenskaper
        backgroundView.setPreserveRatio(false);
        backgroundView.setSmooth(true);
        backgroundView.setFitWidth(stage.getWidth());
        backgroundView.setFitHeight(stage.getHeight());

        // Lägg till lyssnare för att hantera storleksändringar
        stage.widthProperty().addListener((obs, oldVal, newVal) -> backgroundView.setFitWidth((double) newVal));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> backgroundView.setFitHeight((double) newVal));

        // Lägg till bilden och labeln till AnchorPane
        mainMenu.getChildren().addAll(backgroundView, welcomeLabel);
        setView(mainMenu);
    }
    // Skapa AdminView
    public void showAdminView() {
        AnchorPane adminView = new AdminView(this).getView();
        setView(adminView);
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
