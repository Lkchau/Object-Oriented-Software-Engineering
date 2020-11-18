public class CharacterMenuFactory extends AbstractMenuFactory{
    // factory for getting character in menu
    public Menu getMenu(String menuName, Party p){
        return new ChooseCharacterInPartyMenu(p);
    }
}
