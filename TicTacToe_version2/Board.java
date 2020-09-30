public class  Board {

    private Tile[][] board;
    private int width;
    private int height;

    // No-args constructor for board
    public Board(){
        board = null;
        width = 0;
        height = 0;
    }

    // Constructor for board with a width and a height for potential functionality with games that do not have a square board
    public Board(int width, int height){
        board = new Tile[width][height];
        setWidth(width);
        setHeight(height);
    }

    // Constructor for a square board
    public Board(int dimensions){
        this(dimensions,dimensions);
    }

    // Checks if a tile in the board is empty or not
    public boolean checkEmptyTile(int position){
        int row = Math.floorDiv((position),height);
        int col = (position) % width;
        if(!board[row][col].isEmpty()){
            return false;
        }
        return true;
    }

    // Set the board to be an empty board
    public void setEmptyBoard(){
        for(int index = 0; index < board.length;index++){
            for(int index2 = 0; index2 < board[0].length;index2++){
                board[index][index2] = new Tile();
            }
        }
    }

    // Fill a tile in the board with a new tile
    public void fillTile(int position, Tile piece){
        int row = Math.floorDiv((position),height);
        int col = (position) % width;
        board[row][col] = piece;
    }

    // Getter for the board
    public Tile[][] getBoard() {
        return board;
    }

    // Setter for the board
    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    // Getter for the board height
    public int getHeight() {
        return height;
    }

    // Getter for the board width
    public int getWidth() {
        return width;
    }

    // Setter for the board height
    private void setHeight(int height) {
        this.height = height;
    }

    // Setter for the board width
    private void setWidth(int width) {
        this.width = width;
    }

    // Checks if the board is full or not
    public boolean fullBoard(){
        for(int r = 0; r < board.length;r++){
            for(int c = 0; c < board[0].length;c++){
                if(board[r][c].isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    // Returns a board showing the position of each tile
    public String boardPlacements(){
        StringBuilder boardString = new StringBuilder();
        StringBuilder divider = new StringBuilder();
        int coord = 0;
        for(int index = 0; index < width; index++){
            divider.append("+--");
        }
        divider.append("+\n");

        boardString.append(divider);

        for(int index2 = 0; index2 < board.length;index2++){
            for(int index3 = 0; index3 < board[0].length; index3++){
                if(coord <= 9){
                    boardString.append("|" + coord + " ");
                }
                else{
                    boardString.append("|" + coord);
                }
                coord++;

            }
            boardString.append("|\n");
            boardString.append(divider);

        }

        return boardString.toString();
    }

    // Returns the board as readable string
    public String toString(){
        StringBuilder boardString = new StringBuilder();
        StringBuilder divider = new StringBuilder();
        for(int index = 0; index < width; index++){
            divider.append("+--");
        }
        divider.append("+\n");

        boardString.append(divider);

        for(int index2 = 0; index2 < board.length;index2++){
            for(int index3 = 0; index3 < board[0].length; index3++){
                boardString.append("|" + board[index2][index3].toString() + " ");
            }
            boardString.append("|\n");
            boardString.append(divider);

        }

        return boardString.toString();
    }

}
