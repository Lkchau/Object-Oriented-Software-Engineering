import java.util.ArrayList;
import java.util.Objects;

// Hand class to represent a player's hand
public class Hand{

    private ArrayList<Card> cards;
    private int numCards;
    private int handValue;

    // No-args constructor
    public Hand(){
        setCards(new ArrayList<>());
        setNumCards(cards.size());
        setHandValue(calculateHandValue());
    }

    // Constructor for a hand
    public Hand(ArrayList<Card> cards){
        setCards(cards);
        setNumCards(cards.size());
        setHandValue(calculateHandValue());
    }

    // setter for cards in the hand
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
        setNumCards(cards.size());
    }

    // getter for cards in the hand
    public ArrayList<Card> getCards() {
        return cards;
    }

    // setter for number of cards in the hand
    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    // getter for number of cards in the hand
    public int getNumCards() {
        return numCards;
    }

    // setter for the hand value
    public void setHandValue(int handValue) {
        this.handValue = handValue;
    }

    // getter for the hand value
    public int getHandValue() {
        return handValue;
    }

    // updates the hand
    public Hand updateHand(Card cardToAdd){
        cards.add(cardToAdd);
        numCards = cards.size();
        return new Hand(cards);
    }

    // removes a certain card from the hand
    public void removeCard(Card cardToRemove){
        cards.remove(cardToRemove);
        numCards = cards.size();
    }

    // adds a certain cards to the hand
    public void addCards(Card[] cards){
        for(Card card: cards){
            updateHand(card);
        }
    }

    // calculates the hand's value and sets it
    private int calculateHandValue(){
        int handValue = 0;
        for(Card card: cards){
           int[] possibleValue = card.getRank().getValue();
           if(possibleValue.length > 1){
               if(handValue + possibleValue[1] <= 21){
                   handValue+=possibleValue[1];
               }
               else{
                   handValue+=possibleValue[0];
               }
           }
           else{
               handValue+=possibleValue[0];
           }
        }
        setHandValue(handValue);
        return handValue;
    }

    // checks for equality between two hands
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return numCards == hand.numCards &&
                handValue == hand.handValue &&
                Objects.equals(cards, hand.cards);
    }

    // To string to display a hand nicely
    @Override
    public String toString(){
        StringBuilder handString = new StringBuilder();
        handString.append("\n");
        for(Card card: cards){
            handString.append(card.toString() + "\n" );
        }
        handString.append("Value of hand: " + calculateHandValue() + "\n");

        return handString.toString();
    }
}