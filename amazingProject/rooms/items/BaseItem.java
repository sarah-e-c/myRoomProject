//base item class!!!!!!!!!!!!

package rooms.items;

public abstract class BaseItem {

//systematically named
    int[] coordinates;
    public boolean canBeCarried = false;
    public boolean isInInventory;
    public boolean isCombatItem;
    public String itemType; //corresponds to item types in RoomData.
    public boolean needsHandler = false;

    static int numChestsSets = 2; //if this wants to be changeable, need to have a for loop in constructor.

    public static int chests = 0;
    static int keys = 0;

    public BaseItem(){}
    public abstract void access();
    public abstract void check();
    public abstract String handlerAction();
    public abstract void turnPass();
    
    @Override
    public String toString() {
        return itemType;
    }


    public static boolean canMakeChest() {
        return (chests < numChestsSets);
    }

    public static boolean canMakeKey() {
        return (keys < numChestsSets);
    }

}