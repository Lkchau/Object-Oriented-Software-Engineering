// Abstract idea of a rpg with a map
public abstract class RolePlayingGameWithMap extends RolePlayingGame{
    protected Map map;

    public RolePlayingGameWithMap(String mapType){
        super();
        MapFactory mf = new MapFactory();
        map = mf.getMap(mapType);
    }

    public RolePlayingGameWithMap(){
        this("");
    }

    @Override
    public abstract void printInstructions();

    @Override
    public abstract void run();
}
