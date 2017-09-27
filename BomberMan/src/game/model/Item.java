package game.model;

import game.manager.ImageStores;

import java.awt.*;

public class Item extends BaseItem {
    public static final int SIZE = 50;
    public static final int ITEM1 = 1;
    public static final int ITEM2 = 2;

    private int type;

    private Rectangle rectangle;
    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getType() {
        return type;
    }

    public Item(int x, int y, int type) {
        super(x, y);
        this.type = type;
        rectangle = new Rectangle(x,y,SIZE,SIZE);
    }

    public void draw(Graphics2D g2D){
        switch (type){
            case ITEM1:
                g2D.drawImage(ImageStores.IMG_ITEM1,x,y,SIZE,SIZE,null);
                break;
            case ITEM2:
                g2D.drawImage(ImageStores.IMG_ITEM2,x,y,SIZE,SIZE,null);
                break;
            default:
                break;
        }
    }
}
