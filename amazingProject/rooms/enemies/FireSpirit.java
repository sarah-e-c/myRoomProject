package rooms.enemies;


public class FireSpirit extends BaseEnemy {

    boolean isOnFire = true;
    public FireSpirit() {
        hitpoints = 4;
        escapeLuck = 0.5;
    }

    @Override
    public String turnPass() {
        if (hitpoints < 2) {
            isOnFire = false;
        }

        if (isOnFire) {
            return "fireAttack";
            /*
            handler code
            if (s.equals("fireAttack")) {
                hitpoints -= 2;
                System.out.println("The spirit was on fire! Its attack dealt two damage.");
            }
            */
        } else {
            return "notOnFireAttack";
            /*
            handler code
            if (s.equals("notOnFireAttack")) {
                hitpoints -= 1;
                System.out.println("Luckily you extinguished the spirit before it could do much damage!");
                System.out.println("The spirit dealt one damage to you.");
            }
            */
        }
    }

    @Override
    public String giveReward() {
        return "dropTorch";
        /*
        handler code
        if (s.equals("dropTorch")) {
            itemHandler.inventory.add(new rooms.items.Torch());
            System.out.println("The fire spirit dropped a torch!");
        }
        */
    }

    @Override
    public void attack(int power) {
        hitpoints -= power;
        System.out.println("The fire spirit took " + power + " damage!");

    }

    @Override
    public void introduce() {
        System.out.println("A fire spirit appears!");
    }

    @Override
    public boolean checkIfEligible(int level, int turn) {
        if (turn != 0) {
            return true;
        } else {
            return false;
        }
    }
    
}
