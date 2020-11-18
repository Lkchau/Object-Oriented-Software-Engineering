public class BattleActionMenu extends Menu{
    // Menu to represent the possible battle actions
    public BattleActionMenu() {
            super("Available Battle Actions ", FileParser.getFileParser().readFileHash("","BattleActions"));
    }

}
