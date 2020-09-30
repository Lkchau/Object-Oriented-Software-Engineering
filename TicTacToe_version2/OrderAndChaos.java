public class OrderAndChaos extends BoardGame{
    // Class to implement the game order and chaos
    private Team order;
    private Team chaos;

    // No-args constructor
    public OrderAndChaos(){
        super();
    }

    // Constructor that makes use of the BoardGame class constructor
    public OrderAndChaos(Team[] teams, ScoreBoard scores, int boardSize, TeamManager teamManager){
        super(teams,scores,boardSize, teamManager);
    }

    // Implements the check win function by checking if there is a horizontal, vertical, going both ways from top left to bottom right and top right to bottom left
    public boolean checkWin(int position){
        return horizontalWin()||verticalWin()||diagonal1Win()||diagonal2Win();
    }

    // Check if there is a horizontal win
    private boolean horizontalWin(){
        for(int r = 0; r != boardSize;r++){
            for(int c = 0; c != boardSize;c++){
                if(inARow(r,c,0,1,5,"X")){
                    return true;
                }
                if(inARow(r,c,0,1,5,"O")){
                    return true;
                }
            }
        }
        return false;
    }

    // Check if there is a vertical win
    private boolean verticalWin(){
        for(int r = 0; r != boardSize;r++){
            for(int c = 0; c != boardSize;c++){
                if(inARow(r,c,1,0,5,"X")){
                    return true;
                }
                if(inARow(r,c,1,0,5,"O")){
                    return true;
                }
            }
        }
        return false;

    }

    // Check if there is a diagonal win from top left to bottom right
    private boolean diagonal1Win(){
        for(int r = 0; r != boardSize;r++){
            for(int c = 0; c != boardSize;c++){
                if(inARow(r,c,1,1,5,"X")){
                    return true;
                }
                if(inARow(r,c,1,1,5,"O")){
                    return true;
                }
            }
        }
        return false;
    }

    // Check if there is a diagonal win from the bottom left to the top right
    private boolean diagonal2Win(){
        for(int r = 0; r != boardSize;r++){
            for(int c = 0; c != boardSize;c++){
                if(inARow(r,c,1,-1,5,"X")){
                    return true;
                }
                if(inARow(r,c,1,-1,5,"O")){
                    return true;
                }
            }
        }
        return false;
    }

    // Check if there is a certain number of a piece in a row
    private boolean inARow(int row, int col, int directionR, int directionC, int number, String val) {
        for (int index = 0 ; index != number ; index++) {
            int r = row + index*directionR;
            int c = col + index*directionC;
            if (r < 0 || c < 0 || r >= boardSize || c >= boardSize || !board.getBoard()[r][c].getPiece().getPieceType().equals(val)) {
                return false;
            }
        }
        return true;
    }

    // Sets which team is order
    private void setOrder(Team team){
        order = team;
    }

    // Sets which team is chaos
    private void setChaos(Team team){
        chaos = team;
    }

    // Check if chaos won the game
    private boolean chaosWin(){
        return isBoardFull();
    }

    // Implements runGame from the BoardGame superclass to run the game
    protected void runGame(){
        for(Team team: teams){
            if(team.getRole().equals("Order")){
                setOrder(team);
            }
            if(team.getRole().equals("Chaos")) {
                setChaos(team);
            }
        }
        Board currBoard = getBoard();

        for(Team team : getTeams()){
            System.out.println(team.toString());
        }
        printBoardRules(currBoard);
        placementRules();
        while(!chaosWin()){
            boolean win = playOneTurn();
            if(win){
                break;
            }
            if(chaosWin()){
                winMessage(chaos);
                break;
            }
        }
    }

    // Implements placementRules from the BoardGame to print out the initial placement rules for users to follow
    protected void placementRules(){
        System.out.println("Please input a move in the following format: position piece (Example: entering 15 X will result in a X put into position 15 and entering 4 O will result in a O being put into position 4)");
    }

    // Implements printCorrectFormantMessage from the BoardGame superclass to print out the correct format for a move
    protected void printCorrectFormatMessage(){
        System.out.println("Please insert your move in the format: number piece (Possible pieces are O X)");
    }

    // Prints out the win message and updates the scores
    protected void winMessage(Team team){
        System.out.println(team.getRole() + " wins!");
        System.out.println(team.toString());
        scores.updateScores(team);
        setScores(scores);
        teamManager.resetTurns();

    }

    // Implements playOneTurn from the BoardGame superclass to play a single turn in the game
    protected boolean playOneTurn(){
        try{
            String[] move = teamManager.promptTeam();
            int currMove = Integer.parseInt(move[0]);
            Team currentTeam = teamManager.getCurrentTeam();
            Piece pieceType;
            if(move.length == 1) {
                printCorrectFormatMessage();
                return false;
            }
            else{
                if(move[1].length()>1){
                    printCorrectFormatMessage();
                    return false;
                }
                pieceType = new Piece(move[1]);
            }

            if(validMove(currMove)) {

                if(!currentTeam.hasPiece(pieceType)){
                    System.out.println("You cannot place that piece down, please try again!");
                    return false;
                }
                board.fillTile(currMove,new Tile(pieceType));
                setBoard(board);
                printCurrentBoard();
                if(checkWin(currMove)){
                    winMessage(order);
                    return true;
                }
                else{
                    teamManager.changeTurn();
                }
            }
            return false;
        }
        catch(NumberFormatException err){
            System.err.println("Invalid move!");
            return false;
        }

    }



}
