public class Piece {

    // Piece object to be put into a tile on a board
    private String pieceType;

    // No-args constructor
    public Piece(){
        pieceType = " ";
    }

    // Constructor
    public Piece(String pieceType){
        setPieceType(pieceType);
    }

    // Getter for the piece type
    public String getPieceType() {
        return pieceType;
    }

    // Setter for the piece type
    public void setPieceType(String pieceType) {
        this.pieceType = pieceType;
    }

    // Equals function to see if another piece is of the same pieceType as this piece
    public boolean equals(Piece piece){
        return this.pieceType.equals(piece.getPieceType());
    }

    // Turns the piece into a string
    public String toString(){
        return pieceType;
    }
}
