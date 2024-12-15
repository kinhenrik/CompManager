package com.comp.compmanager.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ViewManager {
    private final Stage stage;
    private final AnchorPane root;
    private final MenuBar menuBar;
    private TeamView teamView;
    private boolean isAdmin = false;
    private boolean isGuest = true;

    public ViewManager(Stage stage) {
        this.stage = stage;
        this.root = new AnchorPane();
        this.menuBar = createMenuBar();
        this.menuBar.setDisable(true);
        initializeRoot();
    }

    private void initializeRoot() {
        // Lägg till menyfältet i root
        AnchorPane.setTopAnchor(menuBar, 0.0);
        AnchorPane.setLeftAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(menuBar, 0.0);

// Ladda bilden om man vill ha på alla skärmar/vy
//        Image backgroundImage = new Image(getClass().getResource("/images/bild1.png").toExternalForm());
//        ImageView backgroundView = new ImageView(backgroundImage);
//
//        // Anpassa bildens egenskaper
//        backgroundView.setPreserveRatio(false);
//        backgroundView.setSmooth(true);
//        backgroundView.setFitWidth(stage.getWidth());
//        backgroundView.setFitHeight(stage.getHeight());
//
//        // Lägg till lyssnare för att hantera storleksändringar
//        stage.widthProperty().addListener((obs, oldVal, newVal) -> backgroundView.setFitWidth((double) newVal));
//        stage.heightProperty().addListener((obs, oldVal, newVal) -> backgroundView.setFitHeight((double) newVal));
//
//      root.setBackground(new Background(backgroundView));
        root.getChildren().add(menuBar);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Select view");
        Menu userMenu = new Menu("Not logged in");

        // Skapa menyval
        MenuItem mainMenuItem = new MenuItem("Main");
        MenuItem adminMenuItem = new MenuItem("Admin View");
        MenuItem teamMenuItem = new MenuItem("Team View");
        MenuItem playerMenuItem = new MenuItem("Player View");
        MenuItem gamesMenuItem = new MenuItem("Games View");
        MenuItem logOutMenuItem = new MenuItem("Log Out");

        // Lägg till valen i menyn
        menu.getItems().addAll(mainMenuItem, adminMenuItem, teamMenuItem, playerMenuItem, gamesMenuItem);
        userMenu.getItems().add(logOutMenuItem);
        menuBar.getMenus().addAll(menu ,userMenu);

        // Koppla menyval till vyhanteraren , skapad av henrik
        mainMenuItem.setOnAction(e -> {
            menu.setText("Select view");
            showMainView();
            System.out.println("You selected the main menu");
        });
        adminMenuItem.setOnAction(e -> {
            menu.setText("Admin view");
            showAdminView();
            System.out.println("You selected the admin menu");
        });
        teamMenuItem.setOnAction(e -> {
            menu.setText("Team view");
            showTeamView();
            System.out.println("You selected the team menu");
        });
        playerMenuItem.setOnAction(e -> {
            menu.setText("Player view");
            showPlayerView();
            System.out.println("You selected the player menu");
        });
        gamesMenuItem.setOnAction(e -> {
            menu.setText("Games view");
            showGamesView();
            System.out.println("You selected the games menu");
        });

        //Hoppar tillbaka till mainView och disablar menyn när man loggar ut
            logOutMenuItem.setOnAction(e -> {
            showMainView();
            menuBar.setDisable(true);
            isAdmin = false;
            isGuest = false;
            userMenu.setText("Not logged in");
                System.out.println("not logged in");
            menu.setText("Select view");
        });

        return menuBar;
    }
    // Skapa Login-view , skapad av henrik
    private VBox createLoginView() {
        Button adminButton = new Button("Login as admin");
        Button guestButton = new Button("Continue as guest");

        adminButton.setPrefWidth(150);
        guestButton.setPrefWidth(150);

        adminButton.setOnAction(event -> {
            // Öppnar login-fönstret om man inte är admin
            LoginView loginView = new LoginView(this);
            if (!isAdmin) {
                loginView.createLoginWindow();
            }
            // Aktiverar knappar och visar vem man är inloggad som
            if (isAdmin) {
                menuBar.setDisable(false);
                isGuest = false;
                menuBar.getMenus().get(1).setText("Logged in as: " + loginView.getDropdownValue());
                menuBar.getMenus().get(0).setText("Select view");
                System.out.println("Logged in as : " + loginView.getDropdownValue());
            }
        });

        guestButton.setOnAction(event -> {
            menuBar.setDisable(false);
            isGuest = true;
            isAdmin = false;
            menuBar.getMenus().get(1).setText("Logged in as: Guest");
            menuBar.getMenus().get(0).setText("Select view");
            System.out.println("Logged in as: Guest");
        });

        VBox loginBox = new VBox(10);
        loginBox.getChildren().addAll(adminButton, guestButton);

        return loginBox;
    }

    // Skapa huvudmenyn
    public void showMainView() {
        AnchorPane mainMenu = new AnchorPane();
        VBox loginView = createLoginView();

        // Lägger till login-knapparna och positionerar
        loginView.setAlignment(Pos.BOTTOM_CENTER);
        loginView.setPadding(new Insets(10));
        AnchorPane.setTopAnchor(loginView, 0.0);
        AnchorPane.setBottomAnchor(loginView, 0.0);
        AnchorPane.setLeftAnchor(loginView, 0.0);
        AnchorPane.setRightAnchor(loginView, 0.0);

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
        mainMenu.getChildren().addAll(backgroundView,loginView, welcomeLabel);
        setView(mainMenu);
    }

    // Skapa AdminView
    public void showAdminView() {
        AnchorPane adminView = new AdminView(this).getView();
        setView(adminView);
    }

    // Skapa TeamView
    public void showTeamView() {
//        AnchorPane teamView = new TeamView(this).getView();
//        setView(teamView);
        if (teamView == null) {
            teamView = new TeamView(this);
        }
        setView(teamView.getView());
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
        AnchorPane.setBottomAnchor(newView, 0.0);
        AnchorPane.setLeftAnchor(newView, 0.0);
        AnchorPane.setRightAnchor(newView, 0.0);
        root.getChildren().add(newView);
    }

    public void showGamesView() {
        AnchorPane gameView = new GamesView(this).getView();
        setView(gameView);
    }

    // Starta scenen med huvudmenyn
    public void start() {

        Scene scene = new Scene(root, 850, 600);
        stage.setScene(scene);
        stage.setTitle("CompManager");
        stage.show();
        showMainView(); // Visa huvudmenyn först
    }

//Getters och setters om man är admin elr guest
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isGuest() {
        return isGuest;
    }
    public void setGuest(boolean guest) {
        isGuest = guest;
    }

}
