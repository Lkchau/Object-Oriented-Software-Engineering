import java.util.HashMap;

// Spell factory for different types of spells
public class SpellFactory extends ItemAbstractFactory{

        public Item getItem(String spellType, String spellName){
            HashMap<String, String[]> spellInfo = FileParser.getFileParser().readMonstersAndHeroesHashNameToInfo(spellType + "Spells");
            String[] currSpellInfo = spellInfo.get(spellName.replaceAll(" ",""));
            assert currSpellInfo.length == 4;
            Name spell = new Name(spellName);
            Money price = new Money(Integer.parseInt(currSpellInfo[0]));
            Level level = new Level(Integer.parseInt(currSpellInfo[1]));
            Damage damage = new Damage(Integer.parseInt(currSpellInfo[2]));
            Mana mana = new Mana(Integer.parseInt(currSpellInfo[3]));

            if(spellType == null){
                return null;
            }
            if(spellType.equalsIgnoreCase("ICE")){
                return new IceSpell(spell, price, level, damage, mana);

            } else if(spellType.equalsIgnoreCase("LIGHTNING")){
                return new LightningSpell(spell, price, level, damage, mana);

            } else if(spellType.equalsIgnoreCase("FIRE")){
                return new FireSpell(spell, price, level, damage, mana);
            }

            return null;
        }
}
