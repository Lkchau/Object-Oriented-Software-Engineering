public class Player {
    // Player class to represent a player
    private String playerName;

    // No-args constructor
    public Player(){
        playerName = null;
    }

    // Constructor
    public Player(String playerName){
        setPlayerName(playerName);
    }

    // Getter for player name
    public String getPlayerName() {
        return playerName;
    }

    // Setter for player name
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
