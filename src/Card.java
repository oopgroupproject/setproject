import java.util.ArrayList;
import java.util.List;

public class Card {

    public enum PossibleColors {RED, GREEN, PURPLE}
    public enum PossibleShadings {EMPTY, FILLED, STRIPED}
    public enum PossibleShapes {DIAMOND, WAVE, OVAL}
    public enum PossibleNumbers {ONE, TWO, THREE}

    private final PossibleColors color;
    private final PossibleShadings shading;
    private final PossibleShapes shape;
    private final PossibleNumbers number;


    public PossibleColors getColor() {
        return color;
    }

    public PossibleShadings getShading() {
        return shading;
    }

    public PossibleShapes getShape() {
        return shape;
    }

    public PossibleNumbers getNumber() {
        return number;
    }

    public Card(){
        this.color = PossibleColors.RED;
        this.shape = PossibleShapes.DIAMOND;
        this.shading = PossibleShadings.EMPTY;
        this.number = PossibleNumbers.ONE;
    }

    public Card(PossibleColors color, PossibleShadings shading,
                PossibleShapes shape, PossibleNumbers number) {
        this.color = color;
        this.shading = shading;
        this.shape = shape;
        this.number = number;
    }


    //method that compares 3 cards and checks whether they form a set
    public static boolean isSet(Card first, Card second, Card third) {
        if((first.color.ordinal() == second.color.ordinal() && first.color.ordinal() == third.color.ordinal() && second.color.ordinal()
                == third.color.ordinal() || (first.color.ordinal() != second.color.ordinal() && first.color.ordinal()
                != third.color.ordinal() && second.color.ordinal() != third.color.ordinal()
        ))){
            if((first.shading.ordinal() == second.shading.ordinal() && first.shading.ordinal() == third.shading.ordinal()
                    && second.shading.ordinal() == third.shading.ordinal() || (first.shading.ordinal() != second.shading.ordinal()
                    && first.shading.ordinal() != third.shading.ordinal() && second.shading.ordinal() != third.shading.ordinal()
            ))){
                if((first.shape.ordinal() == second.shape.ordinal() && first.shape.ordinal() == third.shape.ordinal()
                        && second.shape.ordinal() == third.shape.ordinal() || (first.shape.ordinal() != second.shape.ordinal()
                        && first.shape.ordinal() != third.shape.ordinal() && second.shape.ordinal() != third.shape.ordinal()
                ))){
                    if((first.number.ordinal() == second.number.ordinal() && first.number.ordinal() == third.number.ordinal()
                            && second.number.ordinal() == third.number.ordinal()
                            || (first.number.ordinal() != second.number.ordinal() && first.number.ordinal() != third.number.ordinal() && second.number.ordinal() != third.number.ordinal()
                    ))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //method to check if there is a set present in the cards on the board
   public static boolean isSetPresent(ArrayList<Card> cardsOnBoard){
        for(int firstCard = 0; firstCard <= cardsOnBoard.size() - 3;firstCard++) {
            for(int secondCard = firstCard + 1; secondCard <= cardsOnBoard.size() - 2; secondCard++) {
                for(int thirdCard = secondCard + 1; thirdCard <= cardsOnBoard.size() - 1; thirdCard++){
                    if(isSet(cardsOnBoard.get(firstCard), cardsOnBoard.get(secondCard), cardsOnBoard.get(thirdCard))){
                        return true;
                    }

                }

            }
        }
        return false;
   }

    @Override
    public String toString() {
        return color + "" + shading + "" + shape + "" + number + "";
    }

    public static ArrayList<ArrayList<Card>> getAllSets(List<Card> cards) {
        ArrayList<ArrayList<Card>> result = new ArrayList<>();
        if (cards == null) {
            return result;
        }
        for(int firstCard = 0; firstCard <= cards.size() - 3;firstCard++) {
            for (int secondCard = firstCard + 1; secondCard <= cards.size() - 2; secondCard++) {
                for (int thirdCard = secondCard + 1; thirdCard <= cards.size() - 1; thirdCard++) {
                    if (isSet(cards.get(firstCard), cards.get(secondCard), cards.get(thirdCard))) {
                        ArrayList<Card> setArray = new ArrayList<>();
                        setArray.add(cards.get(firstCard));
                        setArray.add(cards.get(secondCard));
                        setArray.add(cards.get(thirdCard));
                        result.add(setArray);
                    }
                }
            }
        }
        System.out.println("The sets are:");
        for(int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        return result;
    }

    boolean setExists(List<Card> cards) {
        return getAllSets(cards).size() > 0;
    }
}
