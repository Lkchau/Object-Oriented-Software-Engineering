import java.util.ArrayList;
import java.util.List;
// Abstract idea of a rpg
public abstract class RolePlayingGame implements Game {
    protected ConcreteMenuFactory menus;
    protected List<Party> parties;

    public RolePlayingGame(){
        setMenus(new ConcreteMenuFactory());
        setParties(new ArrayList<>());
    }

    public void setMenus(ConcreteMenuFactory menus) {
        this.menus = menus;
    }

    public ConcreteMenuFactory getMenus() {
        return menus;
    }

    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }
}
