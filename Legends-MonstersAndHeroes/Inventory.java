import java.util.ArrayList;
import java.util.List;

// Representation of a character's inventory
public class Inventory {

    private List<Item> inventory;
    private Weapon equippedWeapon;
    private Armor equippedArmor;

    public Inventory(List<Item> inv, Weapon equippedWeapon, Armor equippedArmor){
        setInventory(inv);
        setEquippedArmor(equippedArmor);
        setEquippedWeapon(equippedWeapon);
    }

    public Inventory(){
        this(new ArrayList<Item>(), new Weapon(), new Armor());
    }

    public void setEquippedArmor(Armor equippedArmor) {
        this.equippedArmor = equippedArmor;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void changeWeapon(){

    }

    public void changeArmor(){

    }

    public List<Item> getSpells(){
        List<Item> spells = new ArrayList<>();
        for(Item it: inventory){
            if(it instanceof Spell){
                spells.add(it);
            }
        }
        return spells;
    }

    public List<Item> getPotions(){
        List<Item> potions = new ArrayList<>();
        for(Item it: inventory){
            if(it instanceof Potion){
                potions.add(it);
            }
        }
        return potions;
    }

    public List<Item> getEquips(){
        List<Item> equips = new ArrayList<>();
        for(Item it: inventory){
            if(it instanceof Equippable){
                equips.add(it);
            }
            System.out.println(it);
        }
        return equips;
    }

    public void addItem(Item i){
        inventory.add(i);
    }

    public Item getByItemName(String name){
        for(Item item : inventory){
            if(item.getName().toString().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Currently Equipped Armor: " + equippedArmor +"\t|");
        str.append("Currently Equipped Weapon: " + equippedWeapon+ "\t|");
        for(Item i: inventory){
            str.append(i.toString() + "\t");
        }
        return str.toString();
    }
}
