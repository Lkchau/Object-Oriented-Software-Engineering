import java.util.HashMap;
// Game Factory to return certain games (theres only one in this app)
public class GameFactory implements GameFactoryInterface{

    private HashMap<String,Game> games = new HashMap<String,Game>();
    private HashMap<Integer, String> indexGame = new HashMap<Integer, String>();

    public GameFactory(){
        games.put(LegendsMonstersAndHeroes.class.getName(), new LegendsMonstersAndHeroes());
        int index = 1;
        for(String str: games.keySet()){
            indexGame.put(index,str);
            index++;
        }
    }
    @Override
    public Game getGame(String gameName) {
        return games.get(gameName);
    }

    public String lookUpIndex(int num){
        return indexGame.get(num);
    }

    public void printGameOptions(){
        Colors color = Colors.getColors();
        System.out.println("Please choose a game from one of the options:\n");
        for(Integer gameNum: indexGame.keySet()){
            System.out.println("(" + color.coloredString("blue",gameNum.toString()) + ") " + indexGame.get(gameNum));
        }
    }
}
