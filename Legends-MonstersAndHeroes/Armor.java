public class Armor extends Item implements Equippable {
    /*
    Assignment Description: An armor, which when worn by a hero, reduces the incoming damage from
    enemy’s attacks. An armor has a price, a name and a minimum hero level as a
    requirement to be used.
    */

    // Class to represent equippable armor.
    public Armor(Name name, Money price, Level level, Damage damage){
        super(name,price,level,damage);
    }

    public Armor(){
        this(new Name(),new Money(), new Level(), new Damage());
    }

    // Didn't end up using this, because I forgot about it and implemented in the Battle class... the battle class does too much now
    @Override
    public void equip(Character character) {

    }
}
