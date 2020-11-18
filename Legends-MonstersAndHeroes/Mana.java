// Representation of mana, a resource used by heros in the game legends monsters and heroes
public class Mana {

    private int mana;

    public Mana(int mana){
        setMana(mana);
    }

    public Mana(){
        this(0);
    }

    // getter and setter
    public int getMana() {
        return mana;
    }

    public void setMana(int value) {
        this.mana = value;
    }

    // increase mana
    public void addMana(double toAdd){
        mana = (int) Math.round(mana*toAdd);
    }

    public void addMana(Mana toAdd){
        mana += toAdd.getMana();
    }
    @Override
    public String toString() {
        return Integer.toString(mana);
    }
}
