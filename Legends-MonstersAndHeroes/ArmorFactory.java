import java.util.HashMap;

// Class to create and return armor based off of what type of item it is and the name of the armor
public class ArmorFactory extends ItemAbstractFactory{
    // Retrieve the armor
    @Override
    public Item getItem(String itemType, String itemName) {
        if(itemType == null){
            return null;
        }
        else if(itemType.equalsIgnoreCase("ARMOR")){
            HashMap<String, String[]> itemInfo = FileParser.getFileParser().readMonstersAndHeroesHashNameToInfo(itemType + "y");
            String[] currItemInfo = itemInfo.get(itemName.replaceAll(" ",""));
            assert currItemInfo.length == 3;
            Name armorName = new Name(itemName);
            Money price = new Money(Integer.parseInt(currItemInfo[0]));
            Level level = new Level(Integer.parseInt(currItemInfo[1]));
            Damage damage = new Damage(Integer.parseInt(currItemInfo[2]));
            return new Armor(armorName,price,level,damage);
        }

        return null;
    }
}
