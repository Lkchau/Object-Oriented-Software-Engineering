// Concrete implementation of a rpg with a map
public class LegendsMonstersAndHeroes extends RolePlayingGameWithMap{
    private Colors color = Colors.getColors();
    private final String MAPTYPE;
    private final int MINMAPSIZE = 5;
    private final int MAXMAPSIZE = 10;
    private final int DEFAULTMAPSIZE = 8;
    private boolean quit = false;

    // Constructor
    public LegendsMonstersAndHeroes(){
        super("LEGENDS");
        MAPTYPE = "LEGENDS";
    }

    // Run the game and print extra info if needed
    public void run(){
        int showInfo = welcomeScreen();
        switch(showInfo){
            case 2:
                printInstructions();
                break;
        }
        setCustomMap();
        startGame();
    }

    //print out the welcome screen
    private int welcomeScreen(){
        System.out.println(color.coloredString("red","cyan"," ___            ___ ") + "                   Welcome to Legends: Monsters And Heroes!\r\n"+color.coloredString("red","cyan","/   \\          /   \\")+"                                              \r\n" + color.coloredString("red","cyan","\\_   \\        /  __/\r")+"\n" + color.coloredString("red","cyan", " _\\   \\      /  /__ \r") + "\n" + color.coloredString("red","cyan"," \\___  \\____/   __/ \r") + "\n"+color.coloredString("red","cyan","     \\_       _/    \r") + "\n"+color.coloredString("red","cyan","       | @ @  \\_    \r") + "\n" +color.coloredString("red","cyan","       |            \r") + "\n" + color.coloredString("red","cyan","     _/     /\\      \r") + "\n" + color.coloredString("red","cyan","    /o)  (o/\\ \\_    \r") + "\n"+color.coloredString("red","cyan","    \\_____/ /       \r") + "\n" +color.coloredString("red","cyan","      \\____/        "));
        UserPrompt prompter = UserPrompt.getPrompt();
        int response = prompter.promptMenuOptions(this.menus.getMenu("MENU").getMenu("StartMenu", null), false);
        return response;
    }

    // Start the game
    private void startGame(){
        System.out.println(Colors.getColors().coloredString("purple","\t\t\t\t\t\t\t\t\t\t L E T   T H E   G A M E   B E G I N\n"));
        setUpGame();
        runGame(UserPrompt.getPrompt());
    }

    // Set up a game
    private void setUpGame(){
        setUpInitialParty(UserPrompt.getPrompt());
        printCurrentMap();
    }

    // run the game
    private void runGame(UserPrompt prompter){
        while(!quit){
            for(Party p: parties) {
                playOneTurn(prompter, p);
            }
        }

    }

    // Play out one turn of the rpg
    private void playOneTurn(UserPrompt prompter, Party p) {
        String response = "";
        String tileTypeEntered = "";
        do{
            response = printControlMenu(prompter);
            switch (response) {
            case "I":
            case "i":
                printParty(0);
                printCurrentMap();
                break;
            case "Q":
            case "q":
                quit = true;
                break;
            default:
                tileTypeEntered = performMove(response, p);
                printCurrentMap();
                break;
            }
        } while(response.equalsIgnoreCase("I"));

    }

    // Print out the details of the party in question
    private void printParty(int index){
        System.out.println(parties.get(index).toString());
    }

    // Print out the controls for the user
    private String printControlMenu(UserPrompt prompter){
        Menu controlsMenu = this.menus.getMenu("MENU").getMenu("ControlMenu", null);
        return prompter.promptMenuOptionsKey(controlsMenu, false);
    }

    // Ask and set up a custom sized map
    private void setCustomMap(){
        UserPrompt prompter = UserPrompt.getPrompt();
        boolean setMapSize = prompter.promptYN("Would you like to set a map size? (Default is an " + DEFAULTMAPSIZE + "x" + DEFAULTMAPSIZE + " board!)");
        if(setMapSize){
            MapFactory mf = new MapFactory();
            map = mf.getMap(MAPTYPE, prompter.promptForIntWithPrompt("Please enter a map size: ", MINMAPSIZE,MAXMAPSIZE,false));
        }
    }

    // Set up initial party (maybe can add more parties later?)
    private void setUpInitialParty(UserPrompt prompter){
        parties.add(new Party());
        System.out.println(color.coloredString("red", "\t\t\t\t\t\t\t\t\t\t\t\t*   CHOOSE YOUR TEAM   *\n"));
        Menu characterMenu = this.menus.getMenu("MENU").getMenu("CreateHeroMenu", null);
        do{
            createHero(prompter, 0, characterMenu);
        }
        while(parties.get(0).getPartySize() < 3 && prompter.promptYN("Would you like to add another hero to the party? (Max number heroes is 3!). Current party size is: " + parties.get(0).getPartySize()));
        System.out.println(parties.get(0));
        int[] initialPartyLocation = this.map.setRandomPartyLocation(parties.get(0));
        parties.get(0).setPartyType("Hero");
        parties.get(0).setLocation(initialPartyLocation[0], initialPartyLocation[1]);
        parties.get(0).printCurrentLocation();
    }

    // Create a specific hero based on user input and possible heroes
    private void createHero(UserPrompt prompter, int partyIndex, Menu characterMenu){
        CharacterFactory factory = new CharacterFactory();
        CharacterAbstractFactory abstractFactory = factory.getCharacterAbstractFactory("hero");
        String currentHeroType = prompter.promptMenuOptionsValue(characterMenu, false);
        String characterInfo = choicesWithinClass(currentHeroType, FileParser.getFileParser(), prompter);
        Character toAdd = abstractFactory.getCharacter(currentHeroType,characterInfo);
        parties.get(partyIndex).addCharacter(toAdd);
    }

    // show and prompt for a certain hero within an archetype
    private String choicesWithinClass(String type, FileParser fp, UserPrompt prompter){
        Menu characterMenu = this.menus.getMenu("MENU").getMenu(type, null);
        int characterIndex = prompter.promptMenuOptions(characterMenu, false);
        String[] actualInfo = characterMenu.getOptions().get(characterIndex).split("\t\\|\t");
        String characterInfo = actualInfo[0];
        characterMenu.getOptions().remove(characterIndex);
        return characterInfo;
    }

    // print out the current state of the map
    private void printCurrentMap(){
        System.out.println(this.map);

    }

    // Perform a move on the map and trigger any events based on what tile the party enters
    private String performMove(String res, Party p){
        boolean moved = false;
        switch(res){
            case "W":
            case "w":
                moved = this.map.move(p,"w");
                break;
            case "A":
            case "a":
                moved = this.map.move(p,"a");
                break;
            case "S":
            case "s":
                moved = this.map.move(p,"s");
                break;
            case "D":
            case "d":
                moved = this.map.move(p,"d");
                break;
            default:
                break;
        }
        if(moved){
            if(map.getBoard().getBoard()[p.getCurrRow()][p.getCurrCol()] instanceof Enterable){
                ((Enterable) map.getBoard().getBoard()[p.getCurrRow()][p.getCurrCol()]).enter(p);
            }
        }

        return map.getBoard().getBoard()[p.getCurrRow()][p.getCurrCol()].getClass().getName();

    }

    // Print out instructions
    public void printInstructions(){
        System.out.println(color.coloredString("Light yellow","__________               .__         .___        _____       \r\n\\______   \\_____    _____|__| ____   |   | _____/ ____\\____  \r\n |    |  _/\\__  \\  /  ___/  |/ ___\\  |   |/    \\   __\\/  _ \\ \r\n |    |   \\ / __ \\_\\___ \\|  \\  \\___  |   |   |  \\  | (  <_> )\r\n |______  /(____  /____  >__|\\___  > |___|___|  /__|  \\____/ \r\n        \\/      \\/     \\/        \\/           \\/             "         ));
        System.out.println("Movement:\t" + color.coloredString("Light magenta", "W") + " - " + "move upwards" );
        System.out.println("\t\t\t" + color.coloredString("Light magenta", "A") + " - " + "move left" );
        System.out.println("\t\t\t" + color.coloredString("Light magenta", "S") + " - " + "move backwards" );
        System.out.println("\t\t\t" + color.coloredString("Light magenta", "D") + " - " + "move right" );
        System.out.println(color.coloredString("red", "NOTE:") + " diagonal movement is not possible!\n");
        System.out.println("You can have up to " + color.coloredString("blue","THREE") + " heroes for your adventure.");
        System.out.println("You will be prompted to buy/sell or fight whenever you come across events that require you to do so! Please type in something corresponding to the response at those times!");
        System.out.println("To " + color.coloredString("cyan", "QUIT") + " please type in q whenever you are to make a move! You will not be able to leave during a fight or in the market (unless if you force quit that is)\n");
        UserPrompt up = UserPrompt.getPrompt();
        up.promptString("Please type in any key to start the game!",false);
    }
}
