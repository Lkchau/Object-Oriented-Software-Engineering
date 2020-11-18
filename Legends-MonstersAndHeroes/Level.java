// Representation of a character's level
public class Level {
    private int level;

    // Constructors
    public Level(int level){
        setLevel(level);
    }

    public Level(){
        this(1);
    }

    // getter and setter
    public int getLevel() {
        return level;
    }

    public void setLevel(int value) {
        this.level = value;
    }

    // compare two levels
    public boolean compareLevel(Level other){
        return this.level < other.level;
    }
    @Override
    public String toString() {
        return Integer.toString(level);
    }
}
