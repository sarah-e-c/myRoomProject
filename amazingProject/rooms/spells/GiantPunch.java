package rooms.spells;

public class GiantPunch extends BaseSpell {

    public GiantPunch() {
        name = "giant punch";
        level = 1;
    }

    @Override
    public String use() {
        return "giant punch";
        /*
        handler code
        if (s.equals(giantPunch)) {
            System.out.println("You attack with the might of a giant!");
            enemy.attack(4);
        }
        */
    }

    @Override
    public void printInfo() {
        System.out.println("A giant giant punch!");
        System.out.println("Deals 4 damage.");
    }

    
    
}
