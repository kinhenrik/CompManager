package com.comp.compmanager.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewManager {
    private final Stage stage;
    private final AnchorPane root;
    private final MenuBar menuBar;
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
        root.getChildren().add(menuBar);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Select view");

        //userMenu som man kan logga ut med
        Menu userMenu = new Menu("Not logged in");

        // Skapa menyval
        MenuItem mainMenuItem = new MenuItem("Main");
        MenuItem adminMenuItem = new MenuItem("Admin View");
        MenuItem teamMenuItem = new MenuItem("Team View");
        MenuItem playerMenuItem = new MenuItem("Player View");
        //Menyval för userMenu
        MenuItem logoutMenuItem = new MenuItem("Logout");

        // Lägg till valen i menyn
        menu.getItems().addAll(mainMenuItem, adminMenuItem, teamMenuItem, playerMenuItem);
        userMenu.getItems().addAll(logoutMenuItem);
        menuBar.getMenus().addAll(menu, userMenu);

        // Koppla menyval till vyhanteraren och ändrar text beroende på view
        mainMenuItem.setOnAction(e -> {
            menu.setText("Select view");
            showMainView();
        });
        adminMenuItem.setOnAction(e -> {
            menu.setText("Admin view");
            showAdminView();
        });
        teamMenuItem.setOnAction(e -> {
            menu.setText("Team view");
            showTeamView();
        });
        playerMenuItem.setOnAction(e -> {
            menu.setText("Player view");
            showPlayerView();
        });
        //Hoppar tillbaka till mainView och disablar menyn när man loggar ut
        logoutMenuItem.setOnAction(e -> {
            showMainView();
            menuBar.setDisable(true);
            isAdmin = false;
            isGuest = false;
            userMenu.setText("Not logged in");
            menu.setText("Select view");
        });

        return menuBar;
    }

    // Skapa Login-view
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
            }
        });

        guestButton.setOnAction(event -> {
            menuBar.setDisable(false);
            isGuest = true;
            isAdmin = false;
            menuBar.getMenus().get(1).setText("Logged in as: Guest");
            menuBar.getMenus().get(0).setText("Select view");
        });

        VBox loginBox = new VBox(10);
        loginBox.getChildren().addAll(adminButton, guestButton);

        return loginBox;
    }

    // Skapa huvudmenyn
    public void showMainView() {
        AnchorPane mainMenu = new AnchorPane();
        VBox loginView = createLoginView();

        // Lägger till login-knapparna och centrerar dom
        loginView.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(loginView, 0.0);
        AnchorPane.setBottomAnchor(loginView, 0.0);
        AnchorPane.setLeftAnchor(loginView, 0.0);
        AnchorPane.setRightAnchor(loginView, 0.0);

        mainMenu.getChildren().add(loginView);
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
        AnchorPane.setBottomAnchor(newView, 0.0);
        AnchorPane.setLeftAnchor(newView, 0.0);
        AnchorPane.setRightAnchor(newView, 0.0);

        root.getChildren().add(newView);
    }

    // Starta scenen med huvudmenyn
    public void start() {
        Scene scene = new Scene(root, 850, 600);
        stage.setScene(scene);
        stage.setTitle("CompManager");
        stage.show();
        showMainView(); // Visa huvudmenyn först
    }

    //GETTERS & SETTERS FÖR OM MAN ÄR ADMIN ELLER GUEST
    public boolean isAdmin() {
        return isAdmin;
    } public void setAdmin(boolean admin) {
        isAdmin = admin; }

    public boolean isGuest() {
        return isGuest;
    } public void setGuest(boolean guest) {
        isGuest = guest; }
}
