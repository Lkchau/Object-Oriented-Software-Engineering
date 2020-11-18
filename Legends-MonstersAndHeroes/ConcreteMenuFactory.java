import java.util.HashMap;

// Concrete Menu Factory, returns a character (choosing characters) or just a regular menu
public class ConcreteMenuFactory implements MenuFactoryInterface{

    HashMap<String, AbstractMenuFactory> menus = new HashMap<>();
    public ConcreteMenuFactory(){
        menus.put("CHARACTER", new CharacterMenuFactory());
        menus.put("MENU", new MenuFactory());

    }
        @Override
        public AbstractMenuFactory getMenu(String itemType) {
            return menus.get(itemType);
        }
}
