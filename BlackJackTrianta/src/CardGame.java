// Abstract class representing a card game that is playable
public abstract class CardGame implements Playable{

    private PlayerManager playerManager;
    private Deck deck;
    private DeckManager deckManager;

    // all card games can be played
    public abstract void play();

    // all card games can have a player draw a card from the deck
    public abstract void drawCard(Player player);

    // all card games can end
    public abstract void gameOver();

    // setter for player manager
    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    // getter for player manager
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    // setter for deck manager
    public void setDeckManager(DeckManager deckManager) {
        this.deckManager = deckManager;
    }

    // getter for deck manager
    public DeckManager getDeckManager() {
        return deckManager;
    }

    // setter for deck
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    // getter for deck
    public Deck getDeck() {
        return deck;
    }
}