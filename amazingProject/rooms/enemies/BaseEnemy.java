package rooms.enemies;

public abstract class BaseEnemy {
    int roomNumber;
    public int hitpoints = 8;
    public double escapeLuck;
    public BaseEnemy() {}

    public abstract void attack(int power);

    public abstract void introduce();

    public abstract String turnPass();

    public abstract String giveReward();

    public abstract boolean checkIfEligible(int level, int turn);
}
