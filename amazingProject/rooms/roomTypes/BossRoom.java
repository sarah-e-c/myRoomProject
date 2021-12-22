package rooms.roomTypes;

import rooms.RoomData;

public class BossRoom extends BaseRoom {
    public BossRoom() {
        chanceForEnemy = 1;
    }

    @Override
    public void roomInit() {
        String enemy = RoomData.BOSS_TYPES[rand.nextInt(RoomData.BOSS_TYPES.length)];
        assignEnemy(enemy);
        hasEnemy = true;
    }}