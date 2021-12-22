package rooms.items;

import java.util.Random;

public class DarkOrb extends BaseItem {
    int turns;
    boolean willExplode;

    Random rand = new Random();
    public DarkOrb() {
        itemType = "dark orb";
        canBeCarried = true;
        willExplode = rand.nextBoolean();
        if (willExplode) {
            turns = rand.nextInt(50);
        } else {
            turns = Integer.MAX_VALUE;
        }
    }

    public void check() {}
    public void turnPass(){
        if (this.isInInventory) {
            this.turns--;
        }
        if (turns <= 0) {
            needsHandler = true;
        }
    }

    public void access() {
        System.out.println("A dark orb. It looks dangerous.");
        
    }

    public String handlerAction() {
        return "dark orb explode";
    }

    public void explode() {
        needsHandler = false;
        System.out.println("A dark orb in your inventory exploded!");
        System.out.println("You take 1 damage.");
    }
}
