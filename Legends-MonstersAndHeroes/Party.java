import java.util.ArrayList;
import java.util.List;

// Representation of a group of characters, can be monsters or heroes
public class Party {

    private ConcreteMenuFactory mf = new ConcreteMenuFactory();
    private AbstractMenuFactory partyMenuFactory = mf.getMenu("CHARACTER");
    private Menu partyMenu;
    private List<Character> party;
    private String partyType;
    private int partySize;
    private int currRow;
    private int currCol;

    // Constructors
    public Party(List<Character> party, String partyType){
        setParty(party);
        setPartySize(party.size());
        setPartyMenu(partyMenuFactory.getMenu("",this));
        setPartyType(partyType);
    }

    public Party(){
        this(new ArrayList<>(), "Hero");
    }



    public void setPartyMenu(Menu partyMenu) {
        this.partyMenu = partyMenu;
    }

    public Menu getPartyMenu() {
        return partyMenu;
    }

    public void updatePartySize(){
        partySize = party.size();
    }

    public void setParty(List<Character> party) {
        this.party = party;
    }

    public List<Character> getParty() {
        return party;
    }

    // setters and getters
    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setCurrRow(int currRow) {
        this.currRow = currRow;
    }

    public int getCurrRow() {
        return currRow;
    }

    public void setCurrCol(int currCol) {
        this.currCol = currCol;
    }

    public int getCurrCol() {
        return currCol;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setLocation(int row, int col){
        setCurrRow(row);
        setCurrCol(col);
    }

    // Add a character to the party
    public void addCharacter(Character c){
        party.add(c);
        updatePartySize();
        setPartyMenu(partyMenuFactory.getMenu("",this));
    }

    // Print current location of party
    public void printCurrentLocation(){
        System.out.println("Current location on the map is: " + currRow + "x" + currCol + "\n");

    }

    // Get the highest leveled character in the party
    public Level getHighestLevel(){
        int level = party.get(0).level.getLevel();
        for(Character c: party){
            int currCharLevel = c.getLevel().getLevel();
            if(currCharLevel > level){
                level = currCharLevel;
            }
        }
        return new Level(level);
    }

    // check if all characters are fainted
    public boolean allFainted(){
        for(Character c: party){
            if(!c.isFainted()){
                return false;
            }
        }
        return true;
    }

    // Used for autochanging the target if an enemy dies (choose the first alive character)
    public Character getFirstAliveCharacter(){
        for(Character c: party){
            if(!c.isFainted()){
                return c;
            }
        }
        return null;
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
    @Override
    public String toString() {
        StringBuilder partyString = new StringBuilder();
        Colors color = Colors.getColors();
        if(partyType.equalsIgnoreCase("hero")) {
            partyString.append("The current hero party status: \n");
            partyString.append(padRight("Name",20));
            partyString.append(color.coloredString("blue", padRight("Mana",15)));
            partyString.append(color.coloredString("green", padRight("Health",15)));
            partyString.append(padRight("Strength",15));
            partyString.append(padRight("Agility",15));
            partyString.append(padRight("Dexterity",15));
            partyString.append(padRight("Experience",15));
            partyString.append(padRight("Level",15));
            partyString.append(padRight("Money",15));
            partyString.append(padRight("Inventory",15));
            partyString.append("\n");

            for(Character character: party){
                partyString.append(character.toString() + "\n");
            }
        }
        else if(partyType.equalsIgnoreCase("Monster")){
            partyString.append("The current enemy party status: \n");
            partyString.append(padRight("Name",20));
            partyString.append(color.coloredString("red", padRight("Health",15)));
            partyString.append(padRight("Damage",15));
            partyString.append(padRight("Defense",15));
            partyString.append(padRight("Dodge Chance",15));
            partyString.append(padRight("Level",15));
            partyString.append("\n");
            for(Character character: party){
                partyString.append(character.toString() + "\n");
            }

        }
        return partyString.toString();
        
    }

    public boolean equals(Party p){
        return this.party.equals(p.party);
    }
}
