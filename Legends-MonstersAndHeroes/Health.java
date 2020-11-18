public class Health {

    // Representation of health that characters have
    private int health;

    // Constructors
    public Health(int hp){
        setHealth(hp);
    }

    public Health(){
        this(1);
    }

    // setters and getters
    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    // Increase and decrease health
    public void addHealth(double toAdd){
        this.health = (int) Math.round(this.health*toAdd);
    }

    public void addHealth(Health toAdd){
        this.health+= toAdd.health;
    }

    public void subtractHealth(int toSubtract){
        this.health -= toSubtract;
        if(health <= 0){
            health = 0;
        }
    }

    public String toString(){
        return ""+health;
    }
}
