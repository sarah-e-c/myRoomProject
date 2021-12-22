package rooms.items;

import java.util.Random;
import java.util.Scanner;

public class Key extends BaseItem{
    Random rand = new Random();
    static boolean[] availableKeys = new boolean[]{true, true}; //must be changed if this is needed.
    Scanner sc = new Scanner(System.in);
    public int keyNumber;
    @Override
    public void check(){}
    public Key() {
        itemType = "key";
        canBeCarried = true;
        // assigning an untaken key number to the chest
        int chestSetNum = rand.nextInt(numChestsSets);
        boolean flag = true;
        while (flag) {
            if (availableKeys[chestSetNum]) {
                keyNumber = chestSetNum;
                availableKeys[chestSetNum] = false;
                flag = false;
            }
            chestSetNum = rand.nextInt(numChestsSets);
        }
        keys++;
    }

    @Override
    public void access() {
        System.out.println("This is a tiny gold key. I wonder what it could open?");
    }

    @Override
    public String handlerAction(){
        return null;
    }

    public void turnPass(){}

    
}
