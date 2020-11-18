// Representation for physical money used by heroes
public class Money {

    private int value;

    // Constructors
    public Money(int value){
        setValue(value);
    }

    public Money(){
        this(0);
    }


    // getter and setter
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // add or subtract money
    private void addOrSubtract(Money other, int sign){
        setValue(this.value + sign*other.value );
    }

    public void subtract(Money other){
        this.addOrSubtract(other,-1);
    }
    public void add(Money other){
        this.addOrSubtract(other,1);
    }

    // get the halved amount of money (used for monster drops)
    public Money halvedMoney(){
        return new Money(this.value/2);
    }
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
