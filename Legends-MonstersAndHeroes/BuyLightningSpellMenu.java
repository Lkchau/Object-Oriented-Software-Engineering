public class BuyLightningSpellMenu extends Menu{
    // Menu to display Lightning spells that can be bought

    public BuyLightningSpellMenu() {
        super("Available Lightning ", FileParser.getFileParser().readMonstersAndHeroesHash("LightningSpells"));
        setTitle(this.getTitle() + ": " + Colors.getColors().coloredString("yellow","\n\t"+ this.getOptions().get(0)));
        getOptions().remove(0);
    }
}
