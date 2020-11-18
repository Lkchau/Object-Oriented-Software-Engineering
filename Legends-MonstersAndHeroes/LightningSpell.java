public class LightningSpell extends Spell{
    /*
    A lightning spell, apart from the damage it causes it also reduces the
        dodge chance of the enemy.
     */

    // A representation of a lightning spell
    public LightningSpell(Name name, Money price, Level level, Damage damage, Mana mana){
        super(name, price, level, damage, mana);
    }

    public LightningSpell(){
        this(new Name(), new Money(), new Level(), new Damage(), new Mana());
    }
    @Override
    public void cast(Buffable b) {
        super.cast(b);
    }
}
