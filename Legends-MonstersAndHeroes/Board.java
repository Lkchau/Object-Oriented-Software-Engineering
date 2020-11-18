import java.util.Arrays;
import java.util.Random;

// Board class to represent a physical board
public class Board {

    // A board is a 2d array of tiles
    Tile[][] board;
    int height;
    int width;


    // No-args constructor for board
    public Board(){
        setBoard(new Tile[0][0]);
        setWidth(0);
        setHeight(0);
    }

    // Constructor for board with a width and a height for potential functionality with games that do not have a square board
    public Board(int width, int height){
        setBoard(new Tile[width][height]);
        setWidth(width);
        setHeight(height);
    }

    // Constructor for a square board
    public Board(int dimensions){
        this(dimensions,dimensions);
    }

    // setters and getters for Board
    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public void setTile(int row, int column, Tile tile){
        board[row][column] = tile;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    // This fills all the tiles to be inaccessible (used for testing purposes only)
    private void insertInaccessible(){
        for(int index2 = 0; index2 < board.length;index2++) {
            for (int index3 = 0; index3 < board[0].length; index3++) {
                board[index2][index3] = new InaccessibleTile();
            }
        }
    }

    // Sets the party to be on a random spot on the map (NOTE: parties can be spawned on market tiles, the market event will not occur if so)
    public int[] setRandomSpot(Party p){
        boolean partyAdded = false;
        Random random = new Random();
        int[] location = new int[2];
        while(!partyAdded){
            int row = random.nextInt(board.length);
            int col = random.nextInt(board[0].length);
            if(!board[row][col].isOccupied()){
                board[row][col].setOccupied(true);
                board[row][col].getPartiesOnTile().add(p);
                location[0] = row;
                location[1] = col;
                partyAdded = true;
            }
        }
        return location;

    }

    // Changes whether or not the tile is occupied
    public void changeOccupancy(int row, int col, Party p, boolean isOccupied){
        board[row][col].setOccupied(isOccupied);
        board[row][col].remove(p);
    }

    // Function used by the toString to help print out the map prettier
    private String createBorder(char divider){
        StringBuilder div = new StringBuilder();
        int rowLength = board[0][0].getTileLength()*width + width +1;
        final char[] array = new char[rowLength];
        Arrays.fill(array, divider);
        String rowDivider = new String(array);
        div.append(rowDivider + "\n");
        return div.toString();
    }

    public String toString(){
        StringBuilder boardString = new StringBuilder();
        String divider = createBorder('-');

        boardString.append(divider);

        for(int index = 0; index < board.length;index++){
            for(int index2 = 0; index2 < board[0].length; index2++){
                boardString.append("|" + board[index][index2].toString());
            }
            boardString.append("|\n");
            boardString.append(divider);
        }

        return boardString.toString();
    }
}
