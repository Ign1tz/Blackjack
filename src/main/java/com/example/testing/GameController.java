package com.example.testing;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

import static java.lang.Thread.sleep;

public class GameController implements Initializable {
    @FXML
    ImageView test, temp, stack;
    @FXML
    AnchorPane playingField;
    @FXML
    Label points, money, yourBet, dealerPoints, deckSize;
    @FXML
    Button betButton;
    @FXML
    Slider pickMoney;
    @FXML
    TextField writeMoney;
    @FXML
    GridPane grid;
    private double startDragX;
    private double startDragY;
    private boolean first = true;
    Initialize init = new Initialize();
    private final List<Card> deck = init.generateDeck();
    private final List<Boolean> wasFlipped = new ArrayList<>();
    private int count = 0, countDealer = 0, valueWhole, dealerValueWhole, moveCard = 85;
    private int yourMoney = 1000;
    private double bet;
    private boolean alive = true;

    private void calcValue() {
        valueWhole = 0;
        if(!(value == null)){
            for (int i = 0; i <= value.size() - 1; i++){
                valueWhole += value.get(i);
            }
        }
    }

    private void calcValueDealer() {
        dealerValueWhole = 0;
        if(!(valueDealer == null)){
            for (int i = 0; i <= valueDealer.size() - 1; i++){
                dealerValueWhole += valueDealer.get(i);
            }
        }
    }
    private List<Integer> value = new ArrayList<>(), valueDealer = new ArrayList<>();
    private List<ImageView> playing = new ArrayList<>(), dealerPlaying = new ArrayList<>();
    private Image image = new Image("com/example/testing/test.png", 100, 140, true, true);
    public void createNewCard(){
        TranslateTransition translate = new TranslateTransition();
        ImageView card = new ImageView();
        card.setFitWidth(100);
        card.setFitHeight(140);
        if(count == 1){
            card.setX(30);
        }
        card.setImage(image);
        wasFlipped.add(false);
        playing.add(card);
        playing.get(count).addEventHandler(MouseEvent.ANY, event -> {
            drag(card);
            clicked(card);
            event.consume();
        });
        playingField.getChildren().add(card);
        translate.setNode(playing.get(count));
        translate.setDuration(Duration.millis(400));
        translate.setFromX(stack.getLayoutX() - playingField.getLayoutX());
        translate.setFromY(stack.getLayoutY() - playingField.getLayoutY());
        translate.setToX(0);
        translate.setToY(0);
        if(count == 0){
            translate.setOnFinished(event -> {
                dealerFirst();
            });
            translate.setDuration(Duration.millis(400));
        }else if(count == 1){
            translate.setOnFinished(event -> {
                dealerSecond();
            });
        }
        translate.play();
        count++;
        stack.setDisable(true);
    }

    public void clicked(ImageView test) {
        test.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown() && test.getImage() == image) {
                Random rand = new Random();
                int random = rand.nextInt(deck.size() - 1);
                Card card = deck.get(random);
                deck.remove(random);
                Image cardFace = new Image(card.IMAGEPATH);
                test.setImage(cardFace);
                value.add(Integer.valueOf(card.VALUE));
                calcValue();
                if(value.contains(11)){
                    if(valueWhole > 21){
                        value.remove(Integer.valueOf(11));
                        value.add(1);
                        calcValue();
                    }
                }
                if(valueWhole < 21) {
                    points.setText("Points: " + valueWhole);
                }else if(valueWhole == 21){
                    if(count == 2){
                        points.setText("Blackjack");
                        yourMoney += bet * 2;
                        stack.setDisable(true);
                    }else {
                        points.setText("Blackjack");
                        yourMoney += bet;
                        stack.setDisable(true);
                    }
                }
                else {
                    lost();
                }
            }else if(event.isSecondaryButtonDown()){
                test.toFront();
            }
            checkIfAllCardsFlipped();
            deckSize.setText(String.valueOf(deck.size()));
        });
    }
    private void lost(){
        points.setText("You lost");
        yourMoney -= bet;
        stack.setDisable(true);
        alive = false;
        money.setText("You have: " + yourMoney);
    }
    private void drag(ImageView test) {
        test.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                playingField.setStyle("-fx-border-color: #000000");
                while (first) {
                    startDragX = (event.getSceneX() - test.getX());
                    startDragY = (event.getSceneY() - test.getY());
                    first = false;
                }
                if (event.getSceneX() - startDragX > 0 && event.getSceneX() - startDragX < playingField.getWidth() - test.getFitWidth()) {
                    test.setX(event.getSceneX() - startDragX);
                }
                if (event.getSceneY() - startDragY > 0 && event.getSceneY() - startDragY < playingField.getHeight() - test.getFitHeight()) {
                    test.setY(event.getSceneY() - startDragY);
                }
                test.toFront();
            }
        });
        test.setOnMouseReleased(event -> {
            playingField.setStyle("-fx-border-color: #063a26");
            first = true;
        });

    }
    public void setMoney(){
        pickMoney.setValue(Double.parseDouble(writeMoney.getText()));
    }
    public void placeBet() {
        stack.setDisable(false);
        pickMoney.setDisable(true);
        betButton.setDisable(true);
        writeMoney.setDisable(true);
        bet = (int) Double.parseDouble(writeMoney.getText());
        yourBet.setText("Your bet: " + (int) bet);
        startGame();
    }
    public void startNewRound(){
        pickMoney.setDisable(false);
        betButton.setDisable(false);
        writeMoney.setDisable(false);
        playing.clear();
        if(dealerPlaying.size() > 2) {
            for (int i = 2; i < dealerPlaying.size(); i++) {
                grid.getChildren().remove(dealerPlaying.get(i));
            }
        }
        test.setOpacity(0);
        temp.setOpacity(0);
        System.out.println(test.getX());
        dealerPlaying.clear();
        playingField.getChildren().remove(0,playingField.getChildren().size());
        count = 0;
        countDealer = 0;
        value.clear();
        valueDealer.clear();
        valueWhole = 0;
        dealerValueWhole = 0;
        points.setText("Your Points: ");
        dealerPoints.setText("Dealers Points: ");
        alive = true;
        pickMoney.setMax(yourMoney);
        moveCard = 85;
    }

    private void startGame(){
        stack.setDisable(true);
        createNewCard();
    }
    private void dealerFirst(){
        System.out.println(test.getX());
        test.setOpacity(1);
        TranslateTransition translate = new TranslateTransition();
        Random rand = new Random();
        int random = rand.nextInt(deck.size() - 1);
        Card card = deck.get(random);
        deck.remove(random);
        Image cardFace = new Image(card.IMAGEPATH);
        test.setImage(cardFace);
        translate.setNode(test);
        translate.setFromX(stack.getLayoutX() - test.getLayoutX());
        translate.setFromY(stack.getLayoutY() - test.getLayoutY());
        translate.setToX(-500);
        translate.setToY(200);
        translate.setDuration(Duration.millis(500));
        translate.setOnFinished(event -> {
            deckSize.setText(String.valueOf(deck.size()));
            createNewCard();
        });
        translate.play();
        valueDealer.add(Integer.valueOf(card.VALUE));
    }
    private void dealerSecond(){
        temp.setOpacity(1);
        TranslateTransition translate = new TranslateTransition();
        temp.setImage(image);
        translate.setNode(temp);
        translate.setFromX(stack.getLayoutX() - test.getLayoutX());
        translate.setFromY(stack.getLayoutY() - test.getLayoutY());
        translate.setToX(-395);
        translate.setToY(200);
        translate.setDuration(Duration.millis(500));
        translate.play();
    }
    public void stay(){
        if(checkIfAllCardsFlipped() && alive && count > 1){
            alive = false;
            stack.setDisable(true);
            Random rand = new Random();
            int random = rand.nextInt(deck.size() - 1);
            Card card = deck.get(random);
            deck.remove(random);
            Image cardFace = new Image(card.IMAGEPATH);
            temp.setImage(cardFace);
            aceCheck(card);
            dealerPoints.setText("Dealers Points: " + dealerValueWhole);
            dealerPlaying.add(test);
            dealerPlaying.add(temp);
            countDealer = 2;
            deckSize.setText(String.valueOf(deck.size()));
            checkIfWon();
        }
    }
    private void checkIfWon(){
        TranslateTransition translate = new TranslateTransition();
        dealerPoints.setText("Dealers Points: " + dealerValueWhole);
        if(dealerValueWhole < 22){
            if(dealerValueWhole <= 17){
                translate.setNode(dealerPlaying.get(countDealer - 1));
                translate.setByX(-85);
                translate.setOnFinished(event -> newDealerCard());
                translate.play();
                moveCard -= 25;
            }else if(valueWhole >= dealerValueWhole){
                yourMoney += bet;
                money.setText("You have: " + yourMoney);
            }else if(valueWhole < dealerValueWhole){
                lost();
            }
        }else{
            yourMoney += bet;
            money.setText("You have: " + yourMoney);
        }
    }


    private boolean checkIfAllCardsFlipped(){
        boolean allFlipped = true;
        for(int i = 0; i < playing.size(); i++){
            if(playing.get(i).getImage() == image){
                allFlipped = false;
            }
        }
        if(allFlipped && alive){
            stack.setDisable(false);
        }
        return allFlipped;
    }
    private void newDealerCard(){
        TranslateTransition translate = new TranslateTransition();
        Random rand = new Random();
        int random = rand.nextInt(deck.size() - 1);
        Card card = deck.get(random);
        deck.remove(random);
        Image cardFace = new Image(card.IMAGEPATH);
        ImageView cardImage = new ImageView(cardFace);
        cardImage.setFitWidth(100);
        cardImage.setFitHeight(140);
        dealerPlaying.add(cardImage);
        grid.getChildren().add(dealerPlaying.get(countDealer));
        translate.setNode(dealerPlaying.get(countDealer));
        translate.setDuration(Duration.millis(400));
        translate.setFromX(stack.getLayoutX() - dealerPlaying.get(countDealer).getLayoutX());
        translate.setFromY(stack.getLayoutY() - dealerPlaying.get(countDealer).getLayoutY());
        translate.setByX(-395 + (85 - moveCard));
        translate.setToY(74);
        translate.play();
        dealerPlaying.get(countDealer).toFront();
        dealerPlaying.get(countDealer).setX(100);
        countDealer++;
        aceCheck(card);
        translate.setOnFinished(event -> checkIfWon());
    }

    private void aceCheck(Card card) {
        valueDealer.add(Integer.valueOf(card.VALUE));
        calcValueDealer();
        if(valueDealer.contains(11)){
            if(dealerValueWhole > 21){
                valueDealer.remove(Integer.valueOf(11));
                valueDealer.add(1);
                calcValueDealer();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deckSize.setText(String.valueOf(deck.size()));
        money.setText("You have: " + yourMoney);
        pickMoney.setMax(yourMoney);
        pickMoney.setMin(5);
        pickMoney.setValue(25);
        writeMoney.setText(String.valueOf((int) pickMoney.getValue()));
        pickMoney.setValue(Double.parseDouble(writeMoney.getText()));
        pickMoney.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                writeMoney.setText(String.valueOf((int) pickMoney.getValue()));
            }
        });
    }
}