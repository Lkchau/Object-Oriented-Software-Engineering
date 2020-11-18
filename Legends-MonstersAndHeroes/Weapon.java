// Representation of an equippable weapon
public class Weapon extends Item implements Equippable{

    // handsNeeded is not used currently, would be useful for future implementation of dual wielding weapons
    private int handsNeeded;

    // Constructors
    public Weapon(Name name, Money price, Level level, Damage damage, int hands){
        super(name,price,level,damage);
        setHandsNeeded(hands);
    }

    public Weapon(){
        this(new Name(),new Money(), new Level(), new Damage(), 1);
    }

    // getter and setter
    public int getHandsNeeded() {
        return handsNeeded;
    }

    public void setHandsNeeded(int handsNeeded) {
        this.handsNeeded = handsNeeded;
    }

    // Unused
    @Override
    public void equip(Character character) {

    }
}
