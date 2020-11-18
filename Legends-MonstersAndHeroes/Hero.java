import java.util.Random;

// Concrete representation of a character and has a lot more info that needs to be kept track of and is also buffable!
public class Hero extends Character implements Buffable{

    protected HeroStats stats;
    protected Mana mana;
    protected Money wallet;
    protected Experience exp;
    protected Inventory inv;
    protected Health baseHP;
    protected Mana baseMana;
    protected Damage defense;

    // Constructors
    public Hero(Name name, Health hp, Level level, boolean fainted, Money wallet, HeroStats stats, Mana mana, Experience exp, Inventory inv){
        super(name, hp, level, fainted);
        setStats(stats);
        setMana(mana);
        setWallet(wallet);
        setExp(exp);
        setInv(inv);
        setBaseHP(new Health(this.getHp().getHealth()));
        setBaseMana(new Mana(this.getMana().getMana()));
        setDefense(new Damage());

    }

    public Hero(Name name, Level level, boolean fainted, Money wallet, HeroStats stats, Mana mana, Experience exp, Inventory inv){
        super(name, level, fainted);
        setStats(stats);
        setMana(mana);
        setWallet(wallet);
        setExp(exp);
        setInv(inv);
        setBaseHP(new Health(this.getHp().getHealth()));
        setBaseMana(new Mana(this.getMana().getMana()));
        setDefense(new Damage());
    }

    public Hero()
    {
        this(new Name(), new Level(), false, new Money(), new HeroStats(), new Mana(), new Experience(), new Inventory());
    }

    // Setters and getters
    public void setDefense(Damage defense) {
        this.defense = defense;
    }

    public Damage getDefense() {
        return defense;
    }

    public Mana getMana() {
        return mana;
    }

    public void setMana(Mana mana) {
        this.mana = mana;
    }

    public void setBaseMana(Mana baseMana) {
        this.baseMana = baseMana;
    }

    public Mana getBaseMana() {
        return baseMana;
    }

    public Money getWallet() {
        return wallet;
    }

    public void setWallet(Money wallet) {
        this.wallet = wallet;
    }

    public void setStats(HeroStats stats) {
        this.stats = stats;
    }

    public HeroStats getStats() {
        return stats;
    }

    public Experience getExp() {
        return exp;
    }

    public void setExp(Experience exp) {
        this.exp = exp;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public Health getBaseHP() {
        return baseHP;
    }

    public void setBaseHP(Health baseHP) {
        this.baseHP = baseHP;
    }

    // Attack an attackable item
    public void attack(Attackable enemy){
        Damage damageDealt = new Damage();
        Strength heroStrength = this.getStats().getStr();
        Damage weaponDamage = this.getInv().getEquippedWeapon().getDamage();
        damageDealt.setDamage((heroStrength.getStatValue() + weaponDamage.getDamage()));
        enemy.receiveDamage(damageDealt);
    }

    // Apply a buff to self
    public void buff(String stat, int amountToBuff){
        switch (stat) {
            case "Health":
                this.hp.addHealth(new Health(amountToBuff));
                break;
            case "Mana":
                this.mana.addMana(new Mana(amountToBuff));
                break;
            case "Strength":
                this.stats.addStr(new Strength(amountToBuff));
                break;
            case "Dexterity":
                this.stats.addDex(new Dexterity(amountToBuff));
                break;
            case "Defense":
                this.getDefense().setDamage(this.getDefense().getDamage()+amountToBuff);
                break;
            case "Agility":
                this.stats.addAgl(new Agility(amountToBuff));

                break;
        }
    }

    // Calculate and decrease health based on damage of another entity
    public void receiveDamage(Damage enemyDamage){

        if(calculateDodge()){
            int netDamageTaken = 0;
            int damageValue = enemyDamage.getDamage();
            if(inv.getEquippedWeapon() == null){
                netDamageTaken = (int) Math.round(damageValue*0.05);
            }
            else{
                netDamageTaken = (int) Math.round((damageValue - inv.getEquippedArmor().getDamage().getDamage() - defense.getDamage())*0.05);
            }
            if(netDamageTaken < 0){
                netDamageTaken = 0;
            }
            this.hp.subtractHealth(netDamageTaken);
            System.out.println(this.name + " has taken " + Colors.getColors().coloredString("red", netDamageTaken+"") + " damage!");
        }else{
            System.out.println(this.name + " has " +Colors.getColors().coloredString("green","dodged")+ " the attack!");
        }
        if(hp.getHealth() <= 0){
            setFainted(true);
        }

    }

    // Calculate whether or not hero dodges based on their dodgechance
    public boolean calculateDodge(){
        Random r = new Random();
        double dodgeChance = (this.stats.getAgl().getStatValue()*0.002)*100;
        return r.nextDouble()*100 <= dodgeChance;
    }

    // Restore stats, not used
    private void restoreHP(double amountToRestore){
        this.hp.addHealth(amountToRestore);
    }

    private void restoreMana(double amountToRestore){
        this.mana.addMana(amountToRestore);

    }

    // Revive a hero and reset health to half the base
    public void revive(){
        setFainted(false);
        setHp(new Health(baseHP.getHealth()/2));
        System.out.println(this.name+" Revived");
    }

    // Level up a character, any excess health will be gotten rid of and reset to the new base health
    public void levelUp(){
        if(exp.getExp() >= getLevel().getLevel()*10 + 10){
            this.setLevel(new Level(level.getLevel()+1));
            this.setHp(new Health(level.getLevel()*100));
            this.setBaseHP(new Health(hp.getHealth()));
            this.setMana(new Mana((int) Math.round(mana.getMana()*1.1)));
            System.out.println(Colors.getColors().coloredString("yellow", this.name + "   H A S   L E V E L E D   U P   T O   " + this.level));
        }
    }

    // Function to help pad strings and format it!
    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public String toString(){
        Colors color = Colors.getColors();
        StringBuilder heroString = new StringBuilder();
        heroString.append(padRight(name.toString(),20));
        heroString.append(color.coloredString("blue", padRight(mana.toString(),15)));
        heroString.append(color.coloredString("green", padRight(hp.toString(),15)));
        heroString.append(padRight(stats.getStr().toString(),15));
        heroString.append(padRight(stats.getAgl().toString(),15));
        heroString.append(padRight(stats.getDex().toString(),15));
        heroString.append(padRight(exp.toString(),15));
        heroString.append(padRight(level.toString(),15));
        heroString.append(padRight(wallet.toString(),15));
        heroString.append(inv);
        return heroString.toString();
    }


}

