public class BuyIceSpellMenu extends Menu{
    // Menu to display Ice spells that can be bought

    public BuyIceSpellMenu() {
        super("Available Ice Spells ", FileParser.getFileParser().readMonstersAndHeroesHash("IceSpells"));
        setTitle(this.getTitle() + ": " + Colors.getColors().coloredString("yellow","\n\t"+ this.getOptions().get(0)));
        getOptions().remove(0);
    }
}
