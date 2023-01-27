package com.example.testing;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    ImageView test;
    private double startDragX;
    private double startDragY;
    private boolean draging = false, starts = true;

    public void dragStart() {
    }

    public void dragEnd() {
        test.setX(test.getX());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Initialize init = new Initialize();
        Card[] deck = init.generateDeck();
        Random rand = new Random();
        int random = rand.nextInt(312);
        Card card = deck[random];
        Image image = new Image(card.IMAGEPATH);
        test.setImage(image);
        test.setOnMousePressed(event -> {
            startDragX = (event.getSceneX() - test.getX());
            startDragY = (event.getSceneY() - test.getY());
        });
        test.setOnMouseDragged(event -> {
            test.setX(event.getSceneX() - startDragX);
            test.setY(event.getSceneY() - startDragY);
        });
    }
}