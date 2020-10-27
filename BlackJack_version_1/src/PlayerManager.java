import java.util.*;

// PlayerManager class to manage the all the players
class PlayerManager{
    private ArrayList<Player> gamblers;
    private Player currentGambler;
    private Dealer dealer;
    private MoneyManager mm;
    private boolean doneWithSetUp = false;
    private int index = 0;

    // hashmap to show the user how they can edit the players
    private final HashMap<Integer,String> editTeamOptions = new HashMap<Integer, String>(){{
        put(1,"Done");
        put(2,"Add player");
        put(3,"Remove player");
    }};

    // No-args constructor with a CPU as default dealer
    public PlayerManager(){
        setDealer(new Dealer(true));
        setGamblers(new ArrayList<Player>());
        setCurrentGambler(null);
    }

    // setter for gamblers
    public void setGamblers(ArrayList<Player> gamblers) {
        this.gamblers = gamblers;
    }

    // getter for gamblers
    public ArrayList<Player> getGamblers()
    {
        return gamblers;
    }


    // setter for the dealer
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    // getter for the dealer
    public Dealer getDealer() {
        return dealer;
    }

    // setter for the current gambler
    public void setCurrentGambler(Player currentGambler) {
        this.currentGambler = currentGambler;
    }

    // getter for the current gambler
    public Player getCurrentGambler() {
        return currentGambler;
    }

    // set up all the players in a specific card game, continue until there is atleast 1 player and the user is done configuring players
    public void setUpPlayers(CardGame game){
        do{

            switch(promptDecideTeamOptions()){
                case 1: doneWithSetUp = true;
                        if(gamblers == null || gamblers.size() <= 0){
                            System.out.println("You cannot play with no players!\n");
                            doneWithSetUp = false;
                            break;
                        }
                        setCurrentGambler(gamblers.get(0));
                        ArrayList<Player> allPlayers = new ArrayList<>();
                        allPlayers.add(dealer);
                        for(Player p: gamblers){
                            allPlayers.add(p);
                        }
                        mm = new MoneyManager(allPlayers, dealer, gamblers);
                        break;

                case 2: doneWithSetUp = false;
                        addPlayer(game,true);
                        break;
                case 3: doneWithSetUp = false;
                        removePlayer();
                        break;
                default: break;
            }

        }
        while(!doneWithSetUp || gamblers == null || gamblers.size() <= 0);

    }

    // update the money for a player who lost and a player who won
    private void updateMoney(Player playerLost, Player playerWin, Money amount){
        mm.exchangeMoney(playerLost, playerWin, amount);
    }

    // prompt the user for a bet, if a player has no money left, their default bet will be 0! They can't bet again until they add more money
    private int promptBet(Player player){
        if(player.hasNoMoney()){
            return 0;
        }
        return mm.promptBetAmount(player);
    }

    // Resets the current gambler
    private void resetCurrentGambler(){
        index = 0;
        setCurrentGambler(gamblers.get(index));
    }

    // changes the player's turn
    public void changePlayer(){
        index++;
        if(index >= gamblers.size()){
            index = 0;
        }
        currentGambler = gamblers.get(index);
    }

    // set the bets for each gambler
    public void setBets(){
        for(Player player : getGamblers()){
            player.updateBet(new Money(player.getMoney().getCurrency(), promptBet(player)), true);
        }
    }

    // executes the logic for a tie between player and dealer (resets the player bet and no one loses money)
    public void tie(BlackJackPlayer player){
        player.resetBet();
    }

    // executes the logic for a player loss, takes the amount a player bet and gives it to the dealer
    public void playerLoss(BlackJackPlayer player, Money bet){
        Dealer dealer = getDealer();
        updateMoney(player, dealer, bet);
        player.resetBet();
    }

    // executes the logic for a dealer loss, takes the amount a player bet from the dealer and gives it to the player
    public void dealerLoss(BlackJackPlayer player, Money bet){
        Dealer dealer = getDealer();
        updateMoney(dealer, player, bet);
        player.resetBet();
    }

    // updates the gamblers
    private void updatePlayer(Player player){
        for (Player p : gamblers) {
            System.out.println(p.equals(player));
            if (p.equals(player)) {
                p.setMoney(player.getMoney());
                p.setCurrentHand(player.getCurrentHand());
            }
        }
    }

    // resets a player's hand
    private void resetHand(Player player){
        player.resetHand();
    }

    // reset the hands of all the players (including a dealer)
    public void resetHands(){
        for(Player player: gamblers){
            resetHand(player);
        }
        resetHand(dealer);
    }

    // Allows a player to add money to their money pool and forces them to have atleast $20 if they are doing so
    public void addMoney(){
        for(Player gambler: getGamblers()){
            int gamblerValue = gambler.getMoney().getValue();
            if( gamblerValue <= 0){
                if(promptAddMoney(gambler)){
                    mm.addMoneyPool(gambler, promptPlayerMoney(false, -gamblerValue + 20));
                }
            }
        }
    }

    // checks if all the gamblers have no money, if so we know to quit the game
    public boolean checkIfAllHaveNoMoney(){
        for (Player gambler: getGamblers()){
            if(gambler.getMoney().getValue() > 0){
                return false;
            }
        }
        return true;
    }

    // prompt a user to add money if they run out! Only happens between games
    private boolean promptAddMoney(Player player){
        System.out.println("It seems like " + player.getName() + " has no money left!");
        return promptDecisionYN("add money (please add enough for you have a positive amount of money and at least $20 to start to continue playing.)");
    }

    // Prompt a user with a decision of yes or no to a certain question
    private boolean promptDecisionYN(String promptInQuestion){
        System.out.println("Would you like to " + promptInQuestion + "? (y/n)");
        Scanner prompt = new Scanner(System.in);
        String answer = prompt.nextLine();
        while(!answer.equals("y") && !answer.equals("n")){
            System.out.println("Invalid input, please try again!");
            answer = prompt.nextLine();
        }

        return answer.equals("y");
    }

    // Prompt to decide how to setup the teams
    private int promptDecideTeamOptions(){
        int chosenOption = 0;
        do{
            try{
                System.out.println("Current Players: ");
                printCurrentPlayers();
                System.out.println("Please add a player or choose done with player setup! Your options are:\n");
                for(Integer options: editTeamOptions.keySet()){
                    System.out.println("("+ options + ") " + editTeamOptions.get(options));
                }
                Scanner prompt = new Scanner(System.in);
                chosenOption = prompt.nextInt();
            }
            catch (InputMismatchException err){
                System.err.println("Invalid input, please put in a number corresponding to an option");
                chosenOption = promptDecideTeamOptions();
                break;
            }
            catch (NoSuchElementException err){
                System.err.println("Nothing was put in!");
                chosenOption = promptDecideTeamOptions();
                break;
            }

        } while(!editTeamOptions.containsKey(chosenOption));
        return chosenOption;
    }

    // adds a player to the game, if more games are added, this will need to be edited to support players of different games
    private void addPlayer(CardGame game, boolean beginning){
        String newPlayerName = promptPlayerName();
        Money newPlayerMoney = promptPlayerMoney(beginning, 20);
        if(game.getClass().equals(BlackJack.class)) gamblers.add(new BlackJackPlayer(newPlayerMoney,new Hand(), newPlayerName));
        else System.out.println("The game is not supported at the moment.");
    }

    // prints the current players out for the user to see
    private void printCurrentPlayers(){
        for(int i = 0; i < gamblers.size(); i ++){
            System.out.println("(" + (i + 1) + ") " + gamblers.get(i).getName());
        }
    }

    // removes a player from the game
    private void removePlayer(){
        if(gamblers.size() == 0){
            System.out.println("There are no players to be removed currently!");
        }
        else{
            int playerToRemove;
            do{
                System.out.println("Players you can remove: \n");
                printCurrentPlayers();
                playerToRemove = (promptSpecificInt("number corresponding to the player to remove"));

            }while(playerToRemove <= 0 || playerToRemove > gamblers.size());
            gamblers.remove(playerToRemove-1);

        }
    }

    // prompts a player for their name
    private String promptPlayerName(){
        System.out.println("Please enter the name of this player:");
        String newUser;
        do{
            try{
                Scanner prompt = new Scanner(System.in);
                newUser = prompt.nextLine();
                newUser = newUser.replaceAll("\\s","");
                if(newUser.equals("")){
                    System.out.println("Blank names are not allowed.");
                }
            } catch (InputMismatchException err){
                System.err.println("Invalid input!");
                return promptPlayerName();
            }
            catch (NoSuchElementException err){
                System.err.println("Nothing was inputted");
                return promptPlayerName();
            }
        } while(newUser.equals(""));
        return newUser;
    }

    // shows the dealer with their current hand
    private String showDealerWithCurrentHand(){
        return dealer.showDealerWithCurrentHand();
    }

    // Prompts an amount of money for the user to add. Assume that the currency will always be USD for now...
    private Money promptPlayerMoney(boolean beginning, int minimum){
        if(beginning){
            System.out.println("Please enter the starting amount of money for this player.");
        }
        int amountOfMoney;
        do{
             amountOfMoney = promptSpecificInt("amount of money for this player. Minimum: $" + minimum);
        } while(amountOfMoney < minimum);
        return new Money(amountOfMoney);

    }

    // Prompt the user for an integer with a specific prompt
    private int promptSpecificInt(String prom){
        System.out.println("Please input a " + prom);
        int response = 0;
        do{
            try{
                Scanner prompt = new Scanner(System.in);
                response = prompt.nextInt();
                if(response <= 0){
                    System.out.println("Please input a number greater than 0");
                }

            } catch(InputMismatchException err){
                System.err.println("Invalid input, please input a " + prom);
                return promptSpecificInt(prom);
            } catch(NoSuchElementException err){
                System.err.println("Nothing was entered!");
                return promptSpecificInt(prom);
            }
        } while(response <= 0);
        return response;
    }

    // Shows the difference in money each player has (the amount they lost or won)
    private void showScores(){
        System.out.println("These are the current amounts of money for each user: ");
        for(Player player: gamblers){
            System.out.println(player.getDifferenceInMoney());
        }
        System.out.println(dealer.getDifferenceInMoney());
    }

    // Shows the current player and dealer
    public String showCurrentPlayerAndDealer(){
        StringBuilder playerCurrentAndDealerString = new StringBuilder();
        playerCurrentAndDealerString.append("===========================================\n");
        playerCurrentAndDealerString.append("Current player and dealer's values: \n");
        playerCurrentAndDealerString.append(currentGambler + "\n" + currentGambler.showCurrentHand());
        playerCurrentAndDealerString.append(dealer + "\n" + dealer.showCurrentHand());
        playerCurrentAndDealerString.append("===========================================\n");
        return playerCurrentAndDealerString.toString();
    }

    // Shows all the players with their current hands
    public String showPlayersWithCurrentHand(){
        StringBuilder playerManagerString = new StringBuilder();
        playerManagerString.append("===========================================\n");
        playerManagerString.append("Current Status of players:\n");
        for(Player player: gamblers){
            playerManagerString.append(player + "\n");
            playerManagerString.append(player.showCurrentHand());
        }
        playerManagerString.append(dealer +"\n");
        playerManagerString.append(dealer.showCurrentHand());
        playerManagerString.append("===========================================\n");
        return playerManagerString.toString();
    }

    // To string to represent a player manager
    @Override
    public String toString() {
        StringBuilder playerManagerString = new StringBuilder();
        playerManagerString.append("===========================================\n");
        playerManagerString.append("Current Status of players:\n");
        for(Player player: gamblers){
            playerManagerString.append(player + "\n");
        }
        playerManagerString.append(dealer +"\n");
        playerManagerString.append(dealer.getCurrentHand());
        playerManagerString.append("===========================================\n");
        return playerManagerString.toString();
    }
}