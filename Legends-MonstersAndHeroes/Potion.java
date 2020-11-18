import java.util.List;

// Representation of a potion that can be consumed
public class Potion extends Item implements Consumable{
    /*
    A potion can be used by a hero in order to increase one of their statistics by some
    amount. Potions are single-use items which means that once they are used they
    cannot be reused. Potions as well have a price, a name and a minimum hero level
    as a requirement to be used. You can assume for simplicity that the level
    requirement is enforced during the purchase (i.e. when a hero tries to buy an item
    with a level requirement bigger than their level they get rejected by the market).
     */

    // It has a buff associated with it
    private Buff effect;

    // Constructor
    public Potion(Name name, Money price, Level level, Buff effect){
        super(name, price, level, new Damage());
        setEffect(effect);
    }

    // setter and getter for effect
    public void setEffect(Buff effect) {
        this.effect = effect;
    }

    public Buff getEffect() {
        return effect;
    }

    // Consume the potion
    @Override
    public void consume(Buffable character) {
        List<String> buffedStats = effect.getStatsToBuff();
        int amountToBuffBy = effect.getBuffAmount();

        for(String stat: buffedStats){
            character.buff(stat, amountToBuffBy);
        }


    }

    public String toString(){
        return super.toString()+ ": " + effect.toString();
    }
}
