//pretty much entirely static class. Used for map, map traverse operations, etc.

//structure question: assign place of rooms first, and then type?????
package rooms;

import java.util.ArrayList;
import java.util.Random;

/*
fun little map of x and y directions for reference :)))
0 1 2 3 4 
1 
2
3
4
*/

public class Map {
    public int[][] map;
    final public int FILLER_CONSTANT = -1;
    final public int NORTH_CODE = 1;
    final public int EAST_CODE = 2;
    final public int SOUTH_CODE = 3;
    final public int WEST_CODE = 4;
    static Random rand = new Random();

    //creates and stores into int[][] map a map with a custom size and number of rooms!
    //features:
    //the rooms will always be touching one other room.
    //the rooms have numbers, starting at 0 to be easily used in arrays!
    //always returns true.
    // int xSize - how wide you would like the map to be!
    // int ySize - how tall you would like the map to be!
    // int numRooms - number of rooms you would like the map to have!
    public boolean createMap(int xSize, int ySize, int numRooms) {
        int[][] tempMap = new int[ySize][xSize];
        ArrayList<Integer[]> coordinates = new ArrayList<Integer[]>();
        Integer[] coordinateSet;
        Integer[] newCoordinateSet = new Integer[2];

        // tenporary roomarray
        for (int i = 0; i < ySize; i++) {
            for (int k = 0; k < xSize; k++) {
                tempMap[i][k] = FILLER_CONSTANT;
            }
        }

        // make it so each has one touching.
        // uses a numbering system
        int randx = rand.nextInt(xSize);
        int randy = rand.nextInt(ySize);
        tempMap[randy][randx] = 0;
        coordinates.add(new Integer[2]);
        coordinates.get(0)[0] = randx;
        coordinates.get(0)[1] = randy;
        int currentRoomNumber = 1;
        
        
        while (currentRoomNumber < numRooms) { // saying that the loop should continue until max rooms reached
            coordinateSet = coordinates.get(rand.nextInt(coordinates.size()));
            if (!isCornered(tempMap, coordinateSet[1], coordinateSet[0])) {
                newCoordinateSet = addAnyAdjacentRoom(tempMap, coordinateSet[1], coordinateSet[0]);
                coordinates.add(newCoordinateSet);
                tempMap[newCoordinateSet[1]][newCoordinateSet[0]] = currentRoomNumber;
                currentRoomNumber++;
            }
        }
        map = tempMap;
        return true;
    }

    //prints the stored values of the map into the console. Can be used for testing.
    public void printRawMap(){
        for (int i = 0; i < map.length; i++) {
            for (int k = 0; k < map[0].length; k++) {
                if (map[i][k] == FILLER_CONSTANT) {
                    System.out.print(map[i][k]);
                } else {
                    System.out.print(" " + map[i][k]);
                }
               
            }
            System.out.println();
        }
    }

    //returns an ArrayList<Integer> of possible directions available to be traveled to.
    //Uses integer direction constants. Check constants for reference.
    //int x - current x position of player
    //int y - current y position of player
    //int[][] map - map to be used as reference
    public ArrayList<Integer> traverseOptions(int x, int y, int[][] map) {
        ArrayList<Integer> availableDirections = new ArrayList<Integer>();
        if (isNorthARoom(map, y, x)){
            availableDirections.add(NORTH_CODE);
        }
        if (isEastARoom(map, y, x)) {
            availableDirections.add(EAST_CODE);
        }
        if (isSouthARoom(map, y, x)) {
            availableDirections.add(SOUTH_CODE);
        }
        if (isWestARoom(map, y, x)) {
            availableDirections.add(WEST_CODE);
        }
        return availableDirections;
    }

    //returns coordinates in [x,y] of the new position of the player based on direction.
    //int x - current x position of player
    //int y - current y position of player
    //int direction integer direction of direction to be traveled. check constants for reference.
    public int[] traverse(int x, int y, int direction) {
        int[] returnVal = new int[2];
        if (direction == NORTH_CODE) {
            returnVal[0] = x;
            returnVal[1] = y - 1;
            return returnVal;
        }
        if (direction == EAST_CODE) {
            returnVal[0] = x + 1;
            returnVal[1] = y;
            return returnVal;
        }
        if (direction == SOUTH_CODE) {
            returnVal[0] = x;
            returnVal[1] = y + 1;
            return returnVal;
        }
        if (direction == WEST_CODE) {
            returnVal[0] = x - 1;
            returnVal[1] = y;
            return returnVal;
        }
        return returnVal;
    }

    //used in the createMap() method. returns coordinates in [x,y] of an open space for a room to be added.
    //!!! WARNING !!! use the isCornered() method before using this. The position can't be completely blocked.
    //int[][] tempMap - map to be used as reference
    //int y - y position of an existing room
    //int x - x position of an existing room
    private Integer[] addAnyAdjacentRoom(int[][] tempMap, int y, int x) {
        int direction;
        Integer[] coordinates = new Integer[2];
        boolean flag = false;
        while (!flag) {
            direction = rand.nextInt(4) + 1; //directions are integers 1-4, check contant codes
            if (direction == NORTH_CODE) {
                if (!isNorthTaken(tempMap, y, x)) {
                    coordinates[1] = y-1;
                    coordinates[0] = x;
                    flag = true;
                }
            } else if (direction == EAST_CODE) {
                if (!isEastTaken(tempMap, y, x)) {
                    coordinates[1] = y;
                    coordinates[0] = x+1;
                    flag = true;
                }
            } else if (direction == SOUTH_CODE) {
                if (!isSouthTaken(tempMap, y, x)) {
                    coordinates[1] = y + 1;
                    coordinates[0] = x;
                    flag = true;
                }
            } else if (direction == WEST_CODE) {
                if (!isWestTaken(tempMap, y, x)) {
                    coordinates[1] = y;
                    coordinates[0] = x - 1;
                    flag = true;
                }
            }
        }
        return coordinates;
    }

    //checks if any surrounding areas of x and y contain the filler constant.
    //int[][] tempMap - map to be used as reference
    //int y - y position of position to be checked
    //int x - x position of position to be checked
    private boolean isCornered(int[][] tempMap, int y, int x) {
        return ((isNorthTaken(tempMap, y, x)) && (isEastTaken(tempMap, y, x) && isSouthTaken(tempMap, y, x) && isWestTaken(tempMap, y, x)));
    }

    //checks if the north position to the current position contains the filler constant.
    //returns true if the north position is the edge of the map or contains a room.
    //int[][] tempMap - map to be used as reference
    //int y - y position of position to be checked
    //int x - x position of position to be checked
    private boolean isNorthTaken(int[][] tempMap, int y, int x) {
        if (y - 1 >= 0) { // if north target is out of range, stop.
            if (tempMap[y - 1][x] == FILLER_CONSTANT) {
                return false;
            }
        }
        return true;
    }

    //checks if the east position to the current position contains the filler constant.
    //returns true if the north position is the edge of the map or contains a room.
    //int[][] tempMap - map to be used as reference
    //int y - y position of position to be checked
    //int x - x position of position to be checked
    private boolean isEastTaken(int[][] tempMap, int y, int x) {
        if (x + 1 <= tempMap[0].length - 1) { // if east target is out of range, stop.
            if (tempMap[y][x + 1] == FILLER_CONSTANT) {
                return false;
            }
        }
        return true;
    }

    //checks if the south position to the current position contains the filler constant.
    //returns true if the north position is the edge of the map or contains a room.
    //int[][] tempMap - map to be used as reference
    //int y - y position of position to be checked
    //int x - x position of position to be checked
    private boolean isSouthTaken(int[][] tempMap, int y, int x) {
        if (y + 1 <= tempMap.length - 1) { // if south target is out of range, stop.
            if (tempMap[y + 1][x] == FILLER_CONSTANT) {
                return false;
            }
        }
        return true;
    }

    //checks if the west position to the current position contains the filler constant.
    //returns true if the north position is the edge of the map or contains a room.
    //int[][] tempMap - map to be used as reference
    //int y - y position of position to be checked
    //int x - x position of position to be checked
    private boolean isWestTaken(int[][] tempMap, int y, int x) {
        if (x - 1 >= 0) { // if west target is out of range, stop.
            if (tempMap[y][x - 1] == FILLER_CONSTANT) {
                return false;
            }
        }

        return true;
    }

    //the following methods check if a room exists in said direction.
    //at first I tried to use the above methods for this, but the logic is slightly different, so I needed separate ones.
    

    //checks if the north direction contains a room.
    //int[][] tempMap - map to be checked. Is technically never used with a temporary map.
    //int y - current y position.
    //int x - current x position.
    private boolean isNorthARoom(int[][] tempMap, int y, int x) {
        // checks north direction
        if (y - 1 >= 0) {
            if (tempMap[y - 1][x] != FILLER_CONSTANT) {
                return true;
            }
        }
        return false;
    }

    //checks if the east direction contains a room.
    //int[][] tempMap - map to be checked. Is technically never used with a temporary map.
    //int y - current y position.
    //int x - current x position.
    private boolean isEastARoom(int[][] tempMap, int y, int x) {
        if (x + 1 <= tempMap[0].length - 1) { 
            if (tempMap[y][x + 1] != FILLER_CONSTANT) {
                return true;
            }
        }
        return false;
    }

    //checks if the south direction contains a room.
    //int[][] tempMap - map to be checked. Is technically never used with a temporary map.
    //int y - current y position.
    //int x - current x position.
    private boolean isSouthARoom(int[][] tempMap, int y, int x) {
        if (y + 1 <= tempMap.length - 1) { 
            if (tempMap[y + 1][x] != FILLER_CONSTANT) {
                return true;
            }
        }
        return false;
    }

    //checks if the west direction contains a room.
    //int[][] tempMap - map to be checked. Is technically never used with a temporary map.
    //int y - current y position.
    //int x - current x position.
    private boolean isWestARoom(int[][] tempMap, int y, int x) {
        if (x - 1 >= 0) {
            if (tempMap[y][x - 1] != FILLER_CONSTANT) {
                return true;
            }
        }

        return false;
    }


}
