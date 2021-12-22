package rooms.roomTypes;
//will be abstract eventually

import java.util.ArrayList;

import rooms.enemies.BaseEnemy;
import rooms.enemies.FireSpirit;
import rooms.enemies.Giant;
import rooms.items.*;
import rooms.RoomData;
import java.util.Random;

public class BaseRoom {
    final int ITEMS_PER_ROOM = 2;
    public ArrayList<rooms.items.BaseItem> roomItems = new ArrayList<rooms.items.BaseItem>();
    protected double chanceForEnemy;
    public BaseEnemy enemy;
    public boolean hasEnemy;

    Random rand = new Random();


    public void roomInit() {

        //places items in room
        int dataNum;
        chanceForEnemy = 1;
        boolean flag = true;
        for (int i = 0; i < ITEMS_PER_ROOM; i++) {
            flag = true;
            while (flag) {
                dataNum = rand.nextInt(RoomData.DEFAULT_ITEM_TYPES.length);
                if (RoomData.DEFAULT_ITEM_TYPES[dataNum] == "chest") {
                    if (BaseItem.canMakeChest()) {
                        addItem(RoomData.DEFAULT_ITEM_TYPES[dataNum]);
                        flag = false;
                    }
                } else if (RoomData.DEFAULT_ITEM_TYPES[dataNum] == "key") {
                    if (BaseItem.canMakeKey()) {
                        addItem(RoomData.DEFAULT_ITEM_TYPES[dataNum]);
                        flag = false;
                    }

                } else {
                    addItem(RoomData.DEFAULT_ITEM_TYPES[dataNum]);
                    flag = false;
                }
            }
        }

        //assigns an enemy to the room
        if (Math.random() < chanceForEnemy) {
            String enemy = RoomData.DEFAULT_ENEMY_TYPES[rand.nextInt(RoomData.DEFAULT_ENEMY_TYPES.length)];
            assignEnemy(enemy);
            hasEnemy = true;
        } else {
            hasEnemy = false;
        }
    }

    public void killEnemy() {
        hasEnemy = false;
    }

//assigns item
    private void addItem(String itemType) {
        if (itemType.equals(RoomData.DEFAULT_ITEM_TYPES[0])) { //torch
            roomItems.add(new Torch());
        }
        if (itemType.equals(RoomData.DEFAULT_ITEM_TYPES[1])) { //key
            roomItems.add(new Key());
        }
        if (itemType.equals(RoomData.DEFAULT_ITEM_TYPES[2])) { //chest
            roomItems.add(new Chest());
    }
}

//assigns enemy
    protected void assignEnemy(String enemyType) {
        if (enemyType.equals(RoomData.DEFAULT_ENEMY_TYPES[0]))  { //fire spirit
            this.enemy = new FireSpirit();
        }
        if (enemyType.equals(RoomData.BOSS_TYPES[0]))  { //giant
            this.enemy = new Giant();
        }
    }
}
