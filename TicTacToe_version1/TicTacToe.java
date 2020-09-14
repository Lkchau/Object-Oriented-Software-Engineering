import java.util.*;

public class TicTacToe{
    private String[][] board;
    private int turn;
    private String winner;

    //Constructor for a tic tac toe game
    public TicTacToe(){
        this.board = new String[3][3];
        initBoard();
    }

    //Create an empty 3 x 3 board
    private void initBoard(){
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                this.board[r][c] = " ";
            } 
        }   
    }

    //Print out a visual representation of the board for the user
    public void displayBoard(){
        System.out.println("+--+--+--+");
        System.out.println("|" + board[0][0] + " |" + board[0][1] + " |" +  board[0][2] + " |");
        System.out.println("+--+--+--+");
        System.out.println("|" + board[1][0] + " |" + board[1][1] + " |" +  board[1][2] + " |");
        System.out.println("+--+--+--+");
        System.out.println("|" + board[2][0] + " |" + board[2][1] + " |" +  board[2][2] + " |");
        System.out.println("+--+--+--+\n");
    }

    //Check if the board is full
    public boolean fullBoard(){
        for(int r = 0; r < board.length;r++){
            for(int c = 0; c < board[0].length;c++){
                if(board[r][c] == " "){
                    return false;
                }
            }
        }
        return true;
    }
    //Check if there is a winner
    public boolean checkWin(){

        return rowWin() || columnWin() || diagonalWin();

    }
    //Check if all the values going horizontally are the same to determine a winner
    private boolean rowWin(){
        for(int r = 0; r < board.length; r++){
            if(allEqualVal(board[r])){
                winner = board[r][0];
                return true;
            }
        }
        return false;
    }
    //Check if all the values going vertically are the same to determine a winner
    private boolean columnWin(){
        for(int c = 0; c < board[0].length; c++){
            String[] col = new String[3];
            col[0] = board[0][c];
            col[1] = board[1][c];
            col[2] = board[2][c];
            if(allEqualVal(col)){
                winner = col[0];
                return true;
            }
        }
        return false;
    }
    //Check if all the values going diagonally are the same to determine a winner
    private boolean diagonalWin(){
        String[] diagonal1 = new String[3];
        diagonal1[0] = board[0][0];
        diagonal1[1] = board[1][1];
        diagonal1[2] = board[2][2];
        String[] diagonal2 = new String[3];
        diagonal2[0] = board[0][2];
        diagonal2[1] = board[1][1];
        diagonal2[2] = board[2][0];
        if(allEqualVal(diagonal1) || allEqualVal(diagonal2)){
            winner = diagonal1[0];
            return true;
        }
        
        return false;
    }

    //Check if all the values in an array are the same value and not just a empty space
    private boolean allEqualVal(String[] arr){
        if(arr[0].equals(" ")){
            return false;
        }
        Set<String> s = new HashSet<>(Arrays.asList(arr));
        if(s.size() == 1){
            return true;
        }
        else{
            return false;
        }
    }
    //updates the board with a given position and which player (X or O)
    public boolean updateBoard(int position, String user){

        if(position >= 1 && position <= 9){
            boolean valid = validMove(position);
            int row = Math.floorDiv((position - 1),3);
            int col = (position-1) % 3; 

            if(valid){
                board[row][col] = user;
                return true;
            }
        }
        return false;
    }
    //Checks if the inputted position is in bounds and if there isn't already a value in the position
    private boolean validMove(int position){
        if(position > 9 || position < 1){
            return false;
        }
        int row = Math.floorDiv((position - 1),3);
        int col = (position-1) % 3; 
        if(!board[row][col].equals(" ")){
            return false;
        }
        return true;
    }

    //Returns who was the winner
    public String getWinner(){
        return winner;
    }
}