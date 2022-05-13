import java.util.ArrayList;
import java.util.Collections;


public class Deck {
    private ArrayList<Card> deck;

    //method that creates a deck of 81 unique cards
    public ArrayList<Card> createDeck() {
        deck = new ArrayList<>(81);
        for (int i = 0; i < Card.PossibleColors.values().length; i++) {
            for (int j = 0; j < Card.PossibleShadings.values().length; j++) {
                for (int k = 0; k < Card.PossibleShapes.values().length; k++) {
                    for (int l = 0; l < Card.PossibleNumbers.values().length; l++) {
                        deck.add(new Card(Card.PossibleColors.values()[i],
                                Card.PossibleShadings.values()[j],
                                Card.PossibleShapes.values()[k],
                                Card.PossibleNumbers.values()[l]));
                    }
                }
            }
        }
        return deck;
    }

    //method that shuffles the deck
    public static ArrayList<Card> shuffle(ArrayList<Card> deck) {
        Collections.shuffle(deck);
        return deck;
    }
}
