import java.util.HashMap;

public class ScoreBoard {
    // Scoreboard class to store the scores of each team
    private HashMap<Team,Integer> teamToScores;
    private int totalGames;
    private int draws;

    // No-args constructor
    public ScoreBoard(){
        teamToScores = new HashMap<>();
        totalGames = 0;
        draws = 0;
    }

    // Constructor for a score board with a known number of total games and a known number of draws
    public ScoreBoard(Team[] teams, int totalGames, int draws){
        teamToScores = new HashMap<>();
        Team[] reversed = reversedTeams(teams);
        for(int index = 0; index < teams.length;index++){
            teamToScores.put(reversed[index],0);
        }
        this.totalGames = totalGames;
        this.draws = draws;
    }

    // Constructor for 0 total games and 0 draws
    public ScoreBoard(Team[] teams){
        this(teams,0,0);
    }

    // Getter for teams
    public Team[] getTeams() {
        return teamToScores.keySet().toArray(new Team[0]);
    }

    // Getter for draws
    public int getDraws() {
        return draws;
    }

    // Getter for totalGames
    public int getTotalGames() {
        return totalGames;
    }

    // Getter for teamToScores
    public HashMap<Team, Integer> getTeamToScores() {
        return teamToScores;
    }

    // Setter for draws;
    public void setDraws(int draws) {
        this.draws = draws;
    }

    // Setter for teamToScores
    public void setTeamToScores(HashMap<Team, Integer> teamToScores) {
        this.teamToScores = teamToScores;
    }

    // Setter for total games
    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    // Resets the teams and resets the scores to be 0
    public void resetTeams(Team[] newTeams){
        teamToScores.clear();
        Team[] reversed = reversedTeams(newTeams);
        for(int index = 0; index < newTeams.length;index++){
            teamToScores.put(reversed[index],0);
        }
    }

    // Upadates the teams and clears the scoreboard
    public void clearScores(Team[] newTeams){
        resetTeams(newTeams);
        totalGames = 0;
        draws = 0;
    }

    // Updates the scores
    public void updateScores(Team winner){
        totalGames++;
        teamToScores.replace(winner,teamToScores.get(winner)+1);
    }

    // Increases the amount of draws occurred and increases the total number of game
    public void increaseDraw(){
        draws++;
        totalGames++;
    }

    // Method to reverse the teams (This is only used so that the teams can be inserted into the hashmap in numerical order)
    private Team[] reversedTeams(Team[] teams){
        Team[] reversed = new Team[teams.length];
        for(int i = 0; i < teams.length; i++){
            reversed[i] = teams[teams.length-i-1];
        }
        return reversed;
    }

    // ToString method to return the scoreboard as a string
    public String toString(){
        if(teamToScores.isEmpty()){
            return "The Scoreboard is empty! No games have been played yet.";
        }

        StringBuilder scoreBoardString = new StringBuilder();
        scoreBoardString.append("\nScore Board: \n");
        scoreBoardString.append("=======================================================\n");

        for(Team key: teamToScores.keySet()){
            scoreBoardString.append("Team " + key.getTeamNum() + ": " + teamToScores.get(key) + "\n");
        }
        scoreBoardString.append("Draws: " + draws + "\n");
        scoreBoardString.append("Total Games: " + totalGames + "\n");
        scoreBoardString.append("=======================================================\n");
        return scoreBoardString.toString();
    }

    
}
