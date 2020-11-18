// Representation of a sorcerer hero in the game
public class Sorcerer extends Hero{

    // Constructors
    public Sorcerer(Name name, Health hp, Level level, boolean fainted, Money wallet, HeroStats stats, Mana mana, Experience exp, Inventory inv){
        super(name, hp, level, fainted, wallet, stats, mana, exp, inv);
    }

    public Sorcerer(Name name, Level level, boolean fainted, Money wallet, HeroStats stats, Mana mana, Experience exp, Inventory inv){
        super(name, level, fainted, wallet, stats, mana, exp, inv);
    }

    public Sorcerer(){
        super();
    }

    // level based on favored stats
    public void levelUp(){
        if(exp.getExp() >= getLevel().getLevel()*10 + 10){
            super.levelUp();
            this.stats.addAllStats(0.1, 0.1, 0.05);
        }

    }
}
