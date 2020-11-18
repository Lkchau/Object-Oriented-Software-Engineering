import java.util.HashMap;
import java.util.List;

public class Colors {
    // NOTE: Windows users will most likely need to run the following:
    // reg add HKEY_CURRENT_USER\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000001 /f
    // To be able to get the color to show up in the terminal.
    // Reset the color back to normal, need to do at the end otherwise the color persists between print statements and it also colors the terminal
    //create an object of SingleObject
    // Class to wrap strings with ANSI esc keys
    private static Colors colors;
    private static HashMap<String, String> colorFile = readColorFile();

    private Colors() {}

    //Get the only object available
    public static Colors getColors(){
        if (colors == null)
            colors = new Colors();
        return colors;
    }


    private static String lookUpColorCode(String color){
        String colorWanted = color.toUpperCase();
        if(!colorFile.keySet().contains(colorWanted)) return colorFile.get("WHITE");
        return colorFile.get(colorWanted);
    }

    private static HashMap<String, String> readColorFile(){
        FileParser fp = FileParser.getFileParser();
        HashMap<String, String> colors = new HashMap<>();
        List<String> colorsFromList = fp.readFile("", "ColorUnicodeList");
        for(String color: colorsFromList){
            String[] colorParts = color.split(": ");
            String currKey = colorParts[0];
            String currValue = colorParts[1];
            colors.put(currKey, currValue);
        }

        return colors;
    }

    public void print(String color, String background, String toPrint){
        System.out.println(coloredString(color, background, toPrint));
    }
    public void print(String color, String toPrint){
        print(color, "", toPrint);
    }
    public void printWithColorBackground(String background, String toPrint){
        System.out.println(coloredBackground(background, toPrint));
    }
    public String coloredString(String color, String backgroundColor, String toPrint){
        boolean wantToColor = !color.equals("");
        boolean wantBackground = !backgroundColor.equals("");
        if(!wantToColor && !wantBackground) return toPrint;
        StringBuilder colored = new StringBuilder();
        colored.append("\033[");
        if(wantToColor){
            colored.append(lookUpColorCode(color));
        }
        if(wantBackground) {
            if(wantToColor) colored.append(";");
            colored.append(lookUpColorCode(backgroundColor + "_BG"));
        }
        colored.append("m" + toPrint + "\033[" + lookUpColorCode("normal"));
        return colored.toString();
    }
    public String coloredString(String color, String toPrint){
        return coloredString(color, "", toPrint);
    }
    public String coloredBackground(String color, String toPrint){
        return coloredString("", color, toPrint);
    }
    public String coloredString(String toPrint){
        return toPrint;
    }





}
