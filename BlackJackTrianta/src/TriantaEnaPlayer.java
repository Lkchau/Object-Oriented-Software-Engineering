import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TriantaEnaPlayer extends Player{

    private Money money;
    private Hand currentHand;
    private int currentHandIndex = 0;
    private String name;
    private Money initialMoney;
    private boolean doneWithTurn = false;
    private boolean naturalBlackJack = false;
    private Money bet;
    private HashMap<Hand, Money> handToBet = new HashMap<>();
    private Money initialBetAmount;
    private Hand hiddenCards;

    // No-args constructor, utilizes the player class's constructor
    public TriantaEnaPlayer(){
        super();
    }

    // Black jack player constructor
    public TriantaEnaPlayer(Money money, Hand hand, String name){
        super(money, hand, name);
        bet = new Money();
        setCurrentHand(hand);
    }

    // Black Jack Player constructor with the default initial money value of 0
    public TriantaEnaPlayer(Hand hand, String name){
        this(new Money(0), hand, name);
    }

    // Black Jack Player constructor knowing only a player's name
    public TriantaEnaPlayer(String name){
        this(new Money(0), new Hand(), name);
    }

    // setter for natural blackjack
    public void setNaturalBlackJack(boolean naturalBlackJack) {
        this.naturalBlackJack = naturalBlackJack;
    }

    // getter for natural blackjack
    public boolean isNaturalBlackJack() {
        return naturalBlackJack;
    }

    // setter for initial bet amount
    public void setInitialBetAmount(Money initialBetAmount) {
        this.initialBetAmount = initialBetAmount;
    }

    // getter for initial bet amount
    public Money getInitialBetAmount() {
        return initialBetAmount;
    }


    // settter for initial money of the player
    @Override
    public void setInitialMoney(Money initialMoney) {
        this.initialMoney = initialMoney;
    }
    // getter for the player's initial money
    @Override
    public Money getInitialMoney() {
        return initialMoney;
    }

    // setter for handtobet
    public void setHandToBet(HashMap<Hand, Money> handToBet) {
        this.handToBet = handToBet;
    }

    // getter for the hashmap that maps the hand to the amount of money bet for the hand
    public HashMap<Hand, Money> getHandToBet() {
        return handToBet;
    }

    // setter for name
    @Override
    public void setName(String name) {
        this.name = name;
    }

    // getter for the player's name
    @Override
    public String getName() {
        return name;
    }

    // setter for money
    @Override
    public void setMoney(Money money) {
        this.money = money;
    }

    // getter for the amount of money the player currently has
    @Override
    public Money getMoney() {
        return money;
    }

    // setter for the current hand of the player
    @Override
    public void setCurrentHand(Hand hand) {
        this.currentHand = hand;
    }

    // getter for the current hand of the player
    @Override
    public Hand getCurrentHand() {
        return currentHand;
    }

    // setter for the bet
    @Override
    public void setBet(Money bet) {
        this.bet = bet;

    }

    // getter for bet
    @Override
    public Money getBet() {
        return bet;
    }

    // setter for if the user is done with their turn
    public void setDoneWithTurn(boolean doneWithTurn) {
        this.doneWithTurn = doneWithTurn;
    }

    // getter for if the user is done with their turn
    public boolean isDoneWithTurn() {
        return doneWithTurn;
    }

    // update the bet amount for the user
    public void updateBet(Money money, boolean beginning) {
        setInitialBetAmount(money);
        setBet(money);
        handToBet.put(currentHand, bet);
    }

    // check if the player has money left
    public boolean hasNoMoney(){
        return getMoney().getValue() <= 0;
    }

    // resets the player's bets
    public void resetBet(){
        setBet(new Money(money.getCurrency()));

    }

    // reset the player's hand
    @Override
    public void resetHand() {
        super.resetHand();
        resetCurrentHandIndex();
        handToBet.clear();
    }

    // Sets the hand to be done and if there is no hand left that can be played, ends the player's turn else moves onto the next hand
    public void setDoneWithHand(boolean doneWithHand){
        setDoneWithTurn(doneWithHand);
    }

    // Check and return if the player can still play their current hand
    public boolean isAbleToPlay(){
        return isAbleToPlayCurrentHand();
    }

    // check if the current hand can be played
    private boolean isAbleToPlayCurrentHand(){
        if(currentHand.getHandValue() == 21){
            System.out.println("\nYour hand value is already 21! Skipping turn for this hand.");
        }
        return currentHand.getHandValue() < 21;
    }

    // Maps the hand's value to the amount of money bet on the hand for use in blackjack to check and resolve winners/losers
    public HashMap<Integer, Money> getHandValuesAndBetValues(){
        HashMap<Integer, Money> handValuesToBetValues = new HashMap<>();
        handValuesToBetValues.put(currentHand.getHandValue(),handToBet.get(currentHand));
        return handValuesToBetValues;
    }

    // remove a specific card from the current hand
    public void removeCardFromCurrentHand(Card card){
        getCurrentHand().removeCard(card);
    }

    // checks if the user's turn is over or not
    public boolean wantsToPlay(){
        return !isDoneWithTurn();
    }

    // sets the bet for the current hand
    public void setBetForCurrentHand(Money money){
        handToBet.put(currentHand,money);
    }

    // reset the current hand index
    private void resetCurrentHandIndex(){
        currentHandIndex = 0;
    }

    // shows only the current hand of the player
    public String showCurrentHand(){
        return "Currently Playing with: " + getCurrentHand() + "\n";
    }

    // check for equality between two users
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TriantaEnaPlayer that = (TriantaEnaPlayer) o;
        return currentHandIndex == that.currentHandIndex &&
                doneWithTurn == that.doneWithTurn &&
                naturalBlackJack == that.naturalBlackJack &&
                Objects.equals(money, that.money) &&
                Objects.equals(currentHand, that.currentHand) &&
                Objects.equals(name, that.name) &&
                Objects.equals(initialMoney, that.initialMoney) &&
                Objects.equals(handToBet, that.handToBet) &&
                Objects.equals(initialBetAmount, that.initialBetAmount) &&
                Objects.equals(bet, that.bet);
    }

    // overrides the hashcode function
    @Override
    public int hashCode() {
        return Objects.hash(money, currentHand, currentHandIndex, name, initialMoney, doneWithTurn, naturalBlackJack, handToBet, initialBetAmount, bet);
    }


    public void showHandWithHiddenCard(){
        System.out.println(currentHand);
    }

    // overrides toString for use to display the player
    @Override
    public String toString() {
            StringBuilder playerString = new StringBuilder();
            playerString.append("Player " + getName()+ " has:\n");
            playerString.append(getMoney() + " left. With an initial betting pool of " + getInitialMoney() + "\n");
            playerString.append("Total Money gained/lost: " + getDifferenceInMoney() + "\n");
            playerString.append("You currently have the following hands:\n");
            for(Hand hand1 : getHandToBet().keySet()){
                playerString.append(hand1+"\n");
                playerString.append("Bet for hand: " + getHandToBet().get(hand1) + "\n");
            }

            return playerString.toString();
    }
}
