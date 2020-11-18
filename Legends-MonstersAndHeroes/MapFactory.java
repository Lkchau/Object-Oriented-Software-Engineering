public class MapFactory implements MapFactoryInterface{
    // Map factory to return different types of map, this app only has Legends currently!
    public Map getMap(String mapType) {
        if (mapType == null) {
            return null;
        }
        if (mapType.equalsIgnoreCase("LEGENDS")) return new LegendsMap();
        return null;
    }

    public Map getMap(String mapType, int mapSize) {
        if (mapType == null) {
            return null;
        }
        if (mapType.equalsIgnoreCase("LEGENDS")) return new LegendsMap(mapSize);
        return null;
    }
}
