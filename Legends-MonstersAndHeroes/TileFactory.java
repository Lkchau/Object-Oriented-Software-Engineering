// Return tiles of certain types
public class TileFactory implements TileFactoryInterface{

    public Tile getTile(String tileType){
        if(tileType == null){
            return null;
        }
        if(tileType.equalsIgnoreCase("INACCESSIBLE")){
            return new InaccessibleTile();

        } else if(tileType.equalsIgnoreCase("COMMON")){
            return new CommonTile();

        } else if(tileType.equalsIgnoreCase("MARKET")){
            return new MarketTile();
        }

        return null;
    }
}

