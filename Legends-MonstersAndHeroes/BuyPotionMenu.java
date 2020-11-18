public class BuyPotionMenu extends Menu {
    // Menu to display potions that can be bought

    public BuyPotionMenu() {
        super("Available Potions ", FileParser.getFileParser().readMonstersAndHeroesHash("Potions"));
        setTitle(this.getTitle() + ": " + Colors.getColors().coloredString("yellow","\n\t"+ this.getOptions().get(0)));
        getOptions().remove(0);
    }
}
