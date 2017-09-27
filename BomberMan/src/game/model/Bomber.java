package game.model;

import game.manager.ImageStores;

import java.awt.*;

public class Bomber extends BaseItem {
    public static final int SIZE = 50;
    Rectangle rectangle;
    public static final int LEFT = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    protected int delay;
    protected int orientation;

    public Bomber(int x, int y, int orientation, int delay) {
        super(x, y);
        this.delay = delay;
        this.orientation = orientation;
        rectangle = new Rectangle(x, y, SIZE, SIZE);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void move(long numberOfSleep) {
        if (numberOfSleep%delay!=0){
            return;
        }
        switch (orientation) {
            case LEFT:
                x--;
                rectangle.setLocation(x - 1, y);
                break;
            case UP:
                y--;
                rectangle.setLocation(x, y - 1);
                break;
            case DOWN:
                y++;
                rectangle.setLocation(x, y + 1);
                break;
            case RIGHT:
                x++;
                rectangle.setLocation(x + 1, y);
                break;
            default:
                break;
        }
    }

    public void setOrientation(int orientation) {
        if (this.orientation != orientation) {
            this.orientation = orientation;
            switch (orientation) {
                case LEFT:
                    rectangle.setLocation(x - 1, y);
                    break;
                case UP:
                    rectangle.setLocation(x, y - 1);
                    break;
                case DOWN:
                    rectangle.setLocation(x, y + 1);
                    break;
                case RIGHT:
                    rectangle.setLocation(x + 1, y);
                    break;
                default:
                    break;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        switch (orientation) {
            case LEFT:
                graphics2D.drawImage(ImageStores.IMG_BOMBER_LEFT, x, y, SIZE, SIZE, null);
                break;
            case UP:
                graphics2D.drawImage(ImageStores.IMG_BOMBER_UP, x, y, SIZE, SIZE, null);
                break;
            case RIGHT:
                graphics2D.drawImage(ImageStores.IMG_BOMBER_RIGHT, x, y, SIZE, SIZE, null);
                break;
            case DOWN:
                graphics2D.drawImage(ImageStores.IMG_BOMBER_DOWN, x, y, SIZE, SIZE, null);
                break;
            default:
                break;
        }
    }



}
