public class Experience {
    // representation of Experience points that the character can gain
    private int exp;

    public Experience(int exp){
        setExp(exp);
    }

    public Experience(){
        this(0);
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExp() {
        return exp;
    }

    // Increase experience by a certain amount
    public void add(int toAdd){
        this.exp+=toAdd;
    }

    public String toString(){
        return ""+getExp();
    }
}
