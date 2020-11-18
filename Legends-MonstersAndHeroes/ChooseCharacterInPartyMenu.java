import java.util.HashMap;
// menu to represent choosing a member of the party
public class ChooseCharacterInPartyMenu extends Menu{

    public ChooseCharacterInPartyMenu(Party p){
        super("Choose a character from your party",null,null,null);
        HashMap<Integer, Character> characters = new HashMap<>();
        int index = 1;
        for(Character c: p.getParty()){
            characters.put(index, c);
            index++;
        }
        setCharacterOption(characters);
    }
}
