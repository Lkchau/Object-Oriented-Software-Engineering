public class CharacterFactory implements  CharacterFactoryInterface{
    // Returns a abstract character factory
    @Override
    public CharacterAbstractFactory getCharacterAbstractFactory(String characterType) {
        if(characterType.equalsIgnoreCase("HERO")){
            return new HeroFactory();
        }else{
            return new MonsterFactory();
        }
    }
}
