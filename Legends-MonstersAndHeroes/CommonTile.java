import java.util.ArrayList;
import java.util.Random;
// Concrete implementation of a tile to represent a common tile which is enterable and can spawn monsters
public class CommonTile extends Tile implements Enterable{

    private final double MONSTERCHANCE = 0.5;


    public CommonTile(){
        super(true, false, new ArrayList<>());
    }

    // Enter event for common tile
    @Override
    public void enter(Party p) {
        boolean encounteredMonster = encounterMonster();
        if(encounteredMonster){
            printBattleEncounterScreen();
            Battle battle= new Battle(p);
            battle.initiateBattleSequence();
        }
        else{
            printSafeScreen();
        }
    }

    // Check if monster spawns
    public boolean encounterMonster(){
        Random r = new Random();
        double chance = r.nextDouble();
        return chance < MONSTERCHANCE;
    }

    // Battle screen
    public void printBattleEncounterScreen(){
        System.out.println("   .\r\n  / \\\r\n  | |\r\n  | |\r\n  |.|                                         "+ Colors.getColors().coloredString("red","Y O U   H A V E   E N C O U N T E R E D   A N   E N E M Y! ") +"\r\n  |.|\r\n  |:|\r\n  |:|\r\n`--8--'\r\n   8\r\n   O");
    }

    // Safe screen
    public void printSafeScreen(){
        System.out.println("           (                 ,&&&.\r\n            )                .,.&&\r\n           (  (              \\=__/\r\n               )             ,'-'.\r\n         (    (  ,,      _.__|/ /|                                       "+Colors.getColors().coloredString("green","Phew, no monsters spawned! Take a break!\r\n")+"          ) /\\ -((------((_|___/ |\r\n        (  // | (`'      ((  `'--|\r\n      _ -.;_/ \\\\--._      \\\\ \\-._/.\r\n     (_;-// | \\ \\-'.\\    <_,\\_\\`--'|\r\n     ( `.__ _  ___,')      <_,-'__,'\r\n      `'(_ )_)(_)_)'");
    }

    @Override
    public String toString() {
        String ret = super.toString();
        if(this.isOccupied()) {
            Colors color = Colors.getColors();
            StringBuilder commonTileString = new StringBuilder(super.toString());
            commonTileString.setCharAt(commonTileString.length() / 2, 'X');
            ret = color.coloredString("blue", commonTileString.toString());
        }
        return ret;
    }
}
