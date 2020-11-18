// Represents Strength, which is a certain stat
public class Strength extends Stat{
    // Constructors
    public Strength(int value){
        super(value);
    }
    public Strength(){
        super();
    }

    // increase strength
    public void addStr(double toAdd){
        this.addStat(toAdd);
    }
    public void addStr(Strength toAdd){
        this.addStat(toAdd);
    }

    public String toString(){
        return ""+getStatValue();
    }
}
