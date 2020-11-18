public class Damage {
    // Represent damage to be dealt to others (also used to represent defense as well)
    private int damage;

    // Constructors
    public Damage(int damage){
        setDamage(damage);
    }

    public Damage(){
        this(0);
    }

    // Setter and getter
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // Decrease damage
    public void reduceDamage(double dmgReduce){
        setDamage(damage - (int) Math.round(this.damage*dmgReduce));
    }
    @Override
    public String toString() {
        return Integer.toString(damage);
    }
}
