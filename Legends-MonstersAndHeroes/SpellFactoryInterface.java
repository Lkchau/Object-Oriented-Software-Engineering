public interface SpellFactoryInterface {
    // Interface for spell factory
    public Spell getSpell(String spellType, Name spellName, Money price, Level level, Damage damage, Mana mana);
}
