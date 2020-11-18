public class CreatePaladinMenu extends Menu{
    // Menu to create a paladin

    public CreatePaladinMenu() {
        super("Available Paladins ", FileParser.getFileParser().readMonstersAndHeroesHash("Paladins"));
        setTitle(this.getTitle() + ": " + Colors.getColors().coloredString("yellow","\n\t"+ this.getOptions().get(0)));
        getOptions().remove(0);
    }
}
