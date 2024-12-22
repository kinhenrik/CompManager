package com.comp.compmanager;

import com.comp.compmanager.View.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        ViewManager viewManager = new ViewManager(stage); // Skapa vyhanterare
        viewManager.start();
    }

    public static void main(String[] args) {
        launch();
    }
}