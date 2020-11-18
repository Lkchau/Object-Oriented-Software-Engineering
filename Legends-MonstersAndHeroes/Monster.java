import java.util.Random;

// Representation of a monster enemy in the game
public class Monster extends Character implements Debuffable{

    private Damage damage;
    private Damage defense;
    private int dodgeChance;

    // Constructors
    public Monster(Name name, Health hp, Level level, boolean fainted, Damage damage, Damage defense, int dodgeChance){
        super(name,hp,level,fainted);
        setDamage(damage);
        setDefense(defense);
        setDodgeChance(dodgeChance);
    }

    public Monster(Name name, Level level, boolean fainted, Damage damage, Damage defense, int dodgeChance){
        super(name,level, fainted);
        setHp(new Health(level.getLevel()*100));
        setDamage(damage);
        setDefense(defense);
        setDodgeChance(dodgeChance);
    }

    public Monster(Name name, Level level, Damage damage, Damage defense, int dodgeChance){
        this(name,level,false,damage,defense,dodgeChance);
    }

    public Monster(){
        this(new Name(), new Level(), false, new Damage(), new Damage(), 0);
    }

    // Setters and getters
    public void setDamage(Damage damage) {
        this.damage = damage;
    }

    public Damage getDamage() {
        return damage;
    }

    public void setDefense(Damage defense) {
        this.defense = defense;
    }

    public Damage getDefense() {
        return defense;
    }

    public void setDodgeChance(int dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    public int getDodgeChance() {
        return dodgeChance;
    }

    // apply a debuff based on the spell cast on the character
    @Override
    public void debuff(Spell spell){
        boolean dodged = calculateDodge();
        if(!dodged){
            if(spell instanceof IceSpell){
                damage.reduceDamage(0.1);
            }
            else if(spell instanceof FireSpell){
                defense.reduceDamage(0.1);
            }
            else if(spell instanceof LightningSpell){
                dodgeChance = dodgeChance - (int) Math.round(dodgeChance * 0.1);
            }
            this.receiveDamage(spell.getDamage(), dodged);
        }

    }

    // Receive damage from attacks
    @Override
    public void receiveDamage(Damage damage) {
        if(!calculateDodge()){
            int damageValue = damage.getDamage();
            int netDamageTaken = (int) Math.round((damageValue - defense.getDamage())*0.05);
            if(netDamageTaken < 0){
                netDamageTaken = 0;
            }
            this.hp.subtractHealth(netDamageTaken);
            System.out.println(this.name + " has taken " + Colors.getColors().coloredString("red", netDamageTaken+"") + " damage!");
        }
        else{
            System.out.println(this.name + " has " +Colors.getColors().coloredString("green","dodged")+ " the attack!");
        }
        if(hp.getHealth() <= 0){
            setFainted(true);
        }
    }
    public void receiveDamage(Damage damage, boolean dodged) {
        if(!dodged){
            int damageValue = damage.getDamage();
            int netDamageTaken = (int) Math.round((damageValue - defense.getDamage())*0.05);
            if(netDamageTaken < 0){
                netDamageTaken = 0;
            }
            this.hp.subtractHealth(netDamageTaken);
            System.out.println(this.name + " has taken " + Colors.getColors().coloredString("red", netDamageTaken+"") + " damage!");
        }
        else{
            System.out.println(this.name + " has " +Colors.getColors().coloredString("green","dodged")+ " the attack!");
        }
        if(hp.getHealth() <= 0){
            setFainted(true);
        }
    }

    // Calculate dodge chance
    @Override
    public boolean calculateDodge() {
        Random r = new Random();
        double dodgeChance = (this.dodgeChance*0.01)*100;
        return r.nextDouble()*100 <= dodgeChance;
    }

    // attack an enemy
    public void attack(Attackable enemy){
        Damage damageDealt = new Damage();
        Damage monsterDamage = this.getDamage();
        damageDealt.setDamage(monsterDamage.getDamage());
        enemy.receiveDamage(damageDealt);
    }

    // Help with formatting output
    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public String toString(){
        Colors color = Colors.getColors();
        StringBuilder monsterString = new StringBuilder();
        monsterString.append(padRight(name.toString(),20));
        monsterString.append(color.coloredString("red", padRight(hp.toString(),15)));
        monsterString.append(padRight(damage.toString(),15));
        monsterString.append(padRight(defense.toString(),15));
        monsterString.append(padRight(dodgeChance+"",15));
        monsterString.append(padRight(level.toString(),15));
        return monsterString.toString();
    }
}
