import java.util.*;

//Main class to run the tic tac toe game
public class App{

    public static void main(String[] args){
        Runtime.getRuntime().addShutdownHook(new captureExit());
        createGame.run();
        System.exit(0);
    }

}