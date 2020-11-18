public class CreateWarriorMenu extends Menu{
    // Menu to create a warrior

    public CreateWarriorMenu() {
        super("Available Warriors ", FileParser.getFileParser().readMonstersAndHeroesHash("Warriors"));
        setTitle(this.getTitle() + ": " + Colors.getColors().coloredString("yellow","\n\t"+ this.getOptions().get(0)));
        getOptions().remove(0);
    }
}
