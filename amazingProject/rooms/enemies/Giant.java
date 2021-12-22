package rooms.enemies;

import java.util.Random;

public class Giant extends BaseEnemy {
    Random rand = new Random();
    int anger;
    boolean isProtected;
    boolean warnedOfAnger = false;

    @Override
    public String turnPass() {
        anger += 1;
        int attackNumber = rand.nextInt(3);
        if ((anger > 3) && !warnedOfAnger) {
            System.out.println("The giant is getting angry....");
            warnedOfAnger = true;
        }
        if (attackNumber == 0) {
            return "giant punch";
            /*
             * handler code if (s.equals("giant punch")) {
             * System.out.println("The giant punched you in the face!"); takeDamage(4); }
             */
        }

        if (attackNumber == 1) {
            isProtected = true;
            return "protect";
            /*
             * handler code if (s.equals("protect")) {
             * System.out.println("The giant readies for an attack..."); }
             */
        }

        if (attackNumber == 2) {
            if (anger > 4) {
                anger = 0;
                return "mega smash";
                /*
                 * handler code if (s.equals("mega smash")) {
                 * System.out.println("The giant unleashes its anger!!!!"); takeDamage(10); }
                 */
            } else {
                return ("giant punch");
            }
        }

        return null;
    }

    @Override
    public String giveReward() {
        return "learn giant punch";
        /*
        handler code
        if (s.equals("learn giant punch")) {
            System.out.println("You learn the spell: Giant Punch!");
            spells.add(new GiantPunch());
        }
        */
    }

    @Override
    public void attack(int power) {
        if (isProtected) {
            System.out.println("The giant was protected from the attack!");
            isProtected = false;
        } else {
            System.out.println("The giant took " + power + " damage!");
            hitpoints -= power;
        }
    }

    //text for what the enemy says when it is first encountered
    @Override
    public void introduce() {
        System.out.println("A giant giant appears!");

    }

    @Override
    public boolean checkIfEligible(int level, int turn) {
        if (level > 5) {
            return true;
        }  else {
            return false;
        }      
    }

}
