import java.util.HashMap;

// Menu factory
public class MenuFactory extends AbstractMenuFactory{
    HashMap<String,Menu> menus = new HashMap<>();
    public MenuFactory(){
        menus.put(Paladin.class.getName(),new CreatePaladinMenu());
        menus.put(Sorcerer.class.getName(),new CreateSorcererMenu());
        menus.put(Warrior.class.getName(),new CreateWarriorMenu());
    }
    @Override
    public Menu getMenu(String menuName, Party p) {
        if(menuName.equalsIgnoreCase(StartMenu.class.getCanonicalName())){
            return new StartMenu();
        }else if(menuName.equalsIgnoreCase(CreateHeroMenu.class.getName())){
            return new CreateHeroMenu();
        }
        else if(menuName.equalsIgnoreCase(Paladin.class.getName())){
            return menus.get(Paladin.class.getName());
        }
        else if(menuName.equalsIgnoreCase(Sorcerer.class.getName())){
            return menus.get(Sorcerer.class.getName());
        }
        else if(menuName.equalsIgnoreCase(Warrior.class.getName())){
            return menus.get(Warrior.class.getName());
        }
        else if(menuName.equalsIgnoreCase(ControlMenu.class.getName())){
            return new ControlMenu();
        }
        else if(menuName.equalsIgnoreCase(ChooseMarketMenu.class.getName())){
            return new ChooseMarketMenu();
        }
        else if(menuName.equalsIgnoreCase(MarketMainMenu.class.getName())){
            return new MarketMainMenu();
        }
        else if(menuName.equalsIgnoreCase(BuyArmorMenu.class.getName())){
            return new BuyArmorMenu();
        }
        else if(menuName.equalsIgnoreCase(BuyWeaponMenu.class.getName())){
            return new BuyWeaponMenu();
        }
        else if(menuName.equalsIgnoreCase(BuyPotionMenu.class.getName())){
            return new BuyPotionMenu();
        }
        else if(menuName.equalsIgnoreCase(BuyLightningSpellMenu.class.getName())){
            return new BuyLightningSpellMenu();
        }
        else if(menuName.equalsIgnoreCase(BuyIceSpellMenu.class.getName())){
            return new BuyIceSpellMenu();
        }
        else if(menuName.equalsIgnoreCase(BuyFireSpellMenu.class.getName())){
            return new BuyFireSpellMenu();
        }
        else if(menuName.equalsIgnoreCase(BattleActionMenu.class.getName())){
            return new BattleActionMenu();
        }


        return null;
    }

}
