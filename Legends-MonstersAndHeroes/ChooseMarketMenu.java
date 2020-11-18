public class ChooseMarketMenu extends Menu{
    // menu to represent choosing an item from the market

    public ChooseMarketMenu() {
            super("Choose an item to buy ", FileParser.getFileParser().readFileHash("", "MarketMenu"));
        }
}
