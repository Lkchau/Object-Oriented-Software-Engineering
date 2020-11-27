import java.util.Objects;

// Dealer class to represent a physical dealer, who is a type of player in a game
public class BlackJackDealer extends Dealer{
    private boolean CPU = false;
    private Money money;
    private Hand currentHand;
    private Card hiddenCard;
    private Money initialMoney;
    private String name;
    private boolean naturalBlackJack = naturalHand;

    // no-args constructor
    public BlackJackDealer(){
        super();
        setNumToStopAt(17);
    }

    // constructor for a dealer
    public BlackJackDealer(Money money, Hand hand, String name){
        super(money, hand, name);
        if(hand.getCards().size()==0){
            setHiddenCard(new Card());
        }
        else{
            setHiddenCard(hand.getCards().get(0));
            hand.getCards().remove(0);
            hand.setCards(hand.getCards());
        }

    }

    // constructor for a CPU if it is true, otherwise constructs the no-args dealer
    public BlackJackDealer(boolean CPU){
        this();
        if(CPU){
            createCPU();
        }
    }

    // setter for natural black jack
    public void setNaturalBlackJack(boolean naturalBlackJack) {
        this.naturalBlackJack = naturalBlackJack;
    }

    // getter for natural black jack
    public boolean isNaturalBlackJack() {
        return naturalBlackJack;
    }

    // setter for CPU
    public void setCPU(boolean CPU) {
        this.CPU = CPU;
    }

    // setter for hidden card
    public void setHiddenCard(Card hiddenCard) {
        this.hiddenCard = hiddenCard;
    }

    // getter for hidden card
    public Card getHiddenCard() {
        return hiddenCard;
    }

    // setter for name
    @Override
    public void setName(String name) {
        this.name = name;
    }

    // getter for name
    @Override
    public String getName() {
        return name;
    }

    // setter for money
    @Override
    public void setMoney(Money money) {
        this.money = money;
    }

    // getter for money
    @Override
    public Money getMoney() {
        return money;
    }

    // setter for current hand
    @Override
    public void setCurrentHand(Hand hand) {
        this.currentHand = hand;
    }

    // getter for current hand
    @Override
    public Hand getCurrentHand() {
        return currentHand;
    }

    // setter for initial money
    @Override
    public void setInitialMoney(Money initialMoney) {
        this.initialMoney = initialMoney;
    }

    // getter for initial money
    @Override
    public Money getInitialMoney() {
        return initialMoney;
    }

    // creates a computer dealer
    public void createCPU(){
        setMoney(new Money(0));
        setCurrentHand(new Hand());
        setInitialMoney(new Money(0));
        setName("CPU");
    }

    // shows only the dealer's current hand
    public String showCurrentHand(){
        return "Current hand for dealer: " + getCurrentHand();
    }

    // shows the dealer and their current hand
    public String showDealerWithCurrentHand(){
        return toString() + "\n" + showCurrentHand();
    }

    // reveals the hidden card and adds it to the hand
    public void revealHiddenCard(){
        currentHand.updateHand(hiddenCard);
    }

    // update bet from player does not do anything in dealer because they cannot bet in the first place
    @Override
    public void updateBet(Money money, boolean beginning) {
        return;
    }
    public boolean isAbleToPlay(){
        return currentHand.getHandValue() < numToStopAt;
    }
    public boolean wantsToPlay(){
        return true;
    }

    // checks for equality between two dealer
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BlackJackDealer dealer = (BlackJackDealer) o;
        return CPU == dealer.CPU &&
                naturalBlackJack == dealer.naturalBlackJack &&
                Objects.equals(money, dealer.money) &&
                Objects.equals(currentHand, dealer.currentHand) &&
                Objects.equals(hiddenCard, dealer.hiddenCard) &&
                Objects.equals(initialMoney, dealer.initialMoney) &&
                Objects.equals(name, dealer.name);
    }

    // overrides hashcode
    @Override
    public int hashCode() {
        return Objects.hash(CPU, money, currentHand, hiddenCard, initialMoney, name, naturalBlackJack);
    }

    // to string to show the dealer's information
    public String toString(){
        StringBuilder playerString = new StringBuilder();
        playerString.append("Dealer " + name+ " has:\n");
        playerString.append(money + " left. With an initial money pool of " + initialMoney+"\n");
        playerString.append("Total Money gained/lost: " + getDifferenceInMoney());
//        playerString.append(currentHand);
        return playerString.toString();
    }




}