import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

// Battle class to represent a battle in Legends Monsters and Heroes
public class Battle {
    // Every battle has a party and an enemy party, FUTURE extension: battles with multiple parties
    private Party party;
    private Party enemyParty;
    private final double proportionToRecoverAfterTurn = 0.05;

    // Constructors for battles
    public Battle(Party party){
        setParty(party);
        setEnemyParty(new Party());
        generateEnemies();
    }

    public Battle(){
        this(new Party());
    }

    // Setters and getters for attributes
    public void setParty(Party party) {
        this.party = party;
    }

    public Party getParty() {
        return party;
    }

    public Party getEnemyParty() {
        return enemyParty;
    }

    public void setEnemyParty(Party enemyParty) {
        this.enemyParty = enemyParty;
    }

    // Main functionality to generate the opposing (enemy) party
    private void generateEnemies(){
        Level highestMonsterLevel = party.getHighestLevel();
        int numberToGenerate = party.getPartySize();
        while(enemyParty.getPartySize()<numberToGenerate){
            HashMap<Integer, Character> enemiesToChooseFrom = generatePossibleEnemies(FileParser.getFileParser(), highestMonsterLevel);
            List<Integer> keys = new ArrayList<>(enemiesToChooseFrom.keySet());
            Random r = new Random();
            enemyParty.addCharacter(enemiesToChooseFrom.get(keys.get(r.nextInt(keys.size()))));
        }
        enemyParty.setPartyType("Monster");
    }

    // Utilized to restrict the level of enemies (you can have several of the same enemy)
    private HashMap<Integer, Character> generatePossibleEnemies(FileParser fp, Level highestMonsterLevel){
        CharacterFactory factory = new CharacterFactory();
        CharacterAbstractFactory abstractFactory = factory.getCharacterAbstractFactory("monster");
        HashMap<String, String[]> exoskeletons = fp.readMonstersAndHeroesHashNameToInfo( "Exoskeletons");
        exoskeletons.remove("Name");
        exoskeletons.remove("");
        HashMap<String, String[]> dragons = fp.readMonstersAndHeroesHashNameToInfo( "Dragons");
        dragons.remove("Name");
        dragons.remove("");
        HashMap<String, String[]> spirits = fp.readMonstersAndHeroesHashNameToInfo( "Spirits");
        spirits.remove("Name");
        spirits.remove("");

        int index = 1;
        HashMap<Integer, Character> possibleEnemies = new HashMap<>();
        for(String key: exoskeletons.keySet()){
            Character monsterGenerated = abstractFactory.getCharacter("Exoskeleton", key);
            if(monsterGenerated.getLevel().getLevel() <= highestMonsterLevel.getLevel()) {
                possibleEnemies.put(index, monsterGenerated);
                index++;
            }
        }
        for(String key: dragons.keySet()){
            Character monsterGenerated = abstractFactory.getCharacter("Dragon", key);
            if(monsterGenerated.getLevel().getLevel() <= highestMonsterLevel.getLevel()) {
                possibleEnemies.put(index, monsterGenerated);
                index++;

            }
        }
        for(String key: spirits.keySet()){
            Character monsterGenerated = abstractFactory.getCharacter("Spirit", key);
            if(monsterGenerated.getLevel().getLevel() <= highestMonsterLevel.getLevel())
            {
                possibleEnemies.put(index, monsterGenerated);
                index++;
            }
        }
        return possibleEnemies;
    }

    // Starts the battle and runs it
    public void initiateBattleSequence(){
        int currentRound = 1;
        while(!enemyParty.allFainted() && !party.allFainted()) {
            System.out.println(this.enemyParty);
            System.out.println("\nBe ready to battle!\n");
            System.out.println(this.party);
            playOneRoundOfBattle(currentRound++);
        }
        if(enemyParty.allFainted()){
            printVictoryScreen();
        }
        if(party.allFainted()){
            printLosingScreen();
        }
        updateHeroes();
    }

    // runs one round of a battle
    private void playOneRoundOfBattle(int roundNum){
        UserPrompt prompter = UserPrompt.getPrompt();
        System.out.println(Colors.getColors().coloredString("yellow", "\t\t\t\t\t\t\t\t\t G E T   R E A D Y   F O R   R O U N D   " + roundNum));
        ConcreteMenuFactory factory = new ConcreteMenuFactory();
        AbstractMenuFactory menuFactory = factory.getMenu("MENU");
        playHeroTurn(prompter,menuFactory);
        playEnemyTurn();
        recoverHeroAfterARound();
    }

    // Allows the user to take their turn
    private void playHeroTurn(UserPrompt prompter, AbstractMenuFactory menuFactory){
        for(int partyIndex = 0; partyIndex< party.getParty().size();partyIndex++){
            if(!enemyParty.allFainted()){
                if(!party.getParty().get(partyIndex).isFainted()){
                    System.out.println("Turn for hero " + party.getParty().get(partyIndex).getName());
                    if( party.getParty().get(partyIndex) instanceof  Hero){
                        Character monsterToTarget = enemyParty.getParty().get(partyIndex);
                        if(monsterToTarget.isFainted()){
                            monsterToTarget = enemyParty.getFirstAliveCharacter();
                        }
                        System.out.println("Currently targeting "+ Colors.getColors().coloredString("cyan",monsterToTarget.name.toString()));
                        String option = prompter.promptMenuOptionsValue(menuFactory.getMenu("BattleActionMenu", null), false);
                        boolean performedAction = doAction(party.getParty().get(partyIndex), monsterToTarget,option);
                        while(!performedAction){
                            System.out.println("Turn was not taken!");
                            option = prompter.promptMenuOptionsValue(menuFactory.getMenu("BattleActionMenu", null), false);
                            performedAction = doAction(party.getParty().get(partyIndex), monsterToTarget,option);
                        }
                    }
                }
            }
        }
    }

    // Automatically does the enemy turn (they can only attack!)
    private void playEnemyTurn() {
        for(int partyIndex = 0; partyIndex< enemyParty.getParty().size();partyIndex++){
            if(!party.allFainted()){
                if(!enemyParty.getParty().get(partyIndex).isFainted()){
                    Character heroToAttack = party.getParty().get(partyIndex);
                    if(heroToAttack.isFainted()){
                        heroToAttack = party.getFirstAliveCharacter();
                    }
                    if(heroToAttack!= null) attackHero(enemyParty.getParty().get(partyIndex),heroToAttack);
                }
            }
        }

    }

    // perform an action based on what the user chooses
    private boolean doAction(Character c, Character m,String option){
        switch(option){
            case "Attack":
                attackEnemy(c,m);
                break;
            case "Equip":
                if(c instanceof Hero){
                    Hero currHero = ((Hero) c);
                    if(currHero.getInv().getEquips().size() <= 0){
                        System.out.println(Colors.getColors().coloredString("red","You have no Equips to equip!"));
                        return false;
                    }
                    else{
                        return promptEquipItem(currHero);
                    }
                }
                else{
                    return false;
                }
            case "Cast Spell":
                if(c instanceof Hero){
                    Hero currHero = ((Hero) c);
                    if(currHero.getInv().getSpells().size() <= 0){
                        System.out.println(Colors.getColors().coloredString("red","You have no spells to cast!"));
                        return false;
                    }
                    else{
                        if(m instanceof Debuffable){
                            Debuffable monster = (Debuffable) m;
                            if(!promptSpellCast(currHero, monster)){
                                System.out.println(Colors.getColors().coloredString("red","Not enough mana to cast! or not an option"));
                                return false;
                            }
                        }

                    }
                }
                else{
                    return false;
                }

                break;
            case "Use potion":
                if(c instanceof Hero) {
                    Hero currHero = ((Hero) c);
                    if(currHero.getInv().getPotions().size() <= 0){
                        System.out.println(Colors.getColors().coloredString("red","You have no potions to drink!"));
                        return false;
                    }
                    else{
                        return promptPotionDrink(currHero);
                    }
                }
                else{
                    return false;
                }

        }
        return true;
    }

    // Prints out a victory screen
    private void printVictoryScreen(){
        System.out.println("         .* *.               `o`o`\r\n         *. .*              o`o`o`o      ^,^,^\r\n           * \\               `o`o`     ^,^,^,^,^\r\n              \\     ***        |       ^,^,^,^,^\r\n               \\   *****       |        /^,^,^\r\n                \\   ***        |       /\r\n    ~@~*~@~      \\   \\         |      /\r\n  ~*~@~*~@~*~     \\   \\        |     /\r\n  ~*~@smd@~*~      \\   \\       |    /     #$#$#        .`'.;.\r\n  ~*~@~*~@~*~       \\   \\      |   /     #$#$#$#   00  .`,.',\r\n    ~@~*~@~ \\        \\   \\     |  /      /#$#$#   /|||  `.,'\r\n_____________\\________\\___\\____|_/______/_________|\\/\\___||______\r\n                       "+Colors.getColors().coloredString("blue","V I C T O R Y !"));
        UserPrompt up = UserPrompt.getPrompt();
        up.promptString("Please type in any key to continue!",false);
    }

    // Prints out a losing screen
    private void printLosingScreen(){
        System.out.println(Colors.getColors().coloredString("red","                                                                          \r\n@@@@@@@      @@@@@@@@     @@@@@@@@     @@@@@@@@      @@@@@@      @@@@@@@  \r\n@@@@@@@@     @@@@@@@@     @@@@@@@@     @@@@@@@@     @@@@@@@@     @@@@@@@  \r\n@@!  @@@     @@!          @@!          @@!          @@!  @@@       @@!    \r\n!@!  @!@     !@!          !@!          !@!          !@!  @!@       !@!    \r\n@!@  !@!     @!!!:!       @!!!:!       @!!!:!       @!@!@!@!       @!!    \r\n!@!  !!!     !!!!!:       !!!!!:       !!!!!:       !!!@!!!!       !!!    \r\n!!:  !!!     !!:          !!:          !!:          !!:  !!!       !!:    \r\n:!:  !:!     :!:          :!:          :!:          :!:  !:!       :!:    \r\n :::: ::      :: ::::      ::           :: ::::     ::   :::        ::    \r\n:: :  :      : :: ::       :           : :: ::       :   : :        :     \r\n                                                                          "));
        UserPrompt up = UserPrompt.getPrompt();
        up.promptString("Please type in any key to continue!",false);
    }


    //Specification: You can assume that after every successful fight each hero who did not faint gains 100*monsters_level coins and 2 exp for their troubles.
    // Update hero based of specification and revives any fainted heroes
    private void updateHeroes(){
        for(Character c: party.getParty()){
            if(c instanceof Hero){
                Hero currHeroToUpdate = (Hero) c;
                if(currHeroToUpdate.isFainted()){
                    currHeroToUpdate.revive();
                }
                else{
                    int amountMoneyGained = 100*enemyParty.getHighestLevel().getLevel();
                    Money moneyGained = new Money(amountMoneyGained);
                    currHeroToUpdate.getWallet().add(moneyGained);
                    currHeroToUpdate.getExp().add(2);
                    currHeroToUpdate.levelUp();
                }
            }
        }
    }

    //Specification: You can assume that during every round of a fight the heroes regain 10% of their hp and 10% of their mana.
    // Value changed to 5%
    // Heal the health and mana of heroes between rounds in a battle
    private void recoverHeroAfterARound(){
        for(Character c: party.getParty()){
            if(c instanceof Hero){
                Hero currHero = (Hero) c;
                Mana toRecover = new Mana((int) Math.round(currHero.getBaseMana().getMana()*proportionToRecoverAfterTurn));
                currHero.getMana().addMana(toRecover);
                Health hpToRecover = new Health((int) Math.round(currHero.getBaseHP().getHealth()*proportionToRecoverAfterTurn));
                currHero.getHp().addHealth(hpToRecover);
            }
        }
    }

    // Allow the hero to attack an enemy
    private void attackEnemy(Character hero, Character enemy){
        if(hero instanceof Hero){
            Hero currHero = (Hero) hero;
            currHero.attack(enemy);
        }
    }

    // Allow the monster to attack the hero
    private void attackHero(Character monster, Character hero){
        if(monster instanceof Monster){
            Monster currMonster = (Monster) monster;
            currMonster.attack(hero);
        }
    }

    // Prompt for an equip to put on the character and equip it if possible, Would have made sense to use the equip's equip function here but forgot about it in the moment
    private boolean promptEquipItem(Hero c){
        HashMap<Integer, String> inventoryMap = new HashMap<>();
        int index = 1;
        for (Item item : c.getInv().getInventory()) {
            if(item instanceof Equippable) inventoryMap.put(index, item.toString());
            index++;
        }
        int response = UserPrompt.getPrompt().promptMenuOptionsQuittable(new Menu("Please choose an Equip to equip", inventoryMap, null, null), false);
        if(response != -1){
            Item equip = c.getInv().getByItemName(inventoryMap.get(response));
            if(equip instanceof Equippable){
                if(equip instanceof Weapon){

                    c.getInv().setEquippedWeapon((Weapon) equip);
                }
                else if(equip instanceof Armor){

                    c.getInv().setEquippedArmor((Armor) equip);
                }
            }
            return true;
        }
        else{
            return false;
        }


    }

    // Prompt for an spell to cast and cast it if possible
    private boolean promptSpellCast(Hero c, Debuffable enemy){
        HashMap<Integer, String> inventoryMap = new HashMap<>();
        int index = 1;
        for (Item item : c.getInv().getInventory()) {
            if(item instanceof Spell) inventoryMap.put(index, item.toString());
            index++;
        }
        int response = UserPrompt.getPrompt().promptMenuOptionsQuittable(new Menu("Please choose a spell to cast", inventoryMap, null, null), false);
        if(response == -1) return  false;
        Item spell = c.getInv().getByItemName(inventoryMap.get(response).split(" ")[0]);
        if(spell instanceof Spell){
            if(((Spell) spell).getMana().getMana() > c.getMana().getMana()){
                return false;
            }
            else{
                enemy.debuff((Spell) spell);
                c.setMana(new Mana(c.getMana().getMana()-((Spell) spell).getMana().getMana()));
            }
        }
        return true;
    }

    // Prompt for a potion to consume and consume it if possible
    private boolean promptPotionDrink(Hero c){
        HashMap<Integer, String> inventoryMap = new HashMap<>();
        int index = 1;
        for (Item item : c.getInv().getInventory()) {
            if(item instanceof Potion) inventoryMap.put(index, item.toString());
            index++;
        }
        int response = UserPrompt.getPrompt().promptMenuOptionsQuittable(new Menu("Please choose a potion to drink", inventoryMap, null, null), false);
        if(response!= -1){
            Item potion = c.getInv().getByItemName(inventoryMap.get(response).split(":")[0]);
            if(potion instanceof Potion){
                ((Potion) potion).consume(c);
                c.getInv().getInventory().remove(potion);
            }
            return true;
        }
        else{
            return false;
        }
    }
}
