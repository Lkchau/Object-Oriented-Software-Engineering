public class BuyWeaponMenu extends Menu{
    // Menu to display weapons that can be bought

    public BuyWeaponMenu() {
        super("Available Weapons ", FileParser.getFileParser().readMonstersAndHeroesHash("Weaponry"));
        setTitle(this.getTitle() + ": " + Colors.getColors().coloredString("yellow","\n\t"+ this.getOptions().get(0)));
        getOptions().remove(0);
    }
}
