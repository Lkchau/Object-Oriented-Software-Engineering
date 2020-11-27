import java.util.Objects;

// Money class to represent physical money, please note that the current implementation of blackjack, only USD is supported so that the calculations of conversions between currencies is not necessary
// future functionality can be added for currencies and possible chips to represent money
public class Money{

    private String currency;
    private int value;

    // No-args constructor (default currency is USD)
    public Money(){
        setCurrency("USD");
        setValue(0);
    }

    // Constructor for money
    public Money(String currency, int value){
        setCurrency(currency);
        setValue(value);
    }

    // Constructor for money with a certain value (default currency is USD)
    public Money(int value){
        this("USD", value);
    }

    // Constructor for money with a known currency (default value = 0)
    public Money(String currency){
        this(currency, 0);
    }

    // setter for the value of money
    public void setValue(int value) {
        this.value = value;
    }

    // getter for the value of money
    public int getValue() {
        return value;
    }

    // setter for the currency
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // getter for the currency
    public String getCurrency() {
        return currency;
    }

    // Check for equality between two money
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value == money.value &&
                Objects.equals(currency, money.currency);
    }

    // To String method to show the value and currency
    @Override
    public String toString(){
        return "$"+ value + " (" + currency + ")";
    }
}