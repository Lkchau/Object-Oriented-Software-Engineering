import java.util.HashMap;
import java.util.List;
import java.util.Random;

// Concrete implementation of Map for the specific game Legends Monsters and heroes
public class LegendsMap extends Map{
    // Read external file for map proportions and generate map based off it
    HashMap<String, Double> proportions = getMapProportions();

    public LegendsMap(int size){
        super(size);
        generateMap();
    }
    public LegendsMap(){
        super();
        generateMap();
    }

    private void generateMap(){
        setBoard(new Board(mapSize));
        fillMap();
    }

    // Set a random location for the party (can start on market tiles)
    public int[] setRandomPartyLocation(Party p){
        return this.board.setRandomSpot(p);
    }

    // perform movement on the map
    public boolean move(Party p, String direction){
        int currRow = p.getCurrRow();
        int currCol = p.getCurrCol();
        int moveRow = currRow;
        int moveCol = currCol;

        switch(direction){
            case "w":
                moveRow--;
                break;
            case "a":
                moveCol--;
                break;
            case "s":
                moveRow++;
                break;
            case "d":
                moveCol++;
                break;
            default:
                break;
        }
        return moved(currRow, currCol, moveRow, moveCol, p);
    }

    private boolean moved(int currRow, int currCol, int moveRow, int moveCol, Party p){
        boolean moved = false;
        if(moveRow < 0 || moveRow >= board.getWidth() || moveCol < 0 || moveCol >= board.getHeight()){
            printOutOfMapError();
        }
        else{
            if(!board.getBoard()[moveRow][moveCol].isOccupied()){
                p.setCurrRow(moveRow);
                p.setCurrCol(moveCol);
                board.changeOccupancy(currRow,currCol, p, false);
                board.changeOccupancy(moveRow, moveCol, p, true);
                moved = true;
            }
            else{
                printOutOfMapError();
            }
        }
        return moved;
    }


    // Prints if user tries to go off map
    public void printOutOfMapError(){
        System.out.println(Colors.getColors().coloredString("red","Invalid move, trying to move out of map or into inaccessible area!") + " Please redo your turn");
    }

    // Weighted randomness to help make a randomized map
    public static <E> E weightedRandom(HashMap<E, Double> proportions) {
        Random r = new Random();
        E result = null;
        double max = Double.MAX_VALUE;
        for (E element : proportions.keySet()) {
            double value = -Math.log(r.nextDouble()) / proportions.get(element);
            if (value < max) {
                max = value;
                result = element;
            }
        }

        return result;
    }

    // Fill map based off of proportions and randomness
    private void fillMap(){
        TileFactory tileCreator = new TileFactory();
        for(int i = 0; i < board.getHeight(); i++){
            for(int j = 0; j < board.getWidth(); j++){
                Tile randomKey = tileCreator.getTile(weightedRandom(proportions));
                board.setTile(i,j, randomKey);
            }
        }
    }

    // Retrieve map proportions
    private HashMap<String, Double> getMapProportions(){
        FileParser fp = FileParser.getFileParser();
        HashMap<String, Double> mapProportion = new HashMap<>();
        List<String> proportionsFromList = fp.readFile("", "MapProportions");
        for(String prop: proportionsFromList){
            String[] propParts = prop.split(": ");
            String currKey = propParts[0];
            Double currValue = Double.parseDouble(propParts[1]);
            mapProportion.put(currKey, currValue);
        }
        return mapProportion;
    }

    // To be used by the toString to print out the representations of each tile on the map
    private String legend(){
        StringBuilder legend = new StringBuilder();
        legend.append(Colors.getColors().coloredString("purple", "\t\t\t\t\t\tL E G E N D\n"));
        TileFactory tile = new TileFactory();
        legend.append(tile.getTile("Inaccessible")+ ": NonAcessible\t");
        legend.append(tile.getTile("Market")+ ": Market\t");
        Tile occupiedTile = tile.getTile("Common");
        occupiedTile.setOccupied(true);
        legend.append(occupiedTile + ": Current party location");
        return legend.toString();
    }

    @Override
    public String toString() {
        return super.toString() + legend();
    }
}
