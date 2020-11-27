import java.util.Objects;

// Card class to represent a physical card to be used in a card game, in hindsight, there could be different kinds of cards, some without ranks and suits and I could have made another class
public class Card{
    private Suit suit;
    private Rank rank;

    // no-args constructor
    public Card(){
        setRank(new Rank());
        setSuit(new Suit());
    }

    // constructor for a card
    public Card(Suit suit, Rank rank){
        setRank(rank);
        setSuit(suit);
    }

    // setter for a card's suit
    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    // getter for a card's suit
    public Suit getSuit() {
        return suit;
    }

    // setter for a card's rank
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    // getter for a card's rank
    public Rank getRank() {
        return rank;
    }

    // check for equality between two cards
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(suit, card.suit) &&
                Objects.equals(rank, card.rank);
    }

    // toString to display a card
    public String toString(){
        StringBuilder cardString = new StringBuilder();
        cardString.append("| " + rank.toString() + " of " + suit.toString() + " |");
        return cardString.toString();
    }

}