// goal: not have to import items!!!!!!!!!!

import rooms.items.*;
import java.util.Scanner;

public class Main {

    // temporary D:
    final static int X_SIZE = 5;
    final static int Y_SIZE = 5;
    final static int NUM_ROOMS = 5;
    final static int HITPOINTS = 100;
    static boolean isGameOn = false;

    public static void main(String[] args) {
        // initializing
        Scanner sc = new Scanner(System.in);
        Control cont = new Control();
        cont.initControl(X_SIZE, Y_SIZE, NUM_ROOMS); // could probably be combined into constructor
        String userInput;
        isGameOn = true;
        // main game loop!
        while (isGameOn) {
            
            // checks for enemy
            if (cont.currentRoom.hasEnemy) {
                isGameOn = cont.combatHandler.startCombat(cont.currentRoom.enemy);
            }
            if (isGameOn) { // second check, in case combat ends badly D:
                System.out.println("You are in a scary room. What would you like to do?");
                System.out.println("1. Check Inventory");
                System.out.println("2. Traverse to another room");
                System.out.println("3. Inspect this room");
                userInput = sc.next();
                if (userInput.equals("1")) {
                    //inspect inventory
                    cont.inventoryInstance();
                } else if (userInput.equals("2")) {
                    //traverse to another room4
                    cont.traverseInstance();
                } else if (userInput.equals("3")) {
                    //inspect current room
                    cont.inspectCurrentRoomInstance();
                }
            cont.turnPassInternals();

            }

        }
        sc.close();
    }
}