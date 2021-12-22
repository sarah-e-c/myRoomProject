package rooms.spells;

public class Punch extends BaseSpell {

    public Punch() {
        name = "punch";
        level = 1;
    }

    @Override
    public String use() {
        return "punch";
        /*
        handler code
        if (s.equals("punch")) {
            System.out.println("You throw a punch at the enemy!");
            enemy.attack(1);
        }
        */
    }

    @Override
    public void printInfo() {
        System.out.println("A basic punch.");
        System.out.println("Deals 1 damage.");

    }
    
}
