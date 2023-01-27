package com.example.testing;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Initialize {
    private String harts = "Hearts";
    private String diamonds = "Diamonds";
    private String spades = "Spades";
    private String clubs = "Clubs";

    public Card[] generateDeck(){
        Card[] deck = new Card[312];
        int colorCount = 1;
        String color = null, value, name = null, imagePath;
        for(int count = 0; count < 6; count++) {
            for (int cardNumber = 0; cardNumber < 52; cardNumber++) {
                switch (colorCount) {
                    case 1:
                        color = harts;
                        break;
                    case 2:
                        color = diamonds;
                        break;
                    case 3:
                        color = spades;
                        break;
                    case 4:
                        color = clubs;
                        break;
                }
                colorCount++;
                if (colorCount == 5) {
                    colorCount = 1;
                }
                if (cardNumber / 4 >= 10) {
                    value = String.valueOf(10);
                    switch (cardNumber / 4) {
                        case 10:
                            name = "Jack of " + color;
                            break;
                        case 11:
                            name = "Queen of " + color;
                            break;
                        case 12:
                            name = "King of " + color;
                            break;
                    }
                } else {
                    value = String.valueOf((cardNumber / 4) + 1);
                    if ((cardNumber / 4) < 1) {
                        name = "Ace of " + color;
                    } else {
                        name = value + " of " + color;
                    }
                }
                imagePath = "com/example/testing/Decks/Standard/" + name.replace(" ", "_").toLowerCase() + ".png";
                deck[cardNumber + (count * 52)] = new Card(name, color, value, imagePath, "test2");
            }
        }
        return deck;
    }

    public static void main(String[] args) {

        /*Card[] deck = .generateDeck();
        List<Card> cardList = Arrays.asList(deck);
        Collections.shuffle(cardList);
        deck = cardList.toArray(new Card[0]);
        for(int i = 0; i < deck.length; i++){
            Card card = deck[i];
            System.out.println(card.IMAGEPATH);
        }*/
    }
}
