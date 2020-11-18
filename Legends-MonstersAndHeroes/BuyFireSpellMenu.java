public class BuyFireSpellMenu extends Menu{
    // Menu to display fire spells that can be bought

    public BuyFireSpellMenu() {
        super("Available Fire Spells ", FileParser.getFileParser().readMonstersAndHeroesHash("FireSpells"));
        setTitle(this.getTitle() + ": " + Colors.getColors().coloredString("yellow","\n\t"+ this.getOptions().get(0)));
        getOptions().remove(0);
    }
}
