public abstract class BoardGame {

    // All methods and variables are protected such that only subclasses of board game can get access to them
    // Note that in both Order and Chaos and Tic Tac Toe there are methods called horizontal win, vertical win and diagonal win, but there is not in this class because not all boardgames would have those win conditions

    protected Team[] teams;
    protected ScoreBoard scores;
    protected Board board;
    protected int boardSize;
    protected TeamManager teamManager;

    // No-args constructor for a board game
    protected BoardGame(){
        teams = null;
        scores = null;
        boardSize = 0;
        board = null;
        teamManager = null;
    }

    // Constructor for a board game
    protected BoardGame(Team[] teams,ScoreBoard scores, int boardSize, TeamManager teamManager){
        setTeams(teams);
        setScores(scores);
        setBoardSize(boardSize);
        setBoard(new Board(boardSize));
        this.board.setEmptyBoard();
        setTeamManager(teamManager);
    }

    // Getter for teams
    protected Team[] getTeams(){
        return teams;
    }

    // Setter for teams
    protected void setTeams(Team[] teams) {
        this.teams = teams;
    }

    // Getter for scores
    protected ScoreBoard getScores(){
        return scores;
    }

    // Setter for Scores
    protected void setScores(ScoreBoard scores) {
        this.scores = scores;
    }

    // Getter for board size
    protected int getBoardSize() {
        return boardSize;
    }

    // Setter for board size
    protected void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    // Getter for board
    protected Board getBoard(){
        return board;
    }

    // Setter for board
    protected void setBoard(Board board) {
        this.board = board;
    }

    // Getter for teamManager
    protected TeamManager getTeamManager(){
        return teamManager;
    }

    // Setter for teamManager
    protected void setTeamManager(TeamManager teamManager){
        this.teamManager = teamManager;
   }

    // Prints out the current board
    protected void printCurrentBoard(){
        System.out.println(board.toString());
    }

    // Prints out the scores
    protected void printStatistics(){
        System.out.println(scores.toString());
    }

    // Checks if a move is valid by seeing if the tile is filled or if the move is out of bounds
    protected boolean validMove(int position){
        if(position < 0 || position > boardSize*boardSize-1){
            System.out.println("Invalid move! The position put is outside of the board.");
            return false;
        }
        else if(!board.checkEmptyTile(position)){
            System.out.println("The space is already filled!");
            return false;
        }
        return true;

    }

    // Checks if the board is full
    protected boolean isBoardFull(){
        return board.fullBoard();
    }

    // Prints out the initial board coordinates for the user
    protected void printBoardRules(Board b){
        System.out.println("Please follow the following guidelines when assigning a piece: ");
        System.out.println(b.boardPlacements());
    }

    // Abstract method to be implemented for checking for a win condition give a position that the user put a piece in
    protected abstract boolean checkWin(int position);

    // Abstract method to be implemented to play one turn of the board game
    protected abstract boolean playOneTurn();

    // Abstract method to be implemented to run the game
    protected abstract void runGame();

    // Abstract method to be implemented to print out how the user should format their move
    protected abstract void placementRules();

    // Abstract method to be implemented to print out the correct format a move should be
    protected abstract void printCorrectFormatMessage();




}