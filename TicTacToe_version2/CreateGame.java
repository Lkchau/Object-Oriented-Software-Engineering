import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// Driver class for all board games

// Possible TODO: Add functionality so that the first role always goes first (in a way this will let the user choose which team goes first)
public class CreateGame{

    private int currGame;
    private ScoreBoard scores;
    private Map<Integer,String> possibleGames = new HashMap<>();
    private boolean quit = false;
    private boolean gameChange;
    private Team[] teams;
    private int boardSize;
    private int numTeams;
    private int totalPlayers;
    private int[] numPlayersOnEachTeam;
    private TeamManager teamManager;
    private boolean defaultGame = true;
    private boolean resetScores = false;
    private HashMap<String,String> possibleRoles;
    private final File config = getConfig();
    private final int maxBoardSize = 10;

    // Runs the whole game, to be used only in app.java
    public void run(){
        setupGame();
        playSession();
    }

    // Grabs the data for games from the config.properties file
    private File getConfig(){
        String path = new File("").getAbsolutePath();
        return new File(path + "\\config.properties");
    }

    // Sets up variables that are needed for the game
    private void setupGame(){
        setPossibleGames();
        decideCurrGame();
        promptDefault();
        createInitialGame();
        teamManager = new TeamManager(teams);
        scores = new ScoreBoard(teams);
    }

    // Decision on which game to play, in the future if more games would be added this would need to be updated
    private void gameToPlay(){
        BoardGame game = null;
        checkResetScore();
        if(currGame == 1)
        {
            game = new TicTacToe(teams, scores, boardSize, teamManager);
        }
        if(currGame == 2){
            game = new OrderAndChaos(teams, scores, boardSize, teamManager);
        }
        runGame(game);
    }

    // Runs a given game
    private void runGame(BoardGame game){
        game.runGame();
        game.printStatistics();
    }

    // Check if the scoreboard needs to be reset or not and resets the scores and flips the resetScore back to false
    private void checkResetScore(){
        if(resetScores){
            scores.clearScores(teams);
            resetScores = !resetScores;
        }
    }

    // Creates an initial game to be played the first time
    private void createInitialGame(){
        if(defaultGame){
            createDefaultGame(true);
        }
        else{
            createCustomGame(true, true);
        }
        for(Team team: teams){
            team.setCurrentPlayer(team.getPlayers().get(0));
        }
    }

    // Creates a game with default settings and will prompt to change teams if applicable
    private void createDefaultGame(boolean changeTeam){
        boardSize = getDefaultBoardSize(currGame);
        if(changeTeam){
            numTeams = getMinTeams(currGame);
            totalPlayers = numTeams;
            teams = new Team[numTeams];
            for(int index = 0; index < teams.length;index++){
                teams[index] = new Team(1, index+1,"",null);
            }
            numPlayersOnEachTeam = new int[numTeams];
            promptAssignTeams();

            for(int index = 0; index < numTeams;index++){
                numPlayersOnEachTeam[index] = 1;
            }
        }

    }

    // Creates a custom game where the board is resizeable and the teams can be changed
    private void createCustomGame(boolean resizeBoard,boolean changeTeams){
        if(resizeBoard) {
            if(!defaultGame) {
                promptBoardSize(currGame);
            }
        }
        if(changeTeams){
            if(!defaultGame){
                promptNumTeams(currGame);
                promptNumPlayers(currGame);
                teams = new Team[numTeams];
                promptNumPlayersEachTeam();
                promptAssignTeams();
            }

        }

    }

    // Prints the intro to the program and then proceeds to ask for and set the current game
    private void decideCurrGame(){
        currGame = printIntro();
    }

    // Print the introduction to the game and prompt the user for what game they would like to play!
    private int printIntro(){
        System.out.println("Welcome to board game simulator! Please play with another user or team! ");
        int game = promptDecideGame();
        return game;

    }

    // Sets the decision for quitting the game based of user input
    private void decideQuit(){
        quit = endPrompt();
    }

    // Sets the decision for changing the game based of user input
    private void decideGameChange(){
        gameChange = changeGamePrompt();
    }

    // Create a game where the teams are adjusted
    private void createChangeTeamsGame(){
        if(!defaultGame){
            createCustomGame(true,true);
        }
        else{
            createDefaultGame(true);
        }
        teamManager.updateTeamManager(teams);
        resetScores = true;
    }

    // Create a game where the teams are unadjusted
    private void createUnchangedTeamsGame(){
        if(!defaultGame){
            createCustomGame(true, false);
        }
        else{
            createDefaultGame(false);
        }
    }

    // Print the game rules based off the corresponding game
    private void printGameRules(){
        System.out.println(getGameRules(currGame));
    }

    // Starts the gaming session
    private void playSession(){
        while(true){
            printGameRules();
            setRoles();
            gameToPlay();
            decideQuit();
            if(quit){ break;}
            decideGameChange();
            if(gameChange){
                currGame = promptDecideGame();
            }
            promptDefault();
            if(promptChangeTeams()){
                createChangeTeamsGame();
            }
            else{
                createUnchangedTeamsGame();
            }
        }

    }

    // Set the roles for each team
    private void setRoles(){
        possibleRoles = getRoles(currGame);
        for(Team team: teams){
            String[] roleSet = promptRole(team.getTeamNum(), possibleRoles);
            team.setRole(roleSet[0]);
            String[] pieces = roleSet[1].split(",");
            Piece[] p = new Piece[pieces.length];
            for(int index = 0; index < pieces.length;index++){
                p[index] = new Piece(pieces[index]);
            }
            team.setPieces(p);
        }
    }

    // The value mapped to a game number includes the rule set for the game and so to read either rules or game name the value is split into two strings
    private String readPossibleGames(int gameNum, int index, String delimiter){
        return possibleGames.get(gameNum).split(delimiter)[index];
    }

    // Takes the map of possible games and returns it in a human-readable string
    private String possibleGamesToString(){
        if(possibleGames.isEmpty()){
            System.out.println("There are no games available at this time.");
            System.exit(0);
        }
        StringBuilder games = new StringBuilder();
        for(int index = 1; index < possibleGames.size()+1;index++){
            games.append("(" + index + ") " +  getGameName(index) + "\n");
        }
        return games.toString();
    }

    // Grabs the possible games from a config properties where the list of games are stored
    private void setPossibleGames(){
        try{
            if(config == null){
                throw new FileNotFoundException("File does not exist!");
            }
            FileReader reader = new FileReader(config);
            Properties properties = new Properties();
            properties.load(reader);
            properties.size();
            for(int index = 1; index < properties.size()+1;index++){
                possibleGames.put(index,properties.getProperty(""+index));
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File with the games was not read correctly");
        } catch (IOException e) {
            System.err.println("File with the games was not read correctly");
        }

    }

    // Grabs the second entry in config.properties to get the game rules
    private String getGameRules(int gameNum){
        return readPossibleGames(gameNum, 1, " \\| ");
    }

    // Grabs the first entry in config.properties to get the game name
    private String getGameName(int gameNum){
        return readPossibleGames(gameNum, 0, " \\| ");
    }

    // Grabs the possible roles for a particular game from the config.properties file
    private int getPossibleTeams(int gameNum, int index){
        return Integer.parseInt(readPossibleGames(gameNum,index," \\| ").split(": ")[1].split(" \\|\\| ")[0]);
    }

    // Grabs the max number of team for a particular game from the config.properties file
    private int getMaxTeams(int gameNum){
        return getPossibleTeams(gameNum,2);

    }

    // Grabs the min number of team for a particular game from the config.properties file
    private int getMinTeams(int gameNum){
        return getPossibleTeams(gameNum,3);
    }

    // Grabs the default board size for a particular game from the config.properties file
    private int getDefaultBoardSize(int gameNum){
        return getPossibleTeams(gameNum,4);
    }

    // Grabs the min board size for a particular game from the config.properties file
    private int getMinBoardSize(int gameNum){
            return getPossibleTeams(gameNum,5);
    }

    // Prompt to decide what game to choose
    private int promptDecideGame(){
        System.out.println("Please decide what game you would like to play by typing in the corresponding game number! Your options are:\n");
        System.out.println(possibleGamesToString());
        int game = 0;
        while(true){
            try{

                Scanner prompt = new Scanner(System.in);
                game = prompt.nextInt();
                if(possibleGames.containsKey(game)){
                    break;
                }
                else{
                    System.out.println("That game does not exist. Please try again with a value corresponding to a game!");
                }

            } catch(InputMismatchException err){
                System.err.println("Invalid input, please input an number corresponding to a game!");
                promptDecideGame();
                break;
            } catch(NoSuchElementException err){
                System.err.println("Nothing was entered!");
                promptDecideGame();
                break;
            }
        }
        return game;


    }

    // Prompt to decide if the user would like to play with default settings
    private void promptDefault(){
        System.out.println("Would you like to play with default settings? (y/n)");
        Scanner prompt = new Scanner(System.in);
        String answer = prompt.nextLine();
        while(!answer.equals("y") && !answer.equals("n")){
            System.out.println("Invalid input, please try again!");
            answer = prompt.nextLine();
            System.out.println(answer);
        }

        if(answer.equals("y")){
            defaultGame = true;
        }
        else{
            defaultGame = false;
        }


    }

    // Prompt the user for an integer within a range
    private int promptSpecificInt(String prom, int min, int max){
        System.out.println("Please input a " + prom + " Min: " + min + " Max: " + max);
        int response = 0;
        while(true){
            try{
                Scanner prompt = new Scanner(System.in);
                response = prompt.nextInt();
                if(response > max){
                    System.out.println("Number is too large!");
                }
                else if(response < min){
                    System.out.println("Number is too small!");
                }
                else{
                    break;
                }

            } catch(InputMismatchException err){
                System.err.println("Invalid input, please input a " + prom);
                return promptSpecificInt(prom,min,max);
            } catch(NoSuchElementException err){
                System.err.println("Nothing was entered!");
                return promptSpecificInt(prom,min,max);
            }
        }
        return response;
    }

    // Prompt the user for a board size
    private void promptBoardSize(int currGame){
        boardSize = promptSpecificInt("board size n! (It will be a nxn board)",getMinBoardSize(currGame),maxBoardSize);
    }

    // Prompt the user for a number of teams
    private void promptNumTeams(int gameNum){
        int minTeams = getMinTeams(gameNum);
        int maxTeams = getMaxTeams(gameNum);
        numTeams = promptSpecificInt("number of teams playing!", minTeams,maxTeams);
    }

    // Prompt the user for a number of players
    private void promptNumPlayers(int gameNum){
        int minPlayers = getMinTeams(gameNum);
        int maxPlayers = 50;
        totalPlayers = promptSpecificInt("number of total players!", minPlayers, maxPlayers);
    }

    // Prompt the user for the number of players on each team
    private void promptNumPlayersEachTeam(){
        numPlayersOnEachTeam = new int[numTeams];
        int currTotal = 0;
        for(int index = 0; index < numTeams; index++){
            numPlayersOnEachTeam[index] = promptSpecificInt("number of players on team " + (index+1),1,totalPlayers-1);
            currTotal+=numPlayersOnEachTeam[index];
            teams[index] = new Team(numPlayersOnEachTeam[index], index+1, "", null);
        }
        if(currTotal != totalPlayers){
            System.out.println("Invalid number of players, please try again!");
            promptNumPlayersEachTeam();
        }
    }

    // Prompt each player for their name
    private String promptPlayerName(int playerNum, int teamNum){
        String currPlayerName = "";
        System.out.println("Player " + playerNum + " of Team " + teamNum + " please enter your name" );
        while(true){
            try{
                Scanner prompt = new Scanner(System.in);
                currPlayerName = prompt.nextLine();
                String newPlayer = currPlayerName.replaceAll("\\s","");
                if(newPlayer.equals("")){
                    System.out.println("The name you inputted is invalid");
                    currPlayerName = promptPlayerName(playerNum,teamNum);
                }
                break;

            } catch(InputMismatchException err){
                System.err.println("Invalid input, please input a name!");
                currPlayerName = promptPlayerName(playerNum,teamNum);
                break;
            } catch(NoSuchElementException err){
                System.err.println("Nothing was entered!");
                currPlayerName = promptPlayerName(playerNum, teamNum);
                break;
            }
        }
        return currPlayerName;
    }

    // Prompt to assign a user to a team
    private void promptAssignTeams(){
        System.out.println("Note that Team 1 will always go first!\n");
        String currPlayer = "";
        for(Team team: teams){
            int playerNum = 1;

            for(int index = 0; index < team.getNumPlayers();index++){
                currPlayer = promptPlayerName(playerNum, team.getTeamNum());
                team.addPlayerWithName(currPlayer);
                playerNum++;
            }

        }
    }

    // Prompt to assign a role to a team
    private String[] promptRole(int teamNum, HashMap<String, String> pRoles){
        System.out.println("Please input a role for team " + teamNum  );
        System.out.println("Possible roles: ");
        for(String role: pRoles.keySet()){
            System.out.println(role);
        }
        String response = "";
        String[] ret = new String[2];
        while(true){
            try{
                Scanner prompt = new Scanner(System.in);
                response = prompt.nextLine();
                if(!pRoles.containsKey(response)){
                    System.out.println("Role unavailable, please try again");
                    System.out.println("Current roles: ");
                    for(String role: pRoles.keySet()){
                        System.out.println(role);
                    }
                }
                else{
                    ret[0] = response;
                    ret[1] = pRoles.get(response);
                    pRoles.remove(response);
                    break;
                }

            } catch(InputMismatchException err){
                System.err.println("Invalid input, please input a role for team" + teamNum );
                ret = promptRole(teamNum, pRoles);
                break;
            } catch(NoSuchElementException err){
                System.err.println("Nothing was entered!");
                ret = promptRole(teamNum, pRoles);
                break;
            }
        }
        return ret;
    }

    private HashMap<String,String> getRoles(int gameNum){
        HashMap<String,String> possibleRoles = new HashMap<>();
        String roleString = readPossibleGames(gameNum,1," \\|\\| ");
        String[] roles = roleString.split(" / ");
        for(String role: roles){
            possibleRoles.put(role.split(": ")[0], role.split(": ")[1]);
        }
        return possibleRoles;
    }

    // Prompt the user to decide yes or no with a specific prompt
    private boolean promptDecisionYN(String promptInQuestion){
        System.out.println("Would you like to " + promptInQuestion + "? (y/n)");
        Scanner prompt = new Scanner(System.in);
        String answer = prompt.nextLine();
        while(!answer.equals("y") && !answer.equals("n")){
            System.out.println("Invalid input, please try again!");
            answer = prompt.nextLine();
            System.out.println(answer);
        }

        if(answer.equals("y")){
            return true;
        }
        else{
            return false;
        }

    }

    // Asks the user if they would like to end the session
    private boolean endPrompt(){
        return promptDecisionYN("end this session");
    }

    private boolean promptChangeTeams(){
        return promptDecisionYN("change up the teams");
    }

    // Asks the user if they would like to change to a different game
    private boolean changeGamePrompt(){
        return promptDecisionYN("play a different game");
    }

}