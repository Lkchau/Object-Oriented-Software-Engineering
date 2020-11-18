public class GameManager {
    // Representation of something to create, start and load a game
    private Game currGame;
    // run the game
    public void run(){
        loadGame();
        printExitScreen();
    }
    // load it and set up the game
    private void loadGame(){
        load();
        setUpGame();
    }

    // loading progress bar
    private void load(){
        System.out.println("Loading the game...");
        try {
            int progress = 0;
            while(progress <= 100){
                updateProgress(progress);
                progress += 1;
                Thread.sleep(40);
            }
            System.out.println("\n");
        } catch (InterruptedException err) {
            System.err.println("Error loading!");
        }
    }

    private static void updateProgress(int progress) {
        final int width = 100;
        Colors c = Colors.getColors();
        System.out.print("\r|");
        int i = 0;
        for (; i < (int)(progress/100.0*width); i++) {
            System.out.print(c.coloredBackground("blue", " "));
        }
        for (; i < width; i++) {
            System.out.print(" ");
        }
        System.out.print("| Progress: " + c.coloredString("green",progress + "%"));
    }

    // Set up game
    private void setUpGame(){
        GameFactory createGame = new GameFactory();
        UserPrompt prompter = UserPrompt.getPrompt();
        int decision = 1;
        currGame = createGame.getGame(createGame.lookUpIndex(decision));
        currGame.run();
    }

    // Exit game prompt
    private void printExitScreen(){

        System.out.println("Thank you for playing!");
    }
}
