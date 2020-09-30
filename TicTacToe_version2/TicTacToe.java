import java.util.*;

public class TicTacToe extends BoardGame {
    // Implementation of the tic tac toe game

    // No-args constructor uses the constructor of the board game superclass
    public TicTacToe() {
        super();
    }

    // Constructor uses the constructor of the board game superclass
    public TicTacToe(Team[] teams, ScoreBoard scores, int boardSize, TeamManager teamManager) {
        super(teams, scores, boardSize, teamManager);
    }

    // Check if there is a winner
    protected boolean checkWin(int position) {

        return rowWin(position) || columnWin(position) || diagonalWin(position);

    }

    // Check if all the values going horizontally are the same to determine a winner
    private boolean rowWin(int position) {
        int row = Math.floorDiv((position), boardSize);
        if (allEqualVal(board.getBoard()[row])) {
            return true;
        }
        return false;
    }

    // Check if all the values going vertically are the same to determine a winner
    private boolean columnWin(int position) {
        int col = (position) % boardSize;
        int index = 0;
        Tile[] column = new Tile[boardSize];
        for (int r = 0; r < board.getBoard().length; r++) {
            column[index] = board.getBoard()[r][col];
            index++;
        }
        if (allEqualVal(column)) {
            return true;
        }
        return false;
    }

    // Check if all the values going diagonally are the same to determine a winner
    private boolean diagonalWin(int position) {
        Tile[] diagonal1 = new Tile[boardSize];
        Tile[] diagonal2 = new Tile[boardSize];
        int row = Math.floorDiv((position), boardSize);
        int col = position % boardSize;
        if (row == col) {
            for (int i = 0; i < boardSize; i++) {
                diagonal1[i] = board.getBoard()[i][i];
            }
            if (allEqualVal(diagonal1)) {
                return true;
            }
        }
        if (row + col == boardSize - 1) {
            for (int i = 0; i < boardSize; i++) {
                diagonal2[i] = board.getBoard()[i][(boardSize - 1) - i];
            }
            if (allEqualVal(diagonal2)) {
                return true;
            }
        }
        return false;
    }

    //Check if all the values in an array are the same value and not just a empty space
    private boolean allEqualVal(Tile[] arr) {
        if (arr[0].isEmpty()) {
            return false;
        }
        String[] values = new String[arr.length];
        int index = 0;
        for (Tile tile : arr) {
            values[index] = tile.getPiece().getPieceType();
            index++;
        }
        Set<String> s = new HashSet<>(Arrays.asList(values));
        if (s.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    // Check if there is a stalemate
    protected boolean staleMate() {
        return isBoardFull();
    }

    // Implements the run game from the boardgame super class to run the tic tac toe game
    protected void runGame() {
        Board currBoard = getBoard();

        for (Team team : getTeams()) {
            System.out.println(team.toString());
        }
        printBoardRules(currBoard);
        placementRules();
        while (!staleMate()) {
            boolean win = playOneTurn();
            if (win) {
                break;
            }
            if (staleMate()) {
                System.out.println("We have a draw!");
                scores.increaseDraw();
                setScores(scores);
                break;
            }
        }
    }


    // Implements the placement rules from the board game super class to print out the initial placement rules
    @Override
    protected void placementRules() {
        System.out.println("Please input a move in the following format: position (Example: entering 0 will result in O or X being placed in position 0 of the board");
    }

    // Implements the print correct format message from the board game super class to print out the correct format for a move
    @Override
    protected void printCorrectFormatMessage() {
        System.out.println("Please input a move in the following format: position");
    }

    // Implements the playOneTurn from the board game super class to run one turn of the tic tac toe game
    protected boolean playOneTurn() {
        try {
            String[] move = teamManager.promptTeam();
            int currMove = Integer.parseInt(move[0]);
            Team currentTeam = teamManager.getCurrentTeam();
            Piece pieceType = currentTeam.getPieces()[0];

            if (validMove(currMove)) {
                if (!currentTeam.hasPiece(pieceType)) {
                    System.out.println("You cannot place that piece down, please try again!");
                    return false;
                }
                board.fillTile(currMove, new Tile(pieceType));
                setBoard(board);
                printCurrentBoard();
                if (checkWin(currMove)) {
                    System.out.println("We have a winner! \n" + currentTeam.toString());
                    scores.updateScores(currentTeam);
                    setScores(scores);
                    teamManager.resetTurns();
                    return true;
                } else {
                    teamManager.changeTurn();
                }
            }
            return false;
        } catch (NumberFormatException err) {
            printCorrectFormatMessage();
            return playOneTurn();
        }
    }
}