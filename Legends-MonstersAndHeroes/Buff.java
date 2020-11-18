import java.util.ArrayList;
import java.util.List;

// Buff class to represent buffs that can be applied to the player
public class Buff {
    // It has certain stats it can buff and how much to buff it by
    private List<String> statsToBuff;
    private int buffAmount;

    // Constructors
    public Buff(ArrayList<String> toBuff, int buffAmount){
        setStatsToBuff(toBuff);
        setBuffAmount(buffAmount);
    }

    public Buff(){
        this(new ArrayList<>(),0);
    }

    // Setters and getters
    public void setBuffAmount(int buffAmount) {
        this.buffAmount = buffAmount;
    }

    public void setStatsToBuff(List<String> statsToBuff) {
        this.statsToBuff = statsToBuff;
    }

    public int getBuffAmount() {
        return buffAmount;
    }

    public List<String> getStatsToBuff() {
        return statsToBuff;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(String stat: statsToBuff){
            str.append(stat + "/");
        }
        str.append(buffAmount);
        return str.toString();
    }
}
