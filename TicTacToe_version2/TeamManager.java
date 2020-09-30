import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TeamManager {
    // Class to keep track of turns and whose turn it is currently
    private Team[] teams;
    private Team currentTeam;
    private int index = 0;
    //    private Team firstTeam; <- Would be used for functionality to allow the user to explicitly choose the first team (unused)

    // No-args constructor
    public TeamManager(){
        teams = null;
        currentTeam = null;
    }

    // Constructor for a team manager
    public TeamManager(Team[] teams){
        setTeams(teams);
        setCurrentTeam(teams[0]);
    }

    // Getter for teams
    public Team[] getTeams() {
        return teams;
    }

    // Setter for teams
    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    // Getter for the current team
    public Team getCurrentTeam() {
        return currentTeam;
    }

    // Setter for the current team
    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    // Changes the turn by changing who the next player is on the current team and then switch to the next team
    public void changeTurn(){
        currentTeam.changePlayer();
        index++;
        if(index >= teams.length){
            index = 0;
        }
        currentTeam = teams[index];

    }

    // Reset it so that the current team is the first team (only used when a new game starts)
    public void resetTurns(){
        for(Team team: teams){
            team.resetPlayer();
        }
        index = 0;
        currentTeam = teams[index];
    }


    // Update the team manager
    public void updateTeamManager(Team[] teams){
        this.teams = teams;
        for(Team team: teams){
            team.resetPlayer();
        }
        index = 0;
        currentTeam = teams[index];

    }

    // Prompts the current team to take their turn
    public String[] promptTeam(){
        String currPrompt = "";
        while(true) {
            try {
                Scanner prompt = new Scanner(System.in);
                System.out.println("Player " + currentTeam.getCurrentPlayer().getPlayerName() + " Enter your move: ");
                currPrompt= prompt.nextLine();
                break;
            } catch (InputMismatchException err) {
                System.err.println("Invalid input, please input a value on the board");
                return promptTeam();
            } catch (NoSuchElementException err) {
                System.err.println("Invalid input, please input a value on the board");
                return promptTeam();
            }
        }
        return currPrompt.split(" ");
    }


}
