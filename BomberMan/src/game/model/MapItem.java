package game.model;

import game.manager.ImageStores;

import java.awt.*;

public class MapItem extends BaseItem {
    public static final int SIZE = 50;
    public static final int ROCK = 1;
    public static final int BRICK = 2;
    public static final int DOOR = 3;

    private int type;

    private Rectangle rectangle;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public MapItem(int x, int y, int type) {
        super(x, y);
        this.type = type;
        rectangle = new Rectangle(x,y,SIZE,SIZE);
    }

    public void draw(Graphics2D g2D){
        switch (type){
            case BRICK:
                g2D.drawImage(ImageStores.IMG_BRICK,x,y,SIZE,SIZE,null);
                break;
            case ROCK:
                g2D.drawImage(ImageStores.IMG_ROCK,x,y,SIZE,SIZE,null);
                break;
            case DOOR:
                g2D.drawImage(ImageStores.IMG_DOOR,x,y,SIZE,SIZE,null);
                break;
            default:
                break;
        }
    }
}
