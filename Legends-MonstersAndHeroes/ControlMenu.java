public class ControlMenu extends Menu{
    // Menu for the controls to move on the map
    public ControlMenu() {
        super("Time for the next move! Choose your next move: ", null, FileParser.getFileParser().readFileHashString("", "Controls"), null);
    }

    @Override
    public String toString() {
        StringBuilder menuString = new StringBuilder();
        Colors color = Colors.getColors();
        menuString.append(color.coloredString("black","cyan",getTitle())+"\n");
        if(getOptions()!= null){
            for(Integer opt: getOptions().keySet()){
                menuString.append(color.coloredString("purple","(" + opt + ") ") + getOptions().get(opt) +"\n" );
            }
        }
        if(getOptionStrings()!= null){
            for(String opt: getOptionStrings().keySet()){
                menuString.append(color.coloredString("purple","(" + opt + ") ") + getOptionStrings().get(opt) +"\n" );
            }
        }
        return menuString.toString();
    }
}
