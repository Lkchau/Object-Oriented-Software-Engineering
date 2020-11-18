import java.util.HashMap;
import java.util.List;

// Representation of a Market in Legends Monsters and Heroes
public class Market {

    // The attributes probably could have been handled better
    // NOTE: markets have infinite money, would be more realistic to have a starting amount of money
    private ConcreteMenuFactory mf = new ConcreteMenuFactory();
    private AbstractMenuFactory nonCharacterFactory = mf.getMenu("MENU");
    private Menu armors;
    private Menu weapons;
    private Menu potions;
    private Menu fireSpells;
    private Menu iceSpells;
    private Menu lightningSpells;
    private HashMap<String,Item> armorsMap = new HashMap<>();
    private HashMap<String,Item> weaponsMap= new HashMap<>();
    private HashMap<String,Item> potionsMap= new HashMap<>();
    private HashMap<String,Item> fireSpellsMap= new HashMap<>();
    private HashMap<String,Item> iceSpellsMap= new HashMap<>();
    private HashMap<String,Item> lightningSpellsMap= new HashMap<>();

    // Constructor
    public Market(){
        generateMarket();
    }

    // Generate market based off files (Every single market will contain the same items and heroes can obtain multiple copies)
    private void generateMarket(){
        FileParser fp = FileParser.getFileParser();
        armors = nonCharacterFactory.getMenu("BuyArmorMenu", null);
        weapons = nonCharacterFactory.getMenu("BuyWeaponMenu", null);
        potions = nonCharacterFactory.getMenu("BuyPotionMenu", null);
        fireSpells = nonCharacterFactory.getMenu("BuyFireSpellMenu", null);
        iceSpells = nonCharacterFactory.getMenu("BuyIceSpellMenu", null);
        lightningSpells = nonCharacterFactory.getMenu("BuyLightningSpellMenu", null);
        initializeArmors(fp);
        initializeWeapons(fp);
        initializePotions(fp);
        initializeSpells(fp);
    }

    // allow a character to buy an item
    public void buyItem(String itemType, Party p){
        Character charInfo = (UserPrompt.getPrompt().promptMenuOptionsValueChar(p.getPartyMenu(),false));
        removeItem(itemType, charInfo);
    }

    // Helpful for padding and formatting strings
    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    // allow a character to sell an item
    public void sellItem(Party p){
        Character charInfo = (UserPrompt.getPrompt().promptMenuOptionsValueChar(p.getPartyMenu(),false));
        if(charInfo instanceof Hero) {
            Hero hero = ((Hero) charInfo);
            List<Item> chosenHeroInventory = hero.getInv().getInventory();
            if (chosenHeroInventory.size() != 0) {
                HashMap<Integer, String> chosenHeroInventoryMap = new HashMap<>();
                int index = 1;
                for (Item item : chosenHeroInventory) {
                    chosenHeroInventoryMap.put(index, item.toString());
                    index++;
                }
                int response = UserPrompt.getPrompt().promptMenuOptionsQuittable(new Menu("Please choose an item to sell", chosenHeroInventoryMap, null, null), false);
                if(response!=-1){
                    Item removed = chosenHeroInventory.remove(response-1);
                    Money itemSellPrice = removed.getPrice().halvedMoney();
                    hero.getWallet().add(itemSellPrice);
                    doSellItem(removed);
                }
            }
        }
    }

    // Actually do the action of selling an item
    private void doSellItem(Item removed){
        FileParser fp = FileParser.getFileParser();
        switch (removed.getClass().getName()) {
            case "Armor":
                sellSpecificTypeItem(removed,armors,armorsMap,fp.readMonstersAndHeroesHashNameToInfo("Armory"));
                break;
            case "Weapon":
                sellSpecificTypeItem(removed,weapons,weaponsMap,fp.readMonstersAndHeroesHashNameToInfo("Weaponry"));
                break;
            case "Potion":
                sellSpecificTypeItem(removed,potions,potionsMap,fp.readMonstersAndHeroesHashNameToInfo("Potions"));
                break;
            case "FireSpell":
                sellSpecificTypeItem(removed,fireSpells,fireSpellsMap, fp.readMonstersAndHeroesHashNameToInfo("FireSpells"));
                break;
            case "IceSpell":
                sellSpecificTypeItem(removed,iceSpells,iceSpellsMap, fp.readMonstersAndHeroesHashNameToInfo("IceSpells"));
                break;
            case "LightningSpell":
                sellSpecificTypeItem(removed,lightningSpells,lightningSpellsMap, fp.readMonstersAndHeroesHashNameToInfo("LightningSpells"));
                break;
        }
    }

    // Sell and prompt for a specific item type
    private void sellSpecificTypeItem(Item removed, Menu menu, HashMap<String,Item> itemMap, HashMap<String, String[]> hashMap){
        int itemNumberInShop = calculateItemNumber(menu);
        String itemName = removed.getName().getName();
        String[] removedItemInfo = hashMap.get(itemName);
        int padding = getItemPadding(itemMap,itemName);
        String itemToPutBack = paddedItem(itemName,removedItemInfo,padding);
        menu.getOptions().put(itemNumberInShop,itemToPutBack);
        itemMap.put(itemToPutBack, removed);
    }

    // Help pad the item based off longest name of the items
    private int getItemPadding(HashMap<String,Item> itemMap, String itemName){
        int padding = 0;
        for(Item items: itemMap.values()){
            if(items.getName().getName().length()>padding){
                padding = items.getName().getName().length();
            }
        }
        if(itemName.length() > padding){
            padding = itemName.length();
        }
        return padding;
    }

    // return a padded item for formatting
    private String paddedItem(String itemName, String[] removedItemInfo, int padding){
        String itemToPutBack = padRight(itemName,padding) + "\t|\t";

        for(String str: removedItemInfo){
            itemToPutBack +=padRight(str,padding) +"\t|\t";
        }
        return itemToPutBack;
    }

    // Calculate what the item number in the shop a new item would be (it will always be the lowest number not in the market)
    private int calculateItemNumber(Menu menu){
        int itemNumberInShop = 1;
        for(int value: menu.getOptions().keySet()){
            if(value == itemNumberInShop){
                itemNumberInShop++;
            }
            else{
                break;
            }
        }
        return itemNumberInShop;
    }

    // remove a certain item based on a certain item type
    private void removeCertainTypeItem(Character c, Menu menu, HashMap<String, Item> itemMap){
        int index = UserPrompt.getPrompt().promptMenuOptionsQuittable(menu,false);
        if(index!=-1) {
            if(c instanceof Hero) {
                Hero hero = ((Hero) c);
                Money heroMoney = hero.getWallet();
                Item toBuy = itemMap.get(menu.getOptions().get(index));
                Money itemMoney = toBuy.getPrice();
                if(hero.getLevel().compareLevel(toBuy.getLevel())){
                    System.out.println(Colors.getColors().coloredString("red", "Level too low to purchase! Transaction cancelled"));
                }
                else if (heroMoney.getValue() > itemMoney.getValue()) {
                    itemMap.remove(menu.getOptions().get(index));
                    menu.getOptions().remove(index);
                    hero.inv.addItem(toBuy);
                    heroMoney.subtract(itemMoney);
                }
                else{
                    System.out.println(Colors.getColors().coloredString("red", "Not enough money to purchase! Transaction cancelled."));
                }
            }
        }
    }

    // Actually remove item from market
    private void removeItem(String itemType, Character c){
        int index;
        switch (itemType){
            case "Armor":
                removeCertainTypeItem(c, armors, armorsMap);
                break;
            case "Weapon":
                removeCertainTypeItem(c, weapons, weaponsMap);
                break;
            case "Potion":
                removeCertainTypeItem(c, potions, potionsMap);
                break;
            case "IceSpell":
                removeCertainTypeItem(c, iceSpells, iceSpellsMap);
                break;
            case "FireSpell":
                removeCertainTypeItem(c, fireSpells, fireSpellsMap);
                break;
            case "LightningSpell":
                removeCertainTypeItem(c, lightningSpells, lightningSpellsMap);
                break;
        }

    }

    // prompt user to buy item
    public String promptBuy(){
        return UserPrompt.getPrompt().promptMenuOptionsValue(nonCharacterFactory.getMenu("ChooseMarketMenu", null),false);
    }

    // prompt main menu for market
    public int promptMarketMainMenu(){
        return UserPrompt.getPrompt().promptMenuOptions(nonCharacterFactory.getMenu("MarketMainMenu", null),false);
    }

    // Fill with armors
    private void initializeArmors(FileParser fp){
        HashMap<Integer, String[]> hashMap = fp.readMonstersAndHeroesHashArray("Armory");
        ItemFactory factory = new ItemFactory();
        ItemAbstractFactory itemFactory = factory.getItemAbstractFactory("ARMOR");
        hashMap.remove(0);

        for(Integer key: hashMap.keySet()){
            armorsMap.put(armors.get(key,"",0), itemFactory.getItem("ARMOR", hashMap.get(key)[0]));
        }
    }

    // Fill with weapons
    private void initializeWeapons(FileParser fp){
        HashMap<Integer, String[]> hashMap = fp.readMonstersAndHeroesHashArray("Weaponry");
        ItemFactory factory = new ItemFactory();
        ItemAbstractFactory itemFactory = factory.getItemAbstractFactory("WEAPON");
        hashMap.remove(0);

        for(Integer key: hashMap.keySet()){
            weaponsMap.put(weapons.get(key,"",0), itemFactory.getItem("WEAPON", hashMap.get(key)[0]));
        }

    }
    // Fill with potions
    private void initializePotions(FileParser fp){
        HashMap<Integer, String[]> hashMap = fp.readMonstersAndHeroesHashArray("Potions");
        ItemFactory factory = new ItemFactory();
        ItemAbstractFactory itemFactory = factory.getItemAbstractFactory("POTION");
        hashMap.remove(0);

        for(Integer key: hashMap.keySet()){
            potionsMap.put(potions.get(key,"",0), itemFactory.getItem("POTION", hashMap.get(key)[0]));
        }

    }
    // Fill with ice spells
    private void initializeIceSpells(FileParser fp){
        HashMap<Integer, String[]> hashMap = fp.readMonstersAndHeroesHashArray("IceSpells");
        ItemFactory factory = new ItemFactory();
        ItemAbstractFactory itemFactory = factory.getItemAbstractFactory("SPELL");
        hashMap.remove(0);

        for(Integer key: hashMap.keySet()){
            iceSpellsMap.put(iceSpells.get(key,"",0), itemFactory.getItem("ICE", hashMap.get(key)[0]));
        }

    }
    // Fill with fire spells
    private void initializeFireSpells(FileParser fp){
        HashMap<Integer, String[]> hashMap = fp.readMonstersAndHeroesHashArray("FireSpells");
        ItemFactory factory = new ItemFactory();
        ItemAbstractFactory itemFactory = factory.getItemAbstractFactory("SPELL");
        hashMap.remove(0);

        for(Integer key: hashMap.keySet()){
            fireSpellsMap.put(fireSpells.get(key,"",0), itemFactory.getItem("FIRE", hashMap.get(key)[0]));
        }

    }

    // Fill with spells
    private void initializeSpells(FileParser fp){
        initializeIceSpells(fp);
        initializeFireSpells(fp);
        initializeLightningSpells(fp);
    }

    // Fill with lightning spells
    private void initializeLightningSpells(FileParser fp){
        HashMap<Integer, String[]> hashMap = fp.readMonstersAndHeroesHashArray("LightningSpells");
        ItemFactory factory = new ItemFactory();
        ItemAbstractFactory itemFactory = factory.getItemAbstractFactory("SPELL");
        hashMap.remove(0);

        for(Integer key: hashMap.keySet()){
            lightningSpellsMap.put(lightningSpells.get(key,"",0), itemFactory.getItem("LIGHTNING", hashMap.get(key)[0]));
        }

    }

    public String toString(){
        return armors + "\n" + weapons + "\n" + potions +"\n" + fireSpells + "\n" + iceSpells + "\n" + lightningSpells;
    }


}
