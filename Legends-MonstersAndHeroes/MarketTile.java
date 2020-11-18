import java.util.ArrayList;
// Implementation of a tile, deals with the handling of market events
public class MarketTile extends Tile implements Enterable{
    // each market tile has a market
    private Market market;

    // Constructor
    public MarketTile(){
        super(true, false, new ArrayList<>());
        setMarket(new Market());
    }

    // Getter and setter for market
    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    // Perform market event
    @Override
    public void enter(Party p) {
        UserPrompt prompt = UserPrompt.getPrompt();
        this.getPartiesOnTile().add(p);
        boolean enterMarket = prompt.promptYN("You have reached a market! Would you like to enter the market?");
        if(enterMarket) {
            boolean quit = false;
            printMarketIntroduction();
            do{
                int response = this.market.promptMarketMainMenu();
                switch(response){
                    case 1:
                        showMarketBuyMenu();
                        break;
                    case 2:
                        showMarketSellMenu();
                        break;
                    case 3:
                        quit = true;
                }
            } while(!quit);
        }

    }

    // Show buy options
    private void showMarketBuyMenu(){
        String itemType = this.market.promptBuy();
        this.market.buyItem(itemType, getPartiesOnTile().get(0));
    }

    // Show sell options
    private void showMarketSellMenu(){
        this.market.sellItem(getPartiesOnTile().get(0));
    }

    // Print market's intro
    private void printMarketIntroduction(){
        System.out.println("                     ,---.           ,---.\r\n                    / /\"`.\\.--\"\"\"--./,'\"\\ \\\r\n                    \\ \\    _       _    / /\r\n                     `./  / __   __ \\  \\,'\r\n                      /    /_O)_(_O\\    \\\r\n                      |  .-'  ___  `-.  |\r\n                   .--|       \\_/       |--.\r\n                 ,'    \\   \\   |   /   /    `.\r\n                /       `.  `--^--'  ,'       \\\r\n             .-\"\"\"\"\"-.    `--.___.--'     .-\"\"\"\"\"-.\r\n.-----------/         \\------------------/         \\--------------.\r\n| .---------\\         /----------------- \\         /------------. |\r\n| |          `-`--`--'                    `--'--'-'             | |\r\n| |                                                             | |\r\n| |"+ Colors.getColors().coloredString("yellow","                     W E L C O M E                           ")+"| |\r\n| |                                                             | |\r\n| |"+Colors.getColors().coloredString("yellow","                          T O                                ")+"| |\r\n| |                                                             | |\r\n| |"+Colors.getColors().coloredString("yellow","                  T H E   M A R K E T                        ")+"| |\r\n| |                                                             | |\r\n| |_____________________________________________________________| |\r\n|_________________________________________________________________|\r\n                   )__________|__|__________(\r\n                  |            ||            |\r\n                  |____________||____________|\r\n                    ),-----.(      ),-----.(\r\n                  ,'   ==.   \\    /  .==    `.\r\n                 /            )  (            \\\r\n                 `==========='    `==========='  ");
    }

    @Override
    public String toString() {
        Colors color = Colors.getColors();
        StringBuilder marketTileString = new StringBuilder(super.toString());
        String colorToMake = "green";
        if(isOccupied()) {
            marketTileString.setCharAt(marketTileString.length()/2, 'X');
            colorToMake = "blue";
        }
        else{
            marketTileString.setCharAt(marketTileString.length()/2, 'M');
        }

        return color.coloredString(colorToMake,marketTileString.toString());
    }
}
