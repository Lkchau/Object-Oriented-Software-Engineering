public class IceSpell extends Spell{
    /*
        o An ice spell, apart from the damage it causes it also reduces the damage of
            the enemy.
    */

    // Representation of an ice spell
    public IceSpell(Name name, Money price, Level level, Damage damage, Mana mana){
        super(name, price, level, damage, mana);
    }

    public IceSpell(){
        this(new Name(), new Money(), new Level(), new Damage(), new Mana());
    }

    @Override
    public void cast(Buffable b) {
        super.cast(b);
    }
}
