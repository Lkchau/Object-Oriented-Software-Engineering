import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
// Black Jack Player class to represent a player for the black jack game
public class BlackJackPlayer extends Player {
    private Money money;
    private ArrayList<Hand> hands;
    private Hand currentHand;
    private int currentHandIndex = 0;
    private String name;
    private Money initialMoney;
    private boolean doneWithTurn = false;
    private boolean naturalBlackJack = false;
    private ArrayList<Money> bets;
    private Money bet;
    private HashMap<Hand, Money> handToBet = new HashMap<>();
    private Money initialBetAmount;

    // No-args constructor, utilizes the player class's constructor
    public BlackJackPlayer(){
        super();
    }

    // Black jack player constructor
    public BlackJackPlayer(Money money, Hand hand, String name){
        super(money, hand, name);
        hands = new ArrayList<>();
        hands.add(hand);
        bets = new ArrayList<>();
        setCurrentHand(hand);
    }

    // Black Jack Player constructor with the default initial money value of 0
    public BlackJackPlayer(Hand hand, String name){
        this(new Money(0), hand, name);
    }

    // Black Jack Player constructor knowing only a player's name
    public BlackJackPlayer(String name){
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

    // setter for bets
    public void setBets(ArrayList<Money> bets) {
        this.bets = bets;
    }

    // getter for bets
    public ArrayList<Money> getBets() {
        return bets;
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

    // set the hands of the user
    public void setHands(ArrayList<Hand> hands) {
        this.hands = hands;
    }

    // getter for the player's hands
    public ArrayList<Hand> getHands() {
        return hands;
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
        bets = new ArrayList<>();
        bets.add(getBet());
        handToBet.put(hands.get(bets.indexOf(getBet())), bets.get(bets.indexOf(getBet())));
    }

    // check if the player has money left
    public boolean hasNoMoney(){
        return getMoney().getValue() <= 0;
    }

    // resets the player's bets
    public void resetBet(){
        setBet(new Money(money.getCurrency()));
        setBets(new ArrayList<>());
        bets.add(getBet());
    }

    // reset the player's hand
    @Override
    public void resetHand() {
        super.resetHand();
        setHands(new ArrayList<Hand>());
        hands.add(getCurrentHand());
        resetCurrentHandIndex();
        handToBet.clear();
    }

    // Sets the hand to be done and if there is no hand left that can be played, ends the player's turn else moves onto the next hand
    public void setDoneWithHand(boolean doneWithHand){
        if(currentHandIndex+1 >= hands.size()){
            setDoneWithTurn(true);
        }
        else{
            currentHandIndex+=1;
            currentHand = hands.get((currentHandIndex));
        }
    }

    // Check and return if the player can still play their current hand
    public boolean isAbleToPlay(){
        if(isAbleToPlayCurrentHand()) return true;
        while(currentHandIndex+1 < hands.size()){
            currentHandIndex+=1;
            currentHand = hands.get((currentHandIndex));
            if(isAbleToPlayCurrentHand()){
                return true;
            }
        }
        return false;

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
        for(Hand hand: hands){
            handValuesToBetValues.put(hand.getHandValue(),handToBet.get(hand));
        }
        return handValuesToBetValues;
    }

    // remove a specific card from the current hand
    public void removeCardFromCurrentHand(Card card){
        getCurrentHand().removeCard(card);
   }

    // adds a new hand with a specific card to the player's hands
    public void addHand(Card card){
        Hand newHand = new Hand(new ArrayList<>());
        newHand.updateHand(card);
        hands.add(newHand);
    }

    // adds a new hand to a player's hands
    public void addHand(Hand hand){
        hands.add(hand);
        bets.add(getInitialBetAmount());
        handToBet.put(hand, bet);
    }

    // checks if the user's turn is over or not
    public boolean wantsToPlay(){
        return !isDoneWithTurn();
    }

    // sets the bet for the current hand
    public void setBetForCurrentHand(Money money){
        bets.set(currentHandIndex, money);
        updateHandToBet();
    }

    // updates the hashmap and updates the hands to their bet values, adding any new hands/bets if necessary
    public void updateHandToBet(){
        for(int i = 0; i < hands.size();i++){
            handToBet.put(hands.get(i),bets.get(i));
        }
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
        BlackJackPlayer that = (BlackJackPlayer) o;
        return currentHandIndex == that.currentHandIndex &&
                doneWithTurn == that.doneWithTurn &&
                naturalBlackJack == that.naturalBlackJack &&
                Objects.equals(money, that.money) &&
                Objects.equals(hands, that.hands) &&
                Objects.equals(currentHand, that.currentHand) &&
                Objects.equals(name, that.name) &&
                Objects.equals(initialMoney, that.initialMoney) &&
                Objects.equals(bets, that.bets) &&
                Objects.equals(handToBet, that.handToBet) &&
                Objects.equals(initialBetAmount, that.initialBetAmount) &&
                Objects.equals(bet, that.bet);
    }

    // overrides the hashcode function
    @Override
    public int hashCode() {
        return Objects.hash(money, hands, currentHand, currentHandIndex, name, initialMoney, doneWithTurn, naturalBlackJack, bets, handToBet, initialBetAmount, bet);
    }

    // overrides toString for use to display the player
    @Override
    public String toString() {
        if(hands.size() == 0){
            return super.toString();
        }
        else{
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
}
