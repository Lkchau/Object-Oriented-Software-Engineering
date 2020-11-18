import java.util.ArrayList;
import java.util.List;

// Representation of a tile on a board that can be accessed or occupied
public class Tile {
    private boolean accessible;
    private boolean occupied;

    // List of party for maybe future implementation of multiple parties on a tile
    private List<Party> partiesOnTile;
    private final int DEFAULTTILELENGTH = 5;

    // Construtctors
    public Tile(boolean accessible, boolean occupied, ArrayList<Party> partiesOnTile){
        setAccessible(accessible);
        setOccupied(occupied);
        setPartiesOnTile(partiesOnTile);
    }
    public Tile(){
        this(true, false, new ArrayList<>());
    }

    // setters and getters
    public boolean isAccessible(){
        return accessible;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public int getTileLength(){
        return DEFAULTTILELENGTH;
    }

    public void setPartiesOnTile(List<Party> partiesOnTile) {
        this.partiesOnTile = partiesOnTile;
    }

    public List<Party> getPartiesOnTile() {
        return partiesOnTile;
    }

    // remove a certain party on the tile
    public void remove(Party p){
        partiesOnTile.remove(p);
    }


    public String toString(){
        return new String(new char[DEFAULTTILELENGTH]).replace("\0", " ");
    }
}
