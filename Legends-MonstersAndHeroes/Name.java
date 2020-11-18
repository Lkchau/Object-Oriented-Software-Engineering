// Representation of a character or item's name
public class Name {

    private String name;

    // Constructors
    public Name(String s){
        setName(s);
    }

    public Name(){
        this("");
    }

    // Setter and getter
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean equals(Name otherName){
        return this.name.equals(otherName.name);
    }
}
