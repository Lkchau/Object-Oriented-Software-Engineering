import java.util.HashMap;

// Rank class to represent the rank of a card
public class Rank{
    // Hard coded values for ranks of a card, this might be better to be defined elsewhere because the values of each rank could vary depending on the game
    public enum RANK {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};
    private final HashMap<RANK, int[]> RANKTOVALUE =
            new HashMap<RANK, int[]>() {{
                put(RANK.ACE, new int[]{1, 11});
                put(RANK.TWO, new int[]{2});
                put(RANK.THREE, new int[]{3});
                put(RANK.FOUR, new int[]{4});
                put(RANK.FIVE, new int[]{5});
                put(RANK.SIX, new int[]{6});
                put(RANK.SEVEN, new int[]{7});
                put(RANK.EIGHT, new int[]{8});
                put(RANK.NINE, new int[]{9});
                put(RANK.TEN, new int[]{10});
                put(RANK.JACK, new int[]{10});
                put(RANK.QUEEN, new int[]{10});
                put(RANK.KING, new int[]{10});

    }};
    private RANK rank;
    // a rank can have multiple values (ace)
    private int[] value;

    // no-args constructor
    public Rank(){
        setRank(null);
        setValue(new int[]{0});
    }

    // Constructor with a known rank
    public Rank(RANK rank){
        setRank(rank);
        setValue(RANKTOVALUE.get(rank));
    }

    // setter for the rank
    public void setRank(RANK value) {
        this.rank = value;
    }

    // getter for the rank
    public RANK getRank() {
        return rank;
    }

    // setter for the value of the rank
    public void setValue(int[] value) {
        this.value = value;
    }

    // getter for the values of the rank
    public int[] getValue() {
        return value;
    }

    // check if the rank is valid or not
    public boolean isValidRank(RANK rank) {
        for (RANK e : RANK.values()) {
            if(e.name().equals(rank.name())) { return true; }
        }
        return false;
    }

    // check for equality between ranks, knowing that the object we are comparing to is a rank
    public boolean equals(Rank rank){
        return this.rank.name().equals(rank.rank.name());
    }

    // check for equality between ranks
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rank rank1 = (Rank) o;
        return rank.name().equals(rank1.rank.name());
    }

    // to string for the rank to display for the user
    public String toString(){
        return rank.name();
    }
}