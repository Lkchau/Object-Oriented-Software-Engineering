public abstract class Game {
    protected Team[] teams;
    protected ScoreBoard scores;
    protected String name;

    public Game(){
        teams = null;
        scores = null;
        name = null;
    }

    public Game(Team[] teams,ScoreBoard scores, String name){
        this.teams = teams;
        this.scores = scores;
        this.name = name;
    }

    protected Team[] getTeams(){
        return teams;
    }
    protected ScoreBoard getScores(){
        return scores;
    }
    protected String getName(){
        return name;
    }
    protected void printStatistics(){
        System.out.println(scores.toString());
    }


    protected abstract Boolean checkWin();



}
