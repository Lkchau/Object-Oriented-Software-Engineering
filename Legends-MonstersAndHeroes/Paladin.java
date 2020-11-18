// Representation of a Paladin hero in the game
public class Paladin extends Hero{
    public Paladin(Name name, Health hp, Level level, boolean fainted, Money wallet, HeroStats stats, Mana mana, Experience exp, Inventory inv){
        super(name, hp, level, fainted, wallet, stats, mana, exp, inv);
    }

    public Paladin(Name name, Level level, boolean fainted, Money wallet, HeroStats stats, Mana mana, Experience exp, Inventory inv){
        super(name, level, fainted, wallet, stats, mana, exp, inv);
    }

    public Paladin(){
        super();
    }

    // level up based on what paladins are favored in
    public void levelUp(){
        if(exp.getExp() >= getLevel().getLevel()*10 + 10){
            super.levelUp();
            this.stats.addAllStats(0.1, 0.1, 0.05);
        }

    }
}
