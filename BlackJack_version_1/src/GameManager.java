import java.util.*;

// GameManager class manages everything related to the game and can be run by the app
class GameManager implements Runnable{

    private Deck deck = new Deck();
    private DeckManager deckManager = new DeckManager(deck);
    private ArrayList<Player> allPlayers = new ArrayList<Player>();
    private PlayerManager playerManager = new PlayerManager();
    private boolean quit = false;

    // Hardcoded hashmap to display the options of games that the user can play
    private HashMap<Integer, String> intToGame = new HashMap<Integer, String>(){{
        put(1,"Black Jack");
    }};

    // the run function that drives the whole game, utilized by the App's main function
    public void run() {
        addQuitListener();
        printIntro();
        deckManager.shuffle();
        while(!quit){
            CardGame game = null;
            while(game == null) {
                switch (promptGame()) {
                    case 1:
                        game = new BlackJack(deckManager, allPlayers, playerManager);
                        break;

                }
            }
            setUpAllPlayers(game);
            game.play();
            if(promptContinue()){
                break;
            }
            playerManager.addMoney();
            if( checkNoMoreMoneyForAllPlayers()){
                break;
            }
        }
    }

    // adds a thread to listen for when the application ends
    private void addQuitListener(){
        Runtime.getRuntime().addShutdownHook(new Quit());

    }

    // checks if all players have no more money left, if so quit the game
    private boolean checkNoMoreMoneyForAllPlayers(){
        if(playerManager.checkIfAllHaveNoMoney()){
            System.out.println("It seems like all the players have no money left and no one wants to add more money!");
            quit = true;
            return true;
        }
        return false;
    }

    // prompts the user if they want to continue playing
    private boolean promptContinue(){
        quit = promptDecisionYN("quit playing");
        return quit;
    }

    // prompt the user to decide yes or no for a specific prompt
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

    // set up all players for a certain card game
    private void setUpAllPlayers(CardGame game){
        playerManager.setUpPlayers(game);

        for (Player p : playerManager.getGamblers()) {
            if(!allPlayers.contains(p)) allPlayers.add(p);
        }
        allPlayers.retainAll(playerManager.getGamblers());
        if(!allPlayers.contains(playerManager.getDealer())) {
            allPlayers.add(playerManager.getDealer());
        }
    }

    // draws a card for a specific player
    private void playerDrawCard(Player player){
        deckManager.drawCard(player);
    }

    // print out the intro for the game
    private void printIntro(){
        System.out.println("Welcome to the table! Your dealer today will be the computer!\n");
    }

    // prompt the user for what game they would like to play
    private int promptGame(){
        int chosenOption = 0;
        System.out.println("What game would we like to play today?\n");
        do{
            try{
                for(Integer options: intToGame.keySet()){
                    System.out.println("("+ options + ") " + intToGame.get(options));
                }
                Scanner prompt = new Scanner(System.in);
                chosenOption = prompt.nextInt();
                if(!intToGame.containsKey(chosenOption)){
                    System.out.println("Hey, that game doesn't exist! Please try again.");
                }
            }
            catch (InputMismatchException err){
                System.err.println("Invalid input, please put in a number corresponding to an option");
                chosenOption = promptGame();
                break;
            }
            catch (NoSuchElementException err){
                System.err.println("Nothing was put in!");
                chosenOption = promptGame();
                break;
            }

        } while(!intToGame.containsKey(chosenOption));
        return chosenOption;
    }



}