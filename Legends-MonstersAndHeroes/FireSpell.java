public class FireSpell extends Spell {
    /*
    o A fire spell, apart from the damage it causes it also reduces the defense of
        the enemy.
     */

    // Representation of a physical fire spell
    public FireSpell(Name name, Money price, Level level, Damage damage, Mana mana){
        super(name, price, level, damage, mana);
    }

    public FireSpell(){
        this(new Name(), new Money(), new Level(), new Damage(), new Mana());
    }
    @Override
    public void cast(Buffable b) {
        super.cast(b);
    }
}
