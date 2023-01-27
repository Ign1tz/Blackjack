package com.example.testing;

public class Initialize {

    public Card[] generateDeck(){
        Card[] deck = new Card[312];
        int colorCount = 1;
        String color = null, value, name = null, imagePath;
        for(int count = 0; count < 6; count++) {
            for (int cardNumber = 0; cardNumber < 52; cardNumber++) {
                switch (colorCount) {
                    case 1 -> color = "Hearts";
                    case 2 -> color = "Diamonds";
                    case 3 -> color = "Spades";
                    case 4 -> color = "Clubs";
                }
                colorCount++;
                if (colorCount == 5) {
                    colorCount = 1;
                }
                if (cardNumber / 4 >= 10) {
                    value = String.valueOf(10);
                    switch (cardNumber / 4) {
                        case 10 -> name = "Jack of " + color;
                        case 11 -> name = "Queen of " + color;
                        case 12 -> name = "King of " + color;
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
    }
}
