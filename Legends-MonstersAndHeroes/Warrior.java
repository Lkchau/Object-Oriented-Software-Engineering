// Representation of a warrior archetype in the game
public class Warrior extends Hero{
    // Constructors
    public Warrior(Name name, Health hp, Level level, boolean fainted, Money wallet, HeroStats stats, Mana mana, Experience exp, Inventory inv){
        super(name, hp, level, fainted, wallet, stats, mana, exp, inv);
    }

    public Warrior(Name name, Level level, boolean fainted, Money wallet, HeroStats stats, Mana mana, Experience exp, Inventory inv){
        super(name, level, fainted, wallet, stats, mana, exp, inv);
    }

    public Warrior(){
        super();
    }

    // Level up based on favored stats
    public void levelUp(){
        if(exp.getExp() >= getLevel().getLevel()*10 + 10){
            super.levelUp();
            this.stats.addAllStats(0.1, 0.1, 0.05);
        }

    }
}
