package com.example.testing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Blackjack extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent parentRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml"))); //connect fxml file with root
        Scene scene = new Scene(parentRoot);
        String css = Objects.requireNonNull(this.getClass().getResource("game.css")).toExternalForm(); //connect css file to scene
        scene.getStylesheets().add(css);
        stage.setTitle("Hello!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}