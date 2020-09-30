import java.util.ArrayList;

public class Team {
    // Team class to store the players and keep track of which player in the team's turn it is currently
    private int teamNum;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int index = 0;
    private int numPlayers;
    private String role;
    private Piece[] pieces;

    // No-args constructor
    public Team(){
        this.teamNum = 0;
        this.players = null;
        this.numPlayers = 0;
        this.role = null;
        pieces = null;
        currentPlayer = null;
    }

    // Constructor for team given all the needed information
    public Team(ArrayList<Player> players, int numPlayers, int teamNum, String role, Piece[] pieces){
        setTeamNum(teamNum);
        setPlayers(players);
        setNumPlayers(numPlayers);
        setRole(role);
        setPieces(pieces);
        if(players.size() < 1){
            setCurrentPlayer(null);
        }
        else{
            setCurrentPlayer(players.get(0));
        }

    }

    // Constructor for team given all but the number of players
    public Team(ArrayList<Player> players, int teamNum, String role, Piece[] pieces){
        this(players,players.size(), teamNum, role, pieces);
    }

    // Constructor for a team without a list of already know players on the team
    public Team(int numPlayers, int teamNum, String role, Piece[] pieces){
        this(new ArrayList<Player>(),numPlayers, teamNum, role, pieces);

    }

    // Getter for number of players
    public int getNumPlayers(){
        return numPlayers;
    }

    // Setter for number of players
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    // Getter for players
    public ArrayList<Player> getPlayers(){
        return players;
    }

    // Setter for players
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    // Getter for the team number (identifier for the team)
    public int getTeamNum(){
        return teamNum;
    }

    // Setter for the team num
    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    // Getter for the current player
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // Setter for the current player
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    // Getter for the role
    public String getRole() {
        return role;
    }

    // Setter for the role
    public void setRole(String role) {
        this.role = role;
    }

    // Getter for the pieces the team can use
    public Piece[] getPieces() {
        return pieces;
    }

    // Setter for pieces
    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;
    }

    // Change who the current player is
    public void changePlayer(){
        index++;
        if(index >= players.size()){
            index = 0;
        }
        currentPlayer = players.get(index);
    }

    // Resets who is next to be the first player (happens whenever a new game starts)
    public void resetPlayer(){
        index = 0;
        setCurrentPlayer(players.get(0));
    }

    // Changes a specific player (For future possible use, was not used in this current implementation)
    private ArrayList<Player> changeSpecificPlayer(ArrayList<Player> players, Player newPlayer, Player oldPlayer){
        String oldPlayerName = oldPlayer.getPlayerName();
        for(int index = 0; index < players.size(); index++){
            String currPlayerName = players.get(index).getPlayerName();
            if(currPlayerName.equals(oldPlayerName)){
                players.set(index,newPlayer);
            }
        }
        return players;
    }

    // Add a player to the players list
    public void addPlayer(Player newPlayer){
        players.add(newPlayer);
    }

    // Add a player with a specific name
    public void addPlayerWithName(String newPlayer){
        addPlayer(new Player(newPlayer));
    }

    // Check if the team has access to a certain piece
    public boolean hasPiece(Piece piece){
        for(Piece p: pieces){
            if(piece.equals(p)){
                return true;
            }
        }
        return false;
    }

    // Returns a string with a readable format for a team
    public String toString(){
        StringBuilder playerString = new StringBuilder();
        playerString.append("Members in " + teamNum + ": \n");
        for(Player player: players){
            playerString.append(player.getPlayerName() + "\n");
        }
        return playerString.toString();
    }

}
