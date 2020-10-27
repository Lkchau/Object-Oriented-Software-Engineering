public class App{
    // Main driver class for the application, it creates a game manager and runs the game and then exits when the games if finished
    public static void main(String[] args){
        GameManager game = new GameManager();
        game.run();
        System.exit(0);
    }
}