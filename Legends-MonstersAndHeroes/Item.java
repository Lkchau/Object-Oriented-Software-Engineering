// Abstract representation of different kinds of items, to be implemented by concrete items
public abstract class Item {
    private Name name;
    private Money price;
    private Level level;
    private Damage damage;

    // Constructors
    public Item(Name name, Money price, Level level, Damage damage){
        setName(name);
        setPrice(price);
        setLevel(level);
        setDamage(damage);
    }
    public Item(){
        this(new Name(),new Money(), new Level(), new Damage());
    }

    // getters and setters
    public Damage getDamage() {
        return damage;
    }

    public void setDamage(Damage damage) {
        this.damage = damage;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String toString(){
        return this.name.toString();
    }
}
