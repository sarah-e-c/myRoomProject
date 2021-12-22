//torch!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

package rooms.items;

import java.util.Scanner;

public class Torch extends BaseItem implements BaseCombatItem {
    private boolean isOn;
    Scanner sc = new Scanner(System.in);

    public Torch() {
        itemType = "torch";
        canBeCarried = true;
    }

    @Override
    public String handlerAction() {
        return "torch";
    }

    @Override
    public void check() {
    };

    // toggles if it is on or not

    @Override
    public void access() {
        boolean flag = true;
        if (isOn) {
            while (flag) {
                System.out.println("A lit torch. Blow out?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                String userInput = sc.next();
                try {
                    if (Integer.parseInt(userInput) == 1) {
                        this.toggle();
                        System.out.println("You blew out the torch.");
                        flag = false;
                    } else {
                        System.out.println("The torch remains lit");
                        flag = false;
                    }
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("Please input a valid value.");
                }
            }
        } else {
            System.out.println("An unlit torch.");
        }
    }

    @Override
    public void turnPass() {
    }

    private boolean toggle() {
        if (isOn) {
            isOn = false;
        } else {
            isOn = true;
        }
        return true;
    }

    @Override
    public String combatUse() {
        if (isOn) {
            return "lit torch";
        } else {
            return "unlit torch";
        }

        /*
         * handler code //torch actions if (s.equals("lit torch")) {
         * System.out.println("You throw the lit torch at the " + enemy + "!");
         * itemHandler.inventory.remove(index); enemy.attack(4); } if
         * (s.equals("unlit torch")) {
         * System.out.println("You throw the unlit torch at the " + enemy + "!");
         * itemHandler.inventory.remove(index); enemy.attack(2);
         * 
         * }
         */
    }

    @Override
    public String combatCheck() {
        // TODO Auto-generated method stub
        return null;
    }

}