
//Main class to run the tic tac toe game
public class App{

    public static void main(String[] args){
        // Creates the game
        CreateGame game = new CreateGame();
        // Adds a shutdown hook which will be listening for when the program ends and will print out dialogue
        Runtime.getRuntime().addShutdownHook(new CaptureExit());
        // Run the game
        game.run();
        System.exit(0);
    }

}