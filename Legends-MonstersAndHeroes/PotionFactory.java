import java.util.HashMap;

// Potion factory to return different potions
public class PotionFactory extends ItemAbstractFactory{
    @Override
    Item getItem(String itemType, String itemName) {
        HashMap<String, String[]> itemInfo = FileParser.getFileParser().readMonstersAndHeroesHashNameToInfo(itemType + "s");
        String[] currItemInfo = itemInfo.get(itemName.replaceAll(" ",""));
        assert currItemInfo.length == 4;
        Name armorName = new Name(itemName);
        Money price = new Money(Integer.parseInt(currItemInfo[0]));
        Level level = new Level(Integer.parseInt(currItemInfo[1]));
        Buff buff = new Buff();
        buff.setBuffAmount(Integer.parseInt(currItemInfo[2]));

        String[] statsToBuff = currItemInfo[3].split("/");

        for(String toBuff: statsToBuff){
            buff.getStatsToBuff().add(toBuff);
        }
        return new Potion(armorName,price,level, buff);
    }
}
