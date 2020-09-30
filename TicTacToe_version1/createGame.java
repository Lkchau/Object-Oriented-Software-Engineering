import java.util.*;

// Driver class for the tic tac toe game
public class createGame{

    private static int countX = 0;
    private static int countO = 0;
    private static int draw = 0;
    private static boolean quit = false;

    public static void run(){
        while(!quit){
            createNewGame();
            quit = endPrompt();
        }
        printStatistics();
    }

    private static void createNewGame(){
        TicTacToe game = new TicTacToe();
        printIntroduction(game);
        playOneRound(game);
        updateStatistics(game);
    }

    private static void printIntroduction(TicTacToe x){
        System.out.println("Welcome to tic tac toe! Please play with another user and decide who will be using O and X! \nAlso play by inputting a value bewteen 1 and 9 with 1 to 9 corresponding to the grid below!");
        System.out.println("Grid positions: ");
        System.out.println("+--+--+--+");
        System.out.println("|" + 1 + " |" + 2 + " |" +  3 + " |");
        System.out.println("+--+--+--+");
        System.out.println("|" + 4 + " |" + 5 + " |" +  6 + " |");
        System.out.println("+--+--+--+");
        System.out.println("|" + 7 + " |" + 8 + " |" +  9 + " |");
        System.out.println("+--+--+--+\n");
    }

    private static void playOneRound(TicTacToe game){
        int turn = 0;
        while(!game.fullBoard()){
            promptUser(turn, game);
            if(game.checkWin()){
                System.out.println("We have a winner!");
                break;
            }
            else if(game.fullBoard()){
                System.out.println("We have a draw!");
            }
            turn++;
        }
    }

    private static void promptUser(int turn, TicTacToe game){
        if(turn % 2 == 0){
            promptSpecificUser("O", game);
        }
        
        else{
            promptSpecificUser("X", game);
        }
        game.displayBoard();
    }

    private static void promptSpecificUser(String user, TicTacToe game){
        while(true){
            try{
                Scanner prompt = new Scanner(System.in);
                int position;
                System.out.println("Player " + user + " Enter your move: ");
                position = prompt.nextInt();
                if(position > 9 || position < 1){
                    System.out.println("Player " + user + " has done an invalid move, please try again!");
                }
                else{
                    if(game.updateBoard(position, user)){
                        System.out.println("updating...\n");
                        break;
                    }
                    else{
                        System.out.println("That space is already filled! Please try again!");
                    }
                }

            }
            catch(InputMismatchException err){
                System.err.println("Invalid input, please input a value between 1 and 9");
                promptSpecificUser(user, game);
                break;
            }
            catch(NoSuchElementException err){
                System.err.println("Invalid input, please input a value between 1 and 9");
                promptSpecificUser(user, game);
                break;
            }
        
        }
    }

    private static boolean endPrompt(){
        System.out.println("Would you like to continue? (y/n)");
        Scanner prompt = new Scanner(System.in);
        String answer = prompt.nextLine();
        while(!answer.equals("y") && !answer.equals("n")){
            System.out.println("Invalid input, please try again!");
            answer = prompt.nextLine();
            System.out.println(answer);
        }

        if(answer.equals("y")){
            return false;
        }
        else{
            return true;
        }
        

    }

    private static void updateStatistics(TicTacToe game){
        if(game.getWinner() == null){
            draw++;
        }
        else if(game.getWinner().equals("O")){
            countO++;
        }
        else{
            countX++;
        }

    }
    

    private static void printStatistics(){
        System.out.println("\n=================================");
        System.out.println("Summary Results:");
        System.out.println("X won: " + countX + " times.");
        System.out.println("O won: " + countO + " times.");
        System.out.println("There were " + draw + " draws.");
        System.out.println("Out of " + (countX + countO + draw) + " games.");
        System.out.println("=================================\n");

    }
}