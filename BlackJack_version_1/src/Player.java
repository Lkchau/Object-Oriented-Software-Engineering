import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

// Player class to represent the concept of a player
public abstract class Player{
    private Money money;
    private Money initialMoney;
    private Hand currentHand;
    private String name;
    private Money bet;
    private ArrayList<Money> bets;
    private HashMap<Hand, Money> handToBet = new HashMap<>();
    private boolean doneWithTurn = false;
    private boolean naturalBlackJack = false;

    // checks if the player is able to play still
    public abstract boolean isAbleToPlay();

    // checks if the player still wants to play
    public abstract boolean wantsToPlay();

    // updates the bet for a player
    public abstract void updateBet(Money money, boolean beginning);

    // shows a player's current hand
    public abstract String showCurrentHand();

    // No-args constructor
    public Player(){
        setCurrentHand(new Hand());
        setMoney(new Money());
        setInitialMoney(new Money());
        setName("");
        setBet(new Money(0));
    }

    // Constructor for a player
    public Player(Money money, Hand hand, String name){
        setMoney(money);
        setCurrentHand(hand);
        setName(name);
        setInitialMoney(money);
        setBet(new Money(money.getCurrency(),0));
    }

    // setter for current hand
    public void setCurrentHand(Hand hand) {
        this.currentHand = hand;
    }

    // getter for the current hand
    public Hand getCurrentHand() {
        return currentHand;
    }

    // setter for player's money
    public void setMoney(Money money) {
        this.money = money;
    }

    // getter for player's money
    public Money getMoney() {
        return money;
    }

    // setter for a players name
    public void setName(String name) {
        this.name = name;
    }

    // getter for a player's name
    public String getName() {
        return name;
    }

    // setter for the initial money of a player
    public void setInitialMoney(Money initialMoney) {
        this.initialMoney = initialMoney;
    }

    // getter for the initial money of a player
    public Money getInitialMoney() {
        return initialMoney;
    }

    // setter for done with turn
    public void setDoneWithTurn(boolean doneWithTurn) {
        this.doneWithTurn = doneWithTurn;
    }

    // getter for if the player is done with their turn or not
    public boolean isDoneWithTurn() {
        return doneWithTurn;
    }

    // setter for the player's bet
    public void setBet(Money bet) {
        this.bet = bet;
    }

    // getter for the player's bet
    public Money getBet() {
        return bet;
    }

    // setter for natural black jack
    public void setNaturalBlackJack(boolean naturalBlackJack) {
        this.naturalBlackJack = naturalBlackJack;
    }

    // getter for if the player has natural blackjack or not
    public boolean isNaturalBlackJack() {
        return naturalBlackJack;
    }

    // resets a player's current hand
    public void resetHand(){
        setCurrentHand(new Hand());
    }

    // gets the difference in money between the player's money pool (how much they put in) versus how much they currently have, allow for subclasses to use this function!
    protected Money getDifferenceInMoney(){
        int initialValue = getInitialMoney().getValue();
        int currentValue = getMoney().getValue();
        int differenceValue = currentValue - initialValue;
        Money differenceInMoney = new Money(getMoney().getCurrency(),differenceValue);
        return differenceInMoney;
    }

    // check if a player has money or not
    public boolean hasNoMoney(){
        return getMoney().getValue() <= 0;
    }

    // check for equality between to players
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(money, player.money) &&
                Objects.equals(currentHand, player.currentHand) &&
                Objects.equals(name, player.name);
    }

    // To string method to represent a genetic player
    public String toString(){
        StringBuilder playerString = new StringBuilder();
        playerString.append("Player " + getName()+ " has:\n");
        playerString.append(getMoney() + " left. With an initial betting pool of " + getInitialMoney() + "\n");
        playerString.append("Total Money gained/lost: " + getDifferenceInMoney() + "\n");
        playerString.append(getCurrentHand() + "\n");
        playerString.append("Current bet amount: " + getBet());
        return playerString.toString();
    }
}