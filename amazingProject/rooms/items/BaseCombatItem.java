package rooms.items;

public interface BaseCombatItem {
    //when is accessed
    public String combatUse();
    //turn per turn
    public String combatCheck();

    @Override
    public String toString();
}
