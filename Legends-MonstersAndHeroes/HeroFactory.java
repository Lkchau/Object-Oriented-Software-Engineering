import java.util.HashMap;

// Hero Factory to return heroes
public class HeroFactory extends  CharacterAbstractFactory{

    public Character getCharacter(String heroType, String heroName){
        if(heroType == null){
            return null;
        }
        HashMap<String, String[]> characterInfo = FileParser.getFileParser().readMonstersAndHeroesHashNameToInfo(heroType + "s");
        String[] currHeroInfo = characterInfo.get(heroName.replaceAll(" ",""));
        assert currHeroInfo.length == 6;
        int mana = Integer.parseInt(currHeroInfo[0]);
        int str = Integer.parseInt(currHeroInfo[1]);
        int agl = Integer.parseInt(currHeroInfo[2]);
        int dex = Integer.parseInt(currHeroInfo[3]);
        int money = Integer.parseInt(currHeroInfo[4]);
        int experience = Integer.parseInt(currHeroInfo[5]);
        Name charName = new Name(heroName);
        Mana currMana = new Mana(mana);
        HeroStats stats = new HeroStats(new Strength(str), new Agility(agl), new Dexterity(dex));
        Money wallet = new Money(money);
        Experience exp = new Experience(experience);
        Level level = new Level(1);

        if(heroType.equalsIgnoreCase("WARRIOR")){
            return new Warrior(charName,level,false,wallet,stats,currMana,exp,new Inventory());

        } else if(heroType.equalsIgnoreCase("SORCERER")){
            return new Sorcerer(charName,level,false,wallet,stats,currMana,exp,new Inventory());

        } else if(heroType.equalsIgnoreCase("PALADIN")){
            return new Paladin(charName,level,false,wallet,stats,currMana,exp,new Inventory());
        }

        return null;
    }
}