public class MarketMainMenu extends Menu{
    // representation of the main menu for the market
    public MarketMainMenu() {
        super("What would you like to do? ", FileParser.getFileParser().readFileHash("", "MarketMainMenu"));
    }
}
