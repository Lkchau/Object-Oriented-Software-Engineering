import java.util.ArrayList;

// Deck class to represent a physical deck of cards
public class Deck{

    // Think of index 0 as top of deck
    private ArrayList<Card> deck;
    private int numCards;
    private final int initialDeckSize = 52;

    // no-args constructor for deck
    public Deck(){
        initializeDeck();
        setNumCards(deck.size());
    }

    // Constructor for deck with a known number for cards and a specific set of cards
    public Deck(ArrayList<Card> deck, int numCards){
        setDeck(deck);
        setNumCards(numCards);

    }

    // Constructor for deck with a specific set of cards
    public Deck(ArrayList<Card> deck){
        this(deck,deck.size());
    }

    // setter for the deck
    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    // getter for the deck
    public ArrayList<Card> getDeck() {
        return deck;
    }

    // setter for the number of cards in the deck
    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    // getter for the number of cards in the deck
    public int getNumCards() {
        return numCards;
    }

    // getter for the initial deck size
    public int getInitialDeckSize(){
        return initialDeckSize;
    }

    // initialize the deck of 52, this creates a deck that specifically has cards of that have rank and suit
    public void initializeDeck(){
        deck = new ArrayList<>();
        for(Rank.RANK rank: Rank.RANK.values()){
            for(Suit.SUIT suit: Suit.SUIT.values()){
                Rank currRank = new Rank(rank);
                Suit currSuit = new Suit(suit);
                Card currCard = new Card(currSuit, currRank);
                deck.add(currCard);
            }
        }
        numCards = deck.size();
    }


    // To string to show the contents of the deck
    public String toString(){
        StringBuilder deckBuilder = new StringBuilder();
        deckBuilder.append("\nCurrent Deck: \n");
        for(Card card: deck){
            deckBuilder.append(card.toString() + "\n");
        }
        return deckBuilder.toString();
    }
}