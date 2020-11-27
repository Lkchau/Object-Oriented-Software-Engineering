import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

// MoneyManager class to manage the money within the game, contains methods to exchange money between players
public class MoneyManager{
    private ArrayList<Player> players;
    private Dealer dealer;
    private ArrayList<Player> gamblers;

    // no-args constructor
    public MoneyManager(){
        setPlayers(new ArrayList<Player>());
    }

    // Constructor for a money manager
    public MoneyManager(ArrayList<Player> players, Dealer dealer, ArrayList<Player> gamblers){
        setPlayers(players);
        setDealer(dealer);
        setGamblers(gamblers);
    }

    // Constructor for a money manager with known players and dealer
    public MoneyManager(Dealer dealer, ArrayList<Player> gamblers){
        setDealer(dealer);
        setGamblers(gamblers);
        ArrayList<Player> allPlayer = new ArrayList<>();
        allPlayer.add(dealer);
        for(Player gambler: gamblers){
            allPlayer.add(gambler);
        }
        setPlayers(allPlayer);
    }

    // Constructor for a money manager with only known gamblers
    public MoneyManager(ArrayList<Player> gamblers){
        this(new Dealer(), gamblers);
    }

    // setter for players
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    // getter for the players
    public ArrayList<Player> getPlayers() {
        return players;
    }

    // setter for gamblers
    public void setGamblers(ArrayList<Player> gamblers) {
        this.gamblers = gamblers;
    }

    // getter for the gamblers
    public ArrayList<Player> getGamblers() {
        return gamblers;
    }

    // setter for dealer
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    // getter for dealer
    public Dealer getDealer() {
        return dealer;
    }

    // Exchanges money between a lower and a winner, FUTURE: eventually check for type of currency and convert it before adding!
    public void exchangeMoney(Player loser, Player winner, Money money){
        addMoney(winner, money);
        subtractMoney(loser, money);
    }

    // Adds or subtracts a certain amount of money from a player
    public void addOrSubtractMoney(int addOrSubtract, Player player, Money money){
        player.setMoney(new Money(money.getCurrency(),player.getMoney().getValue() + (addOrSubtract*money.getValue())));
    }

    // Adds money to a player
    public void addMoney(Player player, Money money){
        addOrSubtractMoney(1,player,money);
    }

    // Subtracts money from a player
    public void subtractMoney(Player player, Money money){
        addOrSubtractMoney(-1, player, money);
    }

    // Adds to the overall money pool of a player
    public void addMoneyPool(Player player, Money money){
        addOrSubtractMoney(1, player, money);
        player.setInitialMoney(new Money(player.getMoney().getCurrency(),
                player.getInitialMoney().getValue() + money.getValue()));
    }

    // Prompt the user for an amount to bet
    public int promptBetAmount(Player player){
        System.out.println(player.getName() + " please input an amount to bet: ");
        int response = 0;
        do{
            try{
                Scanner prompt = new Scanner(System.in);
                response = prompt.nextInt();
                if(response > player.getMoney().getValue()){
                    System.out.println("Please input a number less than or equal to " + player.getMoney());
                }
                if(response <= 0){
                    System.out.println("Please input a number greater than 0!");
                }

            } catch(InputMismatchException err){
                System.err.println("Invalid input, please input an amount to bet");
                return promptBetAmount(player);
            } catch(NoSuchElementException err){
                System.err.println("Nothing was entered!");
                return promptBetAmount(player);
            }
        } while((response > player.getMoney().getValue()) || (response <= 0));
        return response;

    }
}