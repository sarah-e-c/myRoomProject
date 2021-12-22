package rooms.spells;

public abstract class BaseSpell {
    String name;
    int level;

    public abstract String use();

    public abstract void printInfo();

    @Override
    public String toString() {
        return name;
    };
}
