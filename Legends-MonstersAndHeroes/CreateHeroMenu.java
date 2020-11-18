public class CreateHeroMenu extends Menu{
    // Menu to create a hero
    public CreateHeroMenu() {
        super("Choose your hero class ", FileParser.getFileParser().readFileHash("", "HeroClass"));
    }
}
