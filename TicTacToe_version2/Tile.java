public class Tile {
    // Class for tiles of a board
    private Piece piece;

    // No-args constructor
    public Tile(){
        this.piece = new Piece();
    }

    // Constructor given a piece
    public Tile(Piece piece){
        setPiece(piece);
    }

    // Constructor give a value for a piece
    public Tile(String value){
        this(new Piece(value));
    }

    // Getter for piece
    public Piece getPiece() {
        return piece;
    }

    // Setter for piece
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    // Check if the tile is empty
    public Boolean isEmpty(){
        if(piece.getPieceType().equals(" ")){
            return true;
        }
        else{
            return false;
        }
    }

    // Returns a string representing the value stored in the tile
    public String toString(){
        return piece.getPieceType();
    }

}
