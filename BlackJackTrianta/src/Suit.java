// Suit class to represent the suit of a card
public class Suit{
    // Hard coded suits
    public enum SUIT {SPADES, HEARTS, CLUBS, DIAMONDS};
    private SUIT suit;

    // No-args constructor
    public Suit(){
        setSuit(null);
    }

    // Constructor for a suit
    public Suit(SUIT suit){
        setSuit(suit);
    }

    // setter for a suit
    public void setSuit(SUIT value) {
        this.suit = value;
    }

    // getter for a suit
    public SUIT getSuit() {
        return suit;
    }

    // checks if a suit is a valid suit
    public boolean isValidSuit(SUIT suit) {
        for (Suit.SUIT e : Suit.SUIT.values()) {
            if(e.name().equals(suit)) { return true; }
        }
        return false;
    }

    // The following 4 functions just sets the suit to be a certain suit
    public void setSpadeValue(){
        setSuit(Suit.SUIT.SPADES);
    }
    public void setHeartValue(){
        setSuit(Suit.SUIT.HEARTS);
    }
    public void setClubValue(){
        setSuit(Suit.SUIT.CLUBS);
    }
    public void setDiamondValue(){
        setSuit(Suit.SUIT.DIAMONDS);
    }

    // checks equivalency knowing that the other object is a suit already
    public boolean equals(Suit suit){
        return this.suit.name().equals(suit.suit.name());
    }

    // checks equivalency between two objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Suit suit1 = (Suit) o;
        return suit.name().equals(suit1.suit.name());
    }

    // to string method to display a suit for the user
    public String toString(){
        return suit.name();
    }
}