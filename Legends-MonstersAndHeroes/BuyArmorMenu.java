public class BuyArmorMenu extends Menu{
    // Menu to display armor that can be bought
    public BuyArmorMenu() {
        super("Available Armor ", FileParser.getFileParser().readMonstersAndHeroesHash("Armory"));
        setTitle(this.getTitle() + ": " + Colors.getColors().coloredString("yellow","\n\t"+ this.getOptions().get(0)));
        getOptions().remove(0);
    }
}
