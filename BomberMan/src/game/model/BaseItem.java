package game.model;

public abstract class BaseItem {
    protected int x;
    protected int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BaseItem(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
