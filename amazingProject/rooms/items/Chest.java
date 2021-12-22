package rooms.items;

import java.util.Random;
import java.util.Scanner;


public class Chest extends BaseItem implements BaseCombatItem{
    int chestNumber;
    static boolean[] availableChests = new boolean[]{true, true};
    Scanner sc = new Scanner(System.in);
    Random rand = new Random();
    int pointValue;
    boolean hasBeenOpened = false;
    String userInput;


    public Chest() {
        itemType = "chest";
        pointValue = 200 + rand.nextInt(200);
        isCombatItem = false;

        //assigning an untaken chest number to the chest
        int chestSetNum = rand.nextInt(numChestsSets);
        boolean flag = true;
        while (flag) {
        if (availableChests[chestSetNum]) {
            chestNumber = chestSetNum;
            availableChests[chestSetNum] = false;
            flag = false;
        }
        chestSetNum = rand.nextInt(numChestsSets);
    }
    chests++;
    }

    //returns integer point value. This code is kind of messy becasuse not everything can access everything.
    public int open(int resultNumber) {
        if (resultNumber == 1) {
            System.out.println("The key is a fit!");
            hasBeenOpened = true;
            canBeCarried = true;
            isCombatItem = true;
            needsHandler = false;
            return pointValue;
        } else if (resultNumber == -1) {
            System.out.println("You might need a key to open this...");
            needsHandler = false;
            return 0;
        } else {
            System.out.println("It doesn't look like this key fits this chest.");
            return 0;
        }
    }

    public boolean checkKey(int keyNumber) {
        if (keyNumber == this.chestNumber) {
            return true;
        } else {
            return false;
        }

    }

    public void access() {
        if (hasBeenOpened) {
            System.out.println("an open and empty chest.");
        } else {
            System.out.println("A closed, locked chest.");
            System.out.println("Attempt to open?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            userInput = sc.next();
            if (userInput == "1") {
                needsHandler = true;
            }
    }
}
    public String handlerAction() {
        return "chest";
    }

    public void turnPass(){};
    public void check(){}

    @Override
    public String combatUse() {
        return "emptyChestThrow";
        /*
        handler code
        if (s.equals("emptyChestThrow")) {
            System.out.println("You threw the empty chest at the " + enemy "!");
            enemy.attack(3);
            inventoryHandler.inventory.remove(selectedIndex);
            this.updateCombatInventory();
        }
        */

    }

    @Override
    public String combatCheck() {
        return "";
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return "An open and empty Chest";
    }
}
//handler code to resolve action
// if (action == "chest") { // this is super messy :((((
//     rooms.items.Chest tempItem = (rooms.items.Chest) item;
//     ArrayList<Integer> keyNumbers = new ArrayList<Integer>();
//     for (rooms.items.BaseItem j : cont.itemHandler.inventory) {
//         if (j.itemType == "key") {
//             keyNumbers.add(((rooms.items.Key) j).keyNumber);
//         }

//     }
//     if (keyNumbers.size() != 0) {

//         for (Integer i : keyNumbers) {
//             if (tempItem.checkKey(i)) {
//                 tempItem.open(1);
//                 return;
//             }
//         }
//         tempItem.open(0);

//     } else {
//         tempItem.open(-1);
//     }
// }