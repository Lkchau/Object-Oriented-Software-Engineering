public class ItemFactory implements ItemFactoryInterface{

    // retrieves an item factory to help create concrete items
    @Override
    public ItemAbstractFactory getItemAbstractFactory(String itemType) {
        if(itemType.equals(null));
        else if(itemType.equalsIgnoreCase("SPELL")){
            return new SpellFactory();
        }else if(itemType.equalsIgnoreCase("ARMOR")){
            return new ArmorFactory();
        }
        else if(itemType.equalsIgnoreCase("POTION")){
            return new PotionFactory();
        }
        else if(itemType.equalsIgnoreCase("WEAPON")){
            return new WeaponFactory();
        }
        return null;
    }
}
