import java.util.HashMap;
// Representation of a menu with different options
public class Menu {
    private String title;

    // different kinds of menus
    private HashMap<Integer, String> options;
    private HashMap<String, String> optionStrings;
    private HashMap<Integer, Character> characterOption;


    // Constructors
    public Menu(String title, HashMap<Integer, String> options, HashMap<String, String> options2, HashMap options3){
        setTitle(title);
        if(options!= null) setOptions(options);
        if(options2 != null) setOptionStrings(options2);
        if(options3 != null) setCharacterOption(options3);
    }

    public Menu(String title, HashMap<Integer, String> options){
        this(title, options,null, null);
    }

    public Menu(){
        this("", new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public void setCharacterOption(HashMap<Integer, Character> characterOption) {
        this.characterOption = characterOption;
    }

    // getter and setters
    public HashMap<Integer, Character> getCharacterOption() {
        return characterOption;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashMap<Integer, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<Integer, String> options) {
        this.options = options;
    }

    public HashMap<String, String> getOptionStrings() {
        return optionStrings;
    }

    public void setOptionStrings(HashMap<String, String> optionStrings) {
        this.optionStrings = optionStrings;
    }

    // Check if the option is in the menu or not
    public boolean hasResponse(int response, String responseString, int c){
        if(options!= null){
            return options.containsKey(response);
        }
        if(optionStrings!= null){
            return optionStrings.containsKey(responseString);
        }
        if(characterOption!= null){
            return characterOption.containsKey(c);
        }
        return false;
    }


    // get item from menu
    public String get(int response, String responseString, int charIndex){
        String ret = "";
        if(options!= null){
            ret = options.get(response);
        }
        if(optionStrings!= null){
            ret =  optionStrings.get(responseString);
        }
        if(characterOption!= null){
            ret = characterOption.get(charIndex).toString();
        }
        return ret;
    }

    public Character get(int charIndex){
        if(characterOption!= null){
            return characterOption.get(charIndex);
        }
        return null;
    }

    // Help pad and format strings
    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    @Override
    public String toString() {
        StringBuilder menuString = new StringBuilder();
        Colors color = Colors.getColors();
        menuString.append(color.coloredString("black","green",title)+"\n");
        if(options!= null){
            for(Integer opt: options.keySet()){
                menuString.append(color.coloredString("cyan","(" + opt + ") ") + options.get(opt) +"\n" );
            }
        }
        if(optionStrings!= null){
            for(String opt: optionStrings.keySet()){
                menuString.append(color.coloredString("cyan","(" + opt + ") ") + optionStrings.get(opt) +"\n" );
            }
        }
        if(characterOption!= null){
            menuString.append(padRight("\tName",20));
            menuString.append(color.coloredString("blue", padRight("Mana",15)));
            menuString.append(color.coloredString("green", padRight("Health",15)));
            menuString.append(padRight("Strength",15));
            menuString.append(padRight("Agility",15));
            menuString.append(padRight("Dexterity",15));
            menuString.append(padRight("Experience",15));
            menuString.append(padRight("Level",15));
            menuString.append(padRight("Money",15));
            menuString.append(padRight("Inventory",15));
            for(Integer opt: characterOption.keySet()){
                Character c = characterOption.get(opt);
                if(c instanceof Hero) menuString.append("\n"+color.coloredString("cyan","(" + opt + ") ") + c.toString() );
            }
        }
        return menuString.toString();
    }
}
