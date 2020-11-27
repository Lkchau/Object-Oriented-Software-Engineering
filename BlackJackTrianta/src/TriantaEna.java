import java.util.*;

// BlackJack class representing the card game, black jack... The main logic to be able to play black jack
public class TriantaEna extends CardGame {
    // Each game has a deck, a deck manager players and a player manager
    DeckManager deckManager;
    Deck deck;
    ArrayList<Player> allPlayers;
    PlayerManager playerManager;
    /*
    ACTIONS:
            Hit: The Player receives another card,
            Stand: The Player ends and maintains the value of the current hand,
            Split:  Only available if the Player has two cards of the same kind.
                    The Player splits their hand into two separate hands, and must place a bet on the other hand equal to
                    their original bet. The Dealer gives a single new cards to each of these new hands. Each of these hands
                    is treated as their own separate value.,
            Double up: The Player doubles their bet, and takes only a single hit and immediately stands afterwards.]
     */
    // Two different hashmaps because a player is not always allowed to split
    HashMap<Integer, String> possibleActions = new HashMap<Integer, String>() {{
        put(1, "Hit");
        put(2, "Stand");
    }};

    // No-args constructor
    public TriantaEna() {
        setAllPlayers(new ArrayList<>());
        setDeckManager(new DeckManager());
        setPlayerManager(new PlayerManager());
        setDeck(deckManager.getDeck());
    }

    // Constructor for the black jack game
    public TriantaEna(DeckManager deckManager, ArrayList<Player> allPlayers, PlayerManager playerManager) {
        setPlayerManager(playerManager);
        setAllPlayers(allPlayers);
        setDeckManager(deckManager);
        setDeck(deckManager.getDeck());
    }

    // setter for all players
    public void setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }

    // getter for all players
    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    // setter for deck manager
    public void setDeckManager(DeckManager deckManager) {
        this.deckManager = deckManager;
    }

    // getter for deck manager
    public DeckManager getDeckManager() {
        return deckManager;
    }

    // setter for player manager
    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    // getter for player manager
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    // setter for deck
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    // getter for deck
    public Deck getDeck() {
        return deck;
    }

    // method to play a round of blackjack, implements the abstract method from CardGame
    public void play() {
        dealInitialHand();
        playGamblersTurn();
        playDealersTurn();
        gameOver();
    }

    // deals an initial hand to all the players
    private void dealInitialHand() {
        Dealer dealer = playerManager.getDealer();
        for (int i = 0; i < 2; i++) {
            for (Player player : allPlayers) {
                if (!player.hasNoMoney() && !player.equals(dealer)) drawCard(player);
                if (player.equals(dealer)) drawCard(player);
            }
        }

        // Check for natural blackjacks
        for (Player player : allPlayers) {
            if (player.getCurrentHand().getHandValue() == 21) {
                player.setNaturalBlackJack(true);
            }
        }
        // set the hidden card for dealer
        dealer.setHiddenCard(dealer.getCurrentHand().getCards().remove(0));

    }

    // Nicely displays the initial hands of each player
    private void showInitialHands() {
        System.out.println("Showing the initial status of everyone.");
        System.out.println(playerManager.showPlayersWithCurrentHand());
    }

    // Plays the turns for all the gamblers if a gambler has money left (they can have no money and still be at the table)
    private void playGamblersTurn() {
        playerManager.setBets();
        System.out.println("Only players with money left can play their turn!");
        showInitialHands();
        for (Player player : playerManager.getGamblers()) {
            if (!player.hasNoMoney()) playOnePlayerTurn((BlackJackPlayer) player);
            playerManager.changePlayer();
        }
        System.out.println("All player turns have ended.");
    }

    // Plays the dealer's turn
    private void playDealersTurn() {
        System.out.println("Starting dealer's turn");
        Dealer dealer = playerManager.getDealer();
        dealer.revealHiddenCard();
        System.out.println(dealer.showDealerWithCurrentHand());
        while (dealer.isAbleToPlay()) {
            drawCard(dealer);
            System.out.println(dealer.showDealerWithCurrentHand());
        }
    }

    // Plays a single player's turn
    private void playOnePlayerTurn(BlackJackPlayer player) {
        while ((player.isAbleToPlay() && player.wantsToPlay())) {
            System.out.println("Current turn for player " + player.getName());
            System.out.println(playerManager.showCurrentPlayerAndDealer());
            // Ask user for a choice
            switch (promptPlayerAction(player)) {
                case 1:
                    hitPlayer(player);
                    break;
                case 2:
                    standPlayer(player);
                    break;
                default:
                    break;
            }
        }
        player.setDoneWithTurn(false);
        System.out.println(player.getName() + "'s turn has ended, their results: \n" + playerManager.showCurrentPlayerAndDealer());
    }

    // Hits a player (gives the player a card)
    private void hitPlayer(BlackJackPlayer player) {
        drawCard(player);
    }

    // Does the same thing as the above hit, except it deals a specific card to the user... This is used for testing purposes only!
    private void hitPlayer(BlackJackPlayer player, Card card) {
        drawCard(player, card);
    }

    // Stand the player's hand
    private void standPlayer(BlackJackPlayer player) {
        player.setDoneWithHand(true);
    }

    // Doubles the bet for the player's current hand and stands the hand
    private void doubleUpPlayer(BlackJackPlayer player) {
        player.setBetForCurrentHand(new Money(player.getBet().getCurrency(), player.getBet().getValue() * 2));
        drawCard(player);
        standPlayer(player);
    }

    // Prompt the player for an action
    private int promptPlayerAction(Player player) {
        int chosenOption = 0;
        System.out.println(player.getName() + " please choose an action\n");
        do {
            try {
                HashMap<Integer, String> playerOptions;
                playerOptions = possibleActions;
                for (Integer options : playerOptions.keySet()) {
                    System.out.println("(" + options + ") " + playerOptions.get(options));
                }
                Scanner prompt = new Scanner(System.in);
                chosenOption = prompt.nextInt();
                if (!possibleActions.containsKey(chosenOption)) {
                    System.out.println("Hey, that option doesn't exist! Please try again.");
                }
            } catch (InputMismatchException err) {
                System.err.println("Invalid input, please put in a number corresponding to an option");
                chosenOption = promptPlayerAction(player);
                break;
            } catch (NoSuchElementException err) {
                System.err.println("Nothing was put in!");
                chosenOption = promptPlayerAction(player);
                break;
            }

        } while (!possibleActions.containsKey(chosenOption));
        return chosenOption;
    }

    // Logic for when the game ends, main responsibility is to calculate the scores at the end and decide who wins
    @Override
    public void gameOver() {
        Dealer dealer = playerManager.getDealer();
        int dealerHandValue = dealer.getCurrentHand().getHandValue();
        boolean dealerBust = dealerHandValue > 21;
        for (Player player : playerManager.getGamblers()) {
            BlackJackPlayer BJplayer = (BlackJackPlayer) player;
            HashMap<Integer, Money> handValueToBet = ((BlackJackPlayer) player).getHandValuesAndBetValues();
            for (Integer handValue : handValueToBet.keySet()) {
                int currPlayerHandValue = handValue;
                Money currBet = handValueToBet.get(handValue);
                boolean playerBust = currPlayerHandValue > 21;
                if (dealerBust && playerBust) {
                    playerManager.tie(BJplayer);
                } else if ((!playerBust) && (!dealerBust) && (currPlayerHandValue == dealerHandValue)) {
                    if (currPlayerHandValue == 21) {
                        if (BJplayer.isNaturalBlackJack() && dealer.isNaturalBlackJack()) {
                            playerManager.tie(BJplayer);
                        } else if (BJplayer.isNaturalBlackJack()) {
                            playerManager.dealerLoss(BJplayer, currBet);
                        } else {
                            playerManager.playerLoss(BJplayer, currBet);
                        }
                    } else {
                        playerManager.tie(BJplayer);

                    }
                } else if (playerBust) {
                    playerManager.playerLoss(BJplayer, currBet);
                } else if (dealerBust) {
                    playerManager.dealerLoss(BJplayer, currBet);
                } else {
                    if (dealerHandValue > currPlayerHandValue) {
                        playerManager.playerLoss(BJplayer, currBet);
                    } else {
                        playerManager.dealerLoss(BJplayer, currBet);
                    }
                }

            }

        }
        System.out.println("This round has ended, printing current statuses...\n" + playerManager);
        playerManager.resetHands();
    }

    // draw a random card and return it
    private Card drawCard() {
        return deckManager.drawCard();
    }

    // draw a random card and give it to a specific user
    @Override
    public void drawCard(Player player) {
        deckManager.drawCard(player);
    }

    // draw a specific card and give it to a specific user [USED FOR TESTING ONLY]
    private void drawCard(Player player, Card card) {
        deckManager.drawCard(player, card);
    }
}

