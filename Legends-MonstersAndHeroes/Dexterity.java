public class Dexterity extends Stat{

    // Represen the dexterity stat a hero can have
    public Dexterity(int value){
        super(value);
    }
    public Dexterity(){
        super();
    }

    public void addDex(double statToAdd){
        this.addStat(statToAdd);
    }

    public void addDex(Dexterity statToAdd){
        this.addStat(statToAdd);
    }

    public String toString(){
        return ""+getStatValue();
    }
}
