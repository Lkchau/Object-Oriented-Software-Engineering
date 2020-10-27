import java.util.ArrayList;
import java.util.Random;

// Deck manager class to manage the contents of the deck and actions between the deck and players
public class DeckManager{

    private Deck deck;

    // no-args constructor
    public DeckManager(){
        setDeck(new Deck());
    }

    // Constructor for deck manager
    public DeckManager(Deck deck){
        setDeck(deck);

    }

    // setter for the deck
    public void setDeck(Deck deck) {
        this.deck = deck;
        shuffle();
    }

    // getter for the deck
    public Deck getDeck() {
        return deck;
    }

    // shuffles the deck
    public void shuffle(){
        Random rand = new Random();

        for(int i = 0; i < deck.getNumCards(); i++){
            int randomPosition = i + rand.nextInt(deck.getInitialDeckSize() - i);
            swapCard(randomPosition,i);
        }

    }

    // swaps the positions of two cards in the deck (only used for shuffling)
    private void swapCard(int position1, int position2){
        try{
            Card temp = retrieveDeck().get(position1);
            retrieveDeck().set(position1, retrieveDeck().get(position2));
            retrieveDeck().set(position2, temp);

        }
        catch(ArrayIndexOutOfBoundsException err){
            System.err.println("Incorrect position!");
        }
    }

    // draws a single card and returns it
    public Card drawCard(){
        checkResetDeck();
        return retrieveDeck().remove(0);
    }

    // draws a single card and gives it to a player
    public void drawCard(Player player){
        checkResetDeck();
        player.setCurrentHand(player.getCurrentHand().updateHand(retrieveDeck().get(0)));
        retrieveDeck().remove(0);
        deck.setNumCards(retrieveDeck().size());
    }

    // draws a specific card and gives it to a player, if the card is no longer in the deck, it will give them the top card
    public void drawCard(Player player, Card card){
        checkResetDeck();
        if(!retrieveDeck().contains(card)){
            player.setCurrentHand(player.getCurrentHand().updateHand(retrieveDeck().get(0)));
            retrieveDeck().remove(0);
        }
        else{
            player.setCurrentHand(player.getCurrentHand().updateHand(retrieveDeck().get(retrieveDeck().indexOf(card))));
            retrieveDeck().remove(retrieveDeck().indexOf(card));
        }

        deck.setNumCards(retrieveDeck().size());

    }

    // Retrieves the cards from the deck
    public ArrayList<Card> retrieveDeck(){
        return deck.getDeck();
    }

    // checks if the deck needs to be reset or not (if the deck is empty it makes a new one)
    public void checkResetDeck(){
        if(deck.getNumCards() == 0){
            System.out.println("Deck ran out of cards, using new deck...");
            setDeck(new Deck());
        }
    }

}