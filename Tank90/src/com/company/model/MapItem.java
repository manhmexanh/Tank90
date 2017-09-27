package com.company.model;

import com.company.maganer.ImageStore;

import java.awt.*;
import java.util.ArrayList;

public class MapItem extends BaseItem {
    public static final int SIZE = 25;
    public static final int TYPE_WATER = 2;
    public static final int TYPE_BRICK = 1;

    public static final int TYPE_ROCK = 5;
    public static final int TYPE_TREE = 4;
    public static final int TYPE_LOCATION = 0;
    private int type;

    public MapItem(int x, int y,int type) {
        super(x, y);
        this.type=type;
    }
    public Rectangle getRectangle() {
        return new Rectangle(x, y, SIZE, SIZE);
    }
    @Override
    public void draw(Graphics2D graphics2D) {
        switch (type) {
            case TYPE_WATER:
                graphics2D.drawImage(ImageStore.IMAGE_WATER, x, y, SIZE, SIZE, null);
                break;
            case TYPE_ROCK:
                graphics2D.drawImage(ImageStore.IMAGE_ROCK, x, y, SIZE, SIZE, null);
                break;

                case TYPE_BRICK:
                    graphics2D.drawImage(ImageStore.IMAGE_BRICK, x, y, SIZE, SIZE, null);
                    break;
            case TYPE_TREE:
                graphics2D.drawImage(ImageStore.IMAGE_TREE, x, y, SIZE, SIZE, null);
                break;

            case TYPE_LOCATION:
                graphics2D.drawImage(ImageStore.IMAGE_PLAYER_TANK_UP,x,y,SIZE,SIZE,null);
                break;
            default:
                break;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}