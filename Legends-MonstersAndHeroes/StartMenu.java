public class StartMenu extends Menu{
    // Representation for a start menu
    public StartMenu() {
        super("Start Menu ", FileParser.getFileParser().readFileHash("", "StartMenu"));
    }

}
