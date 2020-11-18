public class CreateSorcererMenu extends Menu{
    // Menu to create a sorcerer

    public CreateSorcererMenu() {
        super("Available Sorcerers ", FileParser.getFileParser().readMonstersAndHeroesHash("Sorcerers"));
        setTitle(this.getTitle() + ": " + Colors.getColors().coloredString("yellow","\n\t"+ this.getOptions().get(0)));
        getOptions().remove(0);
    }

}
