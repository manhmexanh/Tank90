package com.company.model;

import com.company.maganer.GameManager;
import com.company.maganer.ImageStore;
import com.company.views.Gui;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bullet extends BaseItem {
    public static final int SIZE = 2 * 4;
    public static final int ENEMY_BULLET = 0;
    public static final int PLAYER_BULLET = 1;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    private int orientation;
    public int type;

    public Bullet(int x, int y, int delay, int orientation, int type) {
        super(x, y, delay);
        this.orientation = orientation;
        this.type = type;

    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        switch (type) {
            case ENEMY_BULLET:
                graphics2D.drawImage(ImageStore.IMAGE_BULLET, x, y, SIZE, SIZE, null);
                break;
            case PLAYER_BULLET:
                graphics2D.drawImage(ImageStore.IMAGE_BULLET, x, y, SIZE, SIZE, null);

                break;
            default:
                break;
        }
    }

    public void move(long numberOfSleep) {
        switch (orientation) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            default:
                break;

        }

    }

    public boolean checkCoillisionItem(ArrayList<MapItem> blockMapItems) {
        boolean result = false;
        for (int i = 0; i < blockMapItems.size(); i++) {
            if (getRectangle().intersects(blockMapItems.get(i).getRectangle()) == true) {
                result = true;
                if (blockMapItems.get(i).getType() == MapItem.TYPE_BRICK) {
                    blockMapItems.remove(i);
                }

                if (blockMapItems.get(i).getType() == MapItem.TYPE_WATER) {
                    return false;
                }
            }
        }

        return result;
    }
    public boolean checkCoillisionBird(ArrayList<BirdItem> birdItems) {
        boolean result = false;
        for (int i=0;i<birdItems.size();i++) {
            if (getRectangle().intersects(birdItems.get(i).getRectangle()) == true) {
                result = true;
                if(getType()==Bullet.ENEMY_BULLET) {
                    birdItems.remove(i);
                    GameManager.isPlayerTankDie=true;
                }
            }
        }
        return result;
    }
}

