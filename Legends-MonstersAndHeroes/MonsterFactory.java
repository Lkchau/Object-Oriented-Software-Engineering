import java.util.HashMap;

// MonsterFactory to help produce monsters
public class MonsterFactory extends CharacterAbstractFactory{

    public Character getCharacter(String monsterType, String monsterName) {

    if(monsterType == null){
        return null;
    }
        HashMap<String, String[]> characterInfo = FileParser.getFileParser().readMonstersAndHeroesHashNameToInfo(monsterType + "s");
        String[] currMonsterInfo = characterInfo.get(monsterName.replaceAll(" ",""));
        assert currMonsterInfo.length == 4;
        int level = Integer.parseInt(currMonsterInfo[0]);
        int damage = Integer.parseInt(currMonsterInfo[1]);
        int defense = Integer.parseInt(currMonsterInfo[2]);
        int dodgeChance = Integer.parseInt(currMonsterInfo[3]);
        Name charName = new Name(monsterName);
        Damage currDamage = new Damage(damage);
        Damage currDef = new Damage(defense);
        Level lvl = new Level(level);

        if (monsterType == null) {
            return null;
        }
        else{
            return new Monster(charName,lvl,currDamage,currDef,dodgeChance);
        }

    }
}
