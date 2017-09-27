package com.company.model;

import com.company.maganer.GameManager;
import com.company.maganer.ImageStore;
import com.company.views.Gui;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerTank extends BaseItem {
    private static final int INTERVAL_FIRE_BULLET = 1000;
    public static final int SIZE = 2 * 25-2;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public static int locationX = 0;
    public static int locationY = 0;
    private int orientation;

    public static int lives = 2;
    private int starLevel = 0;
    private int type;

    public PlayerTank(int x, int y, int delay, int orientation) {
        super(x, y, delay);

        this.orientation = orientation;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setStarLevel(int starLevel) {
        this.starLevel = starLevel;
    }

    public void upStarLevel() {
        starLevel += 1;
    }

    public int changeOrientation(int newOrientation) {
        this.orientation = newOrientation;
        return orientation;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        switch (orientation) {
            case UP:
                graphics2D.drawImage(ImageStore.IMAGE_PLAYER_TANK_UP, x, y, SIZE, SIZE, null);
                break;
            case DOWN:
                graphics2D.drawImage(ImageStore.IMAGE_PLAYER_TANK_DOWN, x, y, SIZE, SIZE, null);
                break;
            case LEFT:
                graphics2D.drawImage(ImageStore.IMAGE_PLAYER_TANK_LEFT, x, y, SIZE, SIZE, null);
                break;
            case RIGHT:
                graphics2D.drawImage(ImageStore.IMAGE_PLAYER_TANK_RIGHT, x, y, SIZE, SIZE, null);
                break;
            default:

                break;
        }
    }

    public void move(ArrayList<MapItem> mapItems, long numberOfSleep, ArrayList<AITank> aiTanks,ArrayList<BirdItem> birdItems) {
        if (numberOfSleep % delay != 0) {
            return;
        }
        int x0 = getX();
        int y0 = getY();
        switch (orientation) {
            case UP:
                y--;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            case DOWN:
                y++;
                break;
            default:
                break;
        }
        if (checkCoillisionItem(mapItems) || checkCoillisionAITank(aiTanks)||checkCoillisionBird(birdItems)) {
            x = x0;
            y = y0;
        }
    }


    public void fire(ArrayList<Bullet> bullets) {
        if (GameManager.countDownBulletPlayerTank == 0) {
            Bullet bullet = new Bullet(getX() + SIZE / 2 - Bullet.SIZE / 2, getY() + SIZE / 2 - Bullet.SIZE / 2, delay, orientation, Bullet.PLAYER_BULLET);
            bullets.add(bullet);


            GameManager.countDownBulletPlayerTank = INTERVAL_FIRE_BULLET;
        }

    }

    public void countDownFireBulletPlayerTank() {

    }

    public boolean checkCoillisionBullet(ArrayList<Bullet> bullets) {
        boolean result = false;
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).type == Bullet.ENEMY_BULLET) {
                if (getRectangle().intersects(bullets.get(i).getRectangle()) == true) {
                    bullets.remove(i);
                    result = true;
                    Gui.wavPlayers[2].loop(1);
                    Gui.wavPlayers[2].playSound();
                }
            }
        }
        return result;
    }

    private boolean checkCoillisionItem(ArrayList<MapItem> blockMapItems) {
        boolean result = false;
        for (int i = 0; i < blockMapItems.size(); i++) {
            if (getRectangle().intersects(blockMapItems.get(i).getRectangle()) == true)
                result = true;
        }
        return result;
    }

    private boolean checkCoillisionBird(ArrayList<BirdItem> birdItems) {
        boolean result = false;
        for (int i=0;i<birdItems.size();i++) {
            if (getRectangle().intersects(birdItems.get(i).getRectangle()) == true)
                result = true;

        }
        return result;
    }

    private boolean checkCoillisionAITank(ArrayList<AITank> aiTanks) {
        boolean result = false;
        for (int i = 0; i < aiTanks.size(); i++) {
            if (getRectangle().intersects(aiTanks.get(i).getRectangle()) == true)
                result = true;
        }
        return result;
    }
}


