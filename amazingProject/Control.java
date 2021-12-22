//class that handles gameplay aspects!!!!!!!!!!!!!! inventory, movement, scores, etc.

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import rooms.*;
import rooms.enemies.BaseEnemy;
import rooms.items.BaseCombatItem;
import rooms.items.BaseItem;
import rooms.roomTypes.BaseRoom;
import rooms.roomTypes.BossRoom;
import rooms.spells.*;

public class Control {
    // main variables!!!!
    Map centralMap = new Map();
    ArrayList<BaseRoom> allRooms = new ArrayList<BaseRoom>();
    public int currentX;
    public int currentY;
    public BaseRoom currentRoom;
    public ItemHandler itemHandler;
    public CombatHandler combatHandler;
    public ArrayList<rooms.items.BaseItem> allItems = new ArrayList<rooms.items.BaseItem>();
    public Control staticControl;
    public int hitpoints;
    public int protection;
    public boolean isDead;
    public int level;
    public int turn = 0;

    Random rand = new Random();
    Scanner sc = new Scanner(System.in);

    // initiates the control object. Is required
    // int xSize - x size of map
    // int ySize - y size of map
    // int numRooms -
    public void initControl(int xSize, int ySize, int numRooms) {
        hitpoints = Main.HITPOINTS;
        this.centralMap.createMap(xSize, ySize, numRooms);

        // establishing current location
        boolean flag = true;
        int possibleX;
        int possibleY;
        while (flag) {
            possibleX = rand.nextInt(xSize);
            possibleY = rand.nextInt(ySize);
            if (centralMap.map[possibleY][possibleX] != centralMap.FILLER_CONSTANT) {
                this.currentX = possibleX;
                this.currentY = possibleY;
                flag = false;
            }
        }

        // making the room types
        boolean bossRoomCreated = false;
        int roomType = rand.nextInt(RoomData.ROOM_TYPES.length); // this can be modified later if want
        for (int i = 0; i < Main.NUM_ROOMS; i++) {
            if (RoomData.ROOM_TYPES[roomType].equals(RoomData.ROOM_TYPES[0])) { // base room
                allRooms.add(new BaseRoom());

                // adding the boss room. Can only be one.
                // an improvement to this would be to make it so it is guaranteed
            } else if (RoomData.ROOM_TYPES[roomType].equals(RoomData.ROOM_TYPES[1])) { // boss room
                if (bossRoomCreated) {
                    allRooms.add(new BaseRoom());
                } else {
                    allRooms.add(new BossRoom());
                    bossRoomCreated = false;
                }
            }
            allRooms.get(allRooms.size() - 1).roomInit(); // initializes the last room added.
        }

        // init
        updateCurrentRoom();
        itemHandler = new ItemHandler();
        combatHandler = new CombatHandler();
        staticControl = this;
    }

    public void traverseInstance() {
        ArrayList<Integer> options = this.centralMap.traverseOptions(currentX, currentY, centralMap.map);
        String userInput;
        boolean flag = true;

        while (flag) {

            // prints traversal options
            System.out.println("The following directions are available for travel:");
            if (options.contains(centralMap.NORTH_CODE)) {
                System.out.println("1. North");
            }
            if (options.contains(centralMap.EAST_CODE)) {
                System.out.println("2. East");
            }
            if (options.contains(centralMap.SOUTH_CODE)) {
                System.out.println("3. South");
            }
            if (options.contains(centralMap.WEST_CODE)) {
                System.out.println("4. West");
            }
            System.out.println("5. Nevermind.");
            System.out.println("Which would you like to travel to?");

            userInput = sc.next();

            // gets out of the instance if user decides to return
            if (Integer.parseInt(userInput) == 5) {
                return;
            }

            int nowX = currentX;
            int nowY = currentY;
            try {
                int[] coordinates = centralMap.traverse(currentX, currentY, Integer.parseInt(userInput));
                currentX = coordinates[0];
                currentY = coordinates[1];
                updateCurrentRoom();
                flag = false;
            } catch (java.lang.IndexOutOfBoundsException | java.lang.NumberFormatException e) {
                System.out.println("Unable to traverse. Please input a valid value.");
                currentX = nowX;
                currentY = nowY;
            }
        }
    }

    public void inspectCurrentRoomInstance() {
        boolean flag = true;
        boolean flag1 = true;
        String userInput;
        int loopNum = 0;
        while (flag) {
            try {
                System.out.println("You are in a " + currentRoom);
                System.out.println("What would you like to do?");
                System.out.println("1. See what items are in here");
                System.out.println("2. Return");
                userInput = sc.next();
                if (Integer.parseInt(userInput) == 1) {
                    // new instance

                    while (flag1) {
                        try {
                            // printing out all the options

                            for (int i = 0; i < currentRoom.roomItems.size(); i++) {
                                System.out.println(i + 1 + ". " + currentRoom.roomItems.get(i));
                            }
                            if (loopNum == 0) {
                                System.out.println("There are no items in this room");
                                flag1 = false;
                            }
                            userInput = sc.next();
                            currentRoom.roomItems.get(Integer.parseInt(userInput)).access();
                            if (currentRoom.roomItems.get(Integer.parseInt(userInput)).canBeCarried) {
                                // new instance asking if it wants to be picked up
                                boolean flag2 = true;

                                while (flag2) {
                                    try {
                                        System.out.println("This item can be picked up. Add "
                                                + currentRoom.roomItems.get(Integer.parseInt(userInput))
                                                + "to your inventory?");
                                        System.out.println("1. Yes");
                                        System.out.println("2. No");
                                        String userInput1 = sc.next();
                                        if (Integer.parseInt(userInput1) == 1) {
                                            itemHandler.inventory
                                                    .add(currentRoom.roomItems.get(Integer.parseInt(userInput)));
                                            System.out.println(currentRoom.roomItems.get(Integer.parseInt(userInput))
                                                    + "added to your inventory.");
                                            flag2 = false;
                                        } else if (Integer.parseInt(userInput1) == 2) {
                                            flag2 = false;
                                        } else {
                                            throw new java.lang.NumberFormatException();
                                        }
                                    } catch (java.lang.NumberFormatException e) {
                                        System.out.println("Please input a valid value.");
                                    }
                                }

                            }
                            flag1 = false;
                            flag = false;

                        } catch (java.lang.NumberFormatException | java.lang.IndexOutOfBoundsException e) {
                            System.out.println("Please input a valid value.");
                        }
                    }
                } else if (Integer.parseInt(userInput) == 2) {
                    flag = false;
                } else {
                    throw new java.lang.NumberFormatException();
                }

            } catch (java.lang.NumberFormatException e) {
                System.out.println("Please input a valid value.");
            }
        }
    }

    // checks all items in room, does turn pass for all
    public void turnPassInternals() {
        itemHandler.itemTurnPass();
        itemHandler.checkAllItemsInInventory();
        itemHandler.checkAllItemsInRoom();
        itemHandler.events();
        combatHandler.updateCombatItems(itemHandler.inventory);
        turn += 1;
    }

    public void doACheck() {
        itemHandler.checkAllItemsInInventory();
        itemHandler.checkAllItemsInRoom();
        itemHandler.events();
        combatHandler.updateCombatItems(itemHandler.inventory);
    }

    public void inventoryInstance() {
        itemHandler.inventoryMenuAccess();
    }

    private void updateCurrentRoom() {
        currentRoom = allRooms.get(centralMap.map[currentY][currentX]);
    }

    // item handler class! Has inventory as well as inventory methods.
    protected class ItemHandler {
        ArrayList<rooms.items.BaseItem> inventory = new ArrayList<rooms.items.BaseItem>(); // main inventory ArrayList

        protected void printInventory() {
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println(i + 1 + ". " + inventory.get(i));
            }
            System.out.println(inventory.size() + 1 + ". Return.");
        }

        protected void inventoryMenuAccess() {
            String userInput;

            boolean flag = true;
            while (flag) {
                System.out.println("Which would you like to check?");
                this.printInventory();
                userInput = sc.next();
                try {
                    if ((Integer.parseInt(userInput) <= inventory.size()) && (Integer.parseInt(userInput) > 0)) {
                        inventory.get(Integer.parseInt(userInput) - 1).access();
                        flag = false;
                    } else if (Integer.parseInt(userInput) == inventory.size() + 1) {
                        flag = false;
                    } else {
                        throw new java.lang.NumberFormatException();
                    }
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("Please input a valid value.");
                }
            }
        }

        public void events() {
            // iterates through all items.
            for (int i = 0; i < allRooms.size(); i++) {
                for (rooms.items.BaseItem j : allRooms.get(i).roomItems) {
                    if (j.needsHandler) {
                        resolveHandlerAction(j.handlerAction(), j);
                    }

                }
            }
        }

        private void resolveHandlerAction(String action, rooms.items.BaseItem item) {
            // chest action
            if (action.equals("chest")) { // this is super messy :((((
                rooms.items.Chest tempItem = (rooms.items.Chest) item;
                ArrayList<Integer> keyNumbers = new ArrayList<Integer>();
                for (rooms.items.BaseItem j : itemHandler.inventory) {
                    if (j.itemType.equals("key")) {
                        keyNumbers.add(((rooms.items.Key) j).keyNumber);
                    }

                }
                if (keyNumbers.size() != 0) {

                    for (Integer i : keyNumbers) {
                        if (tempItem.checkKey(i)) {
                            tempItem.open(1);
                            return;
                        }
                    }
                    tempItem.open(0);

                } else {
                    tempItem.open(-1);
                }
            }
            if (action.equals("dark orb explode")) {
                ((rooms.items.DarkOrb) item).explode();
                hitpoints -= 1;
            }
        }

        public void checkAllItemsInInventory() {
            for (rooms.items.BaseItem i : inventory) {
                i.check();
            }
        }

        public void checkAllItemsInRoom() {
            for (rooms.items.BaseItem i : currentRoom.roomItems) {
                i.check();
            }
        }

        public void itemTurnPass() {
            // turn passes for all items in inventory
            for (rooms.items.BaseItem i : inventory) {
                i.turnPass();
            }
            // turn passes for all items in room
            for (rooms.items.BaseItem i : currentRoom.roomItems) {
                i.turnPass();
            }

        }
    }

    // combat handler class! handles combat.
    protected class CombatHandler {
        ArrayList<BaseCombatItem> combatItemsInInventory = new ArrayList<BaseCombatItem>();
        ArrayList<BaseSpell> spells = new ArrayList<BaseSpell>();

        public CombatHandler() {
            spells.add(new Punch()); // bad!!!!
        }

        BaseEnemy enemyFighting;
        double protection = 1;

        // main combat instance method
        // returns false if you die.
        protected boolean startCombat(BaseEnemy enemy) {

            // add check to see if is eligible to be attacked;
            if (!enemy.checkIfEligible(level, turn)) {
                return true;
            }
            boolean inBattle = true;
            boolean turnPassed = true;
            enemy.introduce();
            while (inBattle) {

                if (turnPassed) {
                    for (BaseCombatItem i : combatItemsInInventory) {
                        resolveCombatChecks(i.combatCheck());
                    }
                }

                // user part of turn
                System.out.println("What would you like to do?");
                System.out.println("1. Use an attack");
                System.out.println("2. Use an item");
                System.out.println("3. Try to escape");
                String userInput = sc.next();
                try { // protecting against invalid things
                      // attacks - needs work
                    if (Integer.parseInt(userInput) == 1) {
                        System.out.println("Which attack would you like to use?");
                        for (int i = 0; i < spells.size(); i++) {
                            System.out.println(i + 1 + ". " + spells.get(i));
                        }
                        userInput = sc.next();

                        useAttack(spells.get(Integer.parseInt(userInput) - 1).use(), enemy);
                        turnPassed = true;

                        // use items
                    } else if (Integer.parseInt(userInput) == 2) {
                        System.out.println("Which item would you like to use?");
                        int numItem = 1;
                        for (BaseCombatItem i : combatItemsInInventory) {
                            System.out.print(numItem + ". ");
                            numItem += 1;
                            System.out.println(i);
                        }
                        System.out.println(numItem + ". Return");
                        userInput = sc.next();
                        if ((Integer.parseInt(userInput) <= combatItemsInInventory.size())
                                && (Integer.parseInt(userInput) > 0)) {
                            resolveCombatAction(combatItemsInInventory.get(Integer.parseInt(userInput) - 1).combatUse(),
                                    enemy, Integer.parseInt(userInput) - 1);
                            turnPassed = true;

                        } else if (Integer.parseInt(userInput) == combatItemsInInventory.size() + 1) {
                            turnPassed = false;
                        } else {
                            throw new java.lang.NumberFormatException();
                        }

                        // escape
                    } else if (Integer.parseInt(userInput) == 3) {
                        if (Math.random() > enemy.escapeLuck) {
                            System.out.println("You escaped!");
                            inBattle = false;

                        } else {
                            System.out.println("You did not escape.");
                            turnPassed = true;
                        }
                    } else {
                        throw new java.lang.NumberFormatException();
                    }

                } catch (java.lang.NumberFormatException | java.lang.IndexOutOfBoundsException e) {
                    System.out.println("Please input a valid value.");
                    turnPassed = false;
                }
                if (turnPassed) {
                    // checks if enemy ded
                    if (enemy.hitpoints < 1) {
                        inBattle = false;
                        System.out.println("You won the battle!");
                        getEnemyReward(enemy.giveReward());
                        currentRoom.killEnemy();
                        return true;
                    }

                    // enemy part of attack
                    if (inBattle) {
                        // second check to see if battle has ended because could have escaped
                        resolveEnemyAction(enemy.turnPass());
                    }

                    // checks if u ded
                    if (hitpoints < 1) {
                        inBattle = false;
                        return false;
                    }
                }
            }
            return true;
        }

        // needs to be updated every turn
        protected void updateCombatItems(ArrayList<BaseItem> inventory) {
            combatItemsInInventory.clear();
            for (BaseItem i : inventory) {
                if (i.isCombatItem) {
                    combatItemsInInventory.add((BaseCombatItem) i);
                }
            }
        }

        // correspons to using an item
        private void resolveCombatAction(String s, BaseEnemy enemy, int index) {
            if (s.equals("emptyChestThrow")) {
                System.out.println("You threw the empty chest at the " + enemy + "!");
                enemy.attack(3);
                itemHandler.inventory.remove(index);
                this.updateCombatItems(itemHandler.inventory);
            }
            // torch actions
            if (s.equals("lit torch")) {
                System.out.println("You throw the lit torch at the " + enemy + "!");
                itemHandler.inventory.remove(index);
                enemy.attack(4);
                this.updateCombatItems(itemHandler.inventory);

            }
            if (s.equals("unlit torch")) {
                System.out.println("You throw the unlit torch at the " + enemy + "!");
                itemHandler.inventory.remove(index);
                enemy.attack(2);
                this.updateCombatItems(itemHandler.inventory);

            }
        }

        // corresponds to having a turn pass
        private void resolveCombatChecks(String s) {

        }

        // corresponds to doing what an enemy needs
        private void resolveEnemyAction(String s) {

            // FireSpirit attacks
            if (s.equals("fireAttack")) { // if fire spirit is on fire
                System.out.println("The spirit was on fire!");
                takeDamage(2);

            }
            if (s.equals("notOnFireAttack")) { // if fire spirit is not on fire
                System.out.println("Luckily you extinguished the spirit before it could do much damage!");
                takeDamage(1);
            }
            // Giant attacks
            if (s.equals("giant punch")) { // default giant attack
                System.out.println("The giant punched you in the face!");
                takeDamage(4);
            }
            if (s.equals("protect")) {
                System.out.println("The giant readies for an attack...");
            }
            if (s.equals("mega smash")) {
                System.out.println("The giant unleashes its anger!!!!");
                takeDamage(10);
            }
        }

        // corresponds to getting enemy drops/xp
        private void getEnemyReward(String s) {
            // fire spirit drops torch
            if (s.equals("dropTorch")) {
                itemHandler.inventory.add(new rooms.items.Torch());
                System.out.println("The fire spirit dropped a torch!");
            }
        }

        private void takeDamage(int power) {
            hitpoints -= (int) (power / protection);
            System.out.println("You took " + (int) (power / protection) + " damage.");
        }

        private void useAttack(String s, BaseEnemy enemy) {
            if (s.equals("giantPunch")) {
                System.out.println("You attack with the might of a giant!");
                enemy.attack(4);
            }
            if (s.equals("punch")) {
                System.out.println("You throw a punch at the enemy!");
                enemy.attack(1);
            }

        }

    }

    // public int getInventoryIndex(String id) {
    // for (int i = 0; i < this.inventory.size(); i++) {
    // if (id == this.inventory.get(i).itemId) {
    // return i;
    // }
    // }
    // return -1;
    // }

    // public boolean removeFromInventory(String id) {
    // this.inventory.remove(getInventoryIndex(id));
    // return true;
    // }

    // public boolean removeFromInventory(int index) {
    // this.inventory.remove(index);
    // return true;
    // }
}