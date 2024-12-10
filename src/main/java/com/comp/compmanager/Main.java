package com.comp.compmanager;

import com.comp.compmanager.View.AdminView;
import com.comp.compmanager.View.TeamView;

import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        
        // Playerview rutan
        AdminView adminView = new AdminView();
        adminView.show(stage); // Visa spelare-vyn

        // Teamview rutan
        TeamView teamView = new TeamView();
        teamView.show(stage); // Visa TeamView

    }
    public static void main(String[] args) {
        launch();
    }

}
