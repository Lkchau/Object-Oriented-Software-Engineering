import java.util.HashMap;

// Weapon factory to return weapons
public class WeaponFactory extends ItemAbstractFactory{
    @Override
    public Item getItem(String itemType, String itemName) {
        if(itemType == null){
            return null;
        }
        else if(itemType.equalsIgnoreCase("WEAPON")){
            HashMap<String, String[]> itemInfo = FileParser.getFileParser().readMonstersAndHeroesHashNameToInfo(itemType + "ry");
            String[] currItemInfo = itemInfo.get(itemName.replaceAll(" ",""));
            assert currItemInfo.length == 4;
            Name weaponName = new Name(itemName);
            Money price = new Money(Integer.parseInt(currItemInfo[0]));
            Level level = new Level(Integer.parseInt(currItemInfo[1]));
            Damage damage = new Damage(Integer.parseInt(currItemInfo[2]));
            int handsNeeded = Integer.parseInt(currItemInfo[3]);
            return new Weapon(weaponName,price,level,damage, handsNeeded);
        }

        return null;
    }
}
