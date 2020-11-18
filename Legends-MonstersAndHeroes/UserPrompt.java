import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Singleton class to help with user input
public class UserPrompt {

    private static UserPrompt prompt;

    private UserPrompt(){}

    //Get the only object available
    public static UserPrompt getPrompt(){
        if (prompt == null)
            prompt = new UserPrompt();
        return prompt;
    }
    // Prompt to decide what game to choose
    private int promptForIntWithPrompt(String prom, String options, boolean repeat) throws NoSuchElementException{
        Scanner prompt = new Scanner(System.in);

        int response = 0;
        try {
            Colors colors = Colors.getColors();
            if (repeat) System.out.println(colors.coloredString("red","Input was an error! Please try again!"));
            if (!prom.equals("")) System.out.println(prom);
            if (!options.equals("")) System.out.println(options);
            response = prompt.nextInt();
        } catch (InputMismatchException err){
            response = promptForIntWithPrompt(prom, options, true);
        } catch (NoSuchElementException err){
            response = promptForIntWithPrompt(prom, options, true);
        }
        return response;
    }

    // Prompt to decide what game to choose
    public int promptForIntWithPrompt(String prom, boolean repeat) {
        return promptForIntWithPrompt(prom, "",repeat);
    }

    public int promptForIntWithPrompt(String prom, int min, int max, boolean repeat) {
        int response = 0;
        do {
            response = promptForIntWithPrompt(prom + "Minimum: " + min + ", Max: " + max, "", repeat);
            if (response < min) {
                System.out.println(Colors.getColors().coloredString("red", "Value is too small!"));
            } else if (response > max) {
                System.out.println(Colors.getColors().coloredString("red", "Value is too big!"));
            }
        }
        while(response < min || response > max);
        return response;
    }

    public int decideGame(String options, boolean repeat){
        return promptForIntWithPrompt("Please decide what game you would like to play by typing in the corresponding game number! Your options are:\n", options, repeat);
    }

    public int promptMenuOptions(Menu menu, boolean repeat){
        int response = 0;
        do{
            response = promptForIntWithPrompt("",menu.toString(), repeat);
        }
        while(!menu.getOptions().containsKey(response));
        return response;
    }

    public int promptMenuOptionsQuittable(Menu menu, boolean repeat){
        System.out.println(menu.toString());
        String response = promptString("Enter a corresponding number or anything else to quit menu!", false);
        try{
            int value = Integer.parseInt(response);
            if(menu.getOptions().containsKey(value)){
                return value;
            }
            else{
                return -1;
            }
        } catch (NumberFormatException err){
            return -1;
        }

    }

    public String promptMenuOptionsValue(Menu menu, boolean repeat){
        int response = 0;
        String responseString = "";
        int respondCharacter = 0;
        do{
            if(menu.getOptions() != null) response = promptForIntWithPrompt("",menu.toString(), repeat);
            if(menu.getOptionStrings()!= null) responseString = promptString(menu.toString(),menu.getOptionStrings().keySet().toArray(new String[0]), false);
            if(menu.getCharacterOption()!= null) respondCharacter = promptForIntWithPrompt("Please choose a character",menu.toString(), repeat);
        }
        while(!menu.hasResponse(response, responseString, respondCharacter));
        return menu.get(response, responseString, respondCharacter);
    }

    public Character promptMenuOptionsValueChar(Menu menu, boolean repeat){
        int respondCharacter = 0;
        do{
            if(menu.getCharacterOption()!= null) respondCharacter = promptForIntWithPrompt("Please choose a character",menu.toString(), repeat);
        }
        while(!menu.hasResponse(0,"",respondCharacter));
        return menu.get(respondCharacter);
    }

    public String promptMenuOptionsKey(Menu menu, boolean repeat){
        int response = 0;
        String responseString = "";
        int respondCharacter = 0;
        do{
            if(menu.getOptions() != null) response = promptForIntWithPrompt("",menu.toString(), repeat);
            if(menu.getOptionStrings()!= null) responseString = promptString(menu.toString(),menu.getOptionStrings().keySet().toArray(new String[0]), false);
            if(menu.getCharacterOption()!= null) respondCharacter = promptForIntWithPrompt("Please choose a character",menu.toString(), repeat);

        }
        while(!menu.hasResponse(response, responseString, respondCharacter));
        return responseString;
    }

    public String promptString(String prompt, String[] expectedString, boolean repeat){
        Colors c = Colors.getColors();
        if(repeat) System.out.println(c.coloredString("red","Invalid!"));
        Scanner prompter = new Scanner(System.in);
        System.out.println(prompt);
        if(expectedString!= null){
            System.out.println("Please enter one of the following: " + Arrays.toString(expectedString));
        }
        String response = prompter.nextLine();
        if(expectedString != null){
            while(!Arrays.asList(expectedString).contains(response)){
                response = promptString(prompt, expectedString, true);
            }
        }

        return response;
    }



    public String promptString(String prompt, boolean repeat){
        return promptString(prompt, null, repeat);
    }

    public boolean promptYN(String prompt){
        String[] expected = {"Y","y", "N", "n"};
        String response = promptString(prompt, expected, false);
        return response.equalsIgnoreCase("Y");
    }
}
