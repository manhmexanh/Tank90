package com.company.model;

import com.company.maganer.ImageStore;
import com.company.views.Gui;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AITank extends BaseItem {
    private static final int INTERVAL_FIRE_BULLET = 1000;
    public static final int SIZE = 2 * 25-2;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    private int countDownBullet;
    private int countTime = 0;
    private int orientation;
    private Random random = new Random();


    public AITank(int x, int y, int delay, int orientation) {
        super(x, y, delay);
        this.orientation = orientation;
        countDownBullet = 0;

    }

    public void changeOrientation(int newOrientation) {
        this.orientation = newOrientation;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        switch (orientation) {
            case UP:
                graphics2D.drawImage(ImageStore.IMAGE_ENEMY_TANK_UP, x, y, SIZE, SIZE, null);
                break;
            case DOWN:
                graphics2D.drawImage(ImageStore.IMAGE_ENEMY_TANK_DOWN, x, y, SIZE, SIZE, null);
                break;
            case LEFT:
                graphics2D.drawImage(ImageStore.IMAGE_ENEMY_TANK_LEFT, x, y, SIZE, SIZE, null);
                break;
            case RIGHT:
                graphics2D.drawImage(ImageStore.IMAGE_ENEMY_TANK_RIGHT, x, y, SIZE, SIZE, null);
                break;
            default:
                break;

        }
    }

    public void moveOrtherAITank(ArrayList<MapItem> blockItems, long numberOfSleep, PlayerTank playerTank, AITank aiTank) {
        if (numberOfSleep % delay != 0) {
            return;
        }
        countTime++;
        switch (orientation) {
            case UP:
                y--;
                if (checkCoillisionItem(blockItems) || checkCoillisionPlayerTank(playerTank) || checkCoillisionOrtherAITank(aiTank)) {
                    y++;
                    changeOrientation(random.nextInt(4));
                }

                break;
            case LEFT:
                x--;
                if (checkCoillisionItem(blockItems) || checkCoillisionPlayerTank(playerTank) || checkCoillisionOrtherAITank(aiTank)) {
                    x++;
                    changeOrientation(random.nextInt(4));
                }

                break;
            case RIGHT:
                x++;
                if (checkCoillisionItem(blockItems) || checkCoillisionPlayerTank(playerTank) || checkCoillisionOrtherAITank(aiTank)) {
                    x--;
                    changeOrientation(random.nextInt(4));
                }


                break;

            case DOWN:
                y++;
                if (checkCoillisionItem(blockItems) || checkCoillisionPlayerTank(playerTank) || checkCoillisionOrtherAITank(aiTank)) {
                    y--;
                    changeOrientation(random.nextInt(4));
                }

                break;
            default:
                break;

        }
    }
    public void moveNomal(ArrayList<MapItem> blockItems, long numberOfSleep, PlayerTank playerTank,AITank aiTank) {
        if (numberOfSleep % delay != 0) {
            return;
        }
        countTime++;
        switch (orientation) {
            case UP:
                y--;
                if (checkCoillisionItem(blockItems) || checkCoillisionPlayerTank(playerTank) || checkCoillisionOrtherAITank(aiTank)==false) {
                    y++;
                    changeOrientation(random.nextInt(4));
                }

                break;
            case LEFT:
                x--;
                if (checkCoillisionItem(blockItems) || checkCoillisionPlayerTank(playerTank) || checkCoillisionOrtherAITank(aiTank)==false) {
                    x++;
                    changeOrientation(random.nextInt(4));
                }

                break;
            case RIGHT:
                x++;
                if (checkCoillisionItem(blockItems) || checkCoillisionPlayerTank(playerTank) || checkCoillisionOrtherAITank(aiTank)==false) {
                    x--;
                    changeOrientation(random.nextInt(4));
                }


                break;

            case DOWN:
                y++;
                if (checkCoillisionItem(blockItems) || checkCoillisionPlayerTank(playerTank) || checkCoillisionOrtherAITank(aiTank)==false) {
                    y--;
                    changeOrientation(random.nextInt(4));
                }

                break;
            default:
                break;

        }

        if (countTime % 500 == 0) {
            changeOrientation(random.nextInt(4));
            if (countTime == Integer.MAX_VALUE)
                countTime = 0;
        }
    }

    public void autoFire(ArrayList<Bullet> bullets) {
        if (countDownBullet == 0) {
            Bullet bullet = new Bullet(getX() + SIZE / 2 - Bullet.SIZE / 2, getY() + SIZE / 2 - Bullet.SIZE / 2, delay, orientation, Bullet.ENEMY_BULLET);
            bullets.add(bullet);
            countDownBullet = INTERVAL_FIRE_BULLET;
        }

    }

    public void countDownFireBulletAITank() {
        if (countDownBullet > 0) {
            countDownBullet -= 5;
        }
    }

    private boolean checkCoillisionItem(ArrayList<MapItem> blockMapItems) {
        boolean result = false;
        for (int i = 0; i < blockMapItems.size(); i++) {
            if (getRectangle().intersects(blockMapItems.get(i).getRectangle()) == true)
                result = true;
        }
        return result;
    }

    private boolean checkCoillisionPlayerTank(PlayerTank playerTank) {
        boolean result = false;
        if (getRectangle().intersects(playerTank.getRectangle()) == true)
            result = true;
        return result;
    }

    public boolean checkCoillisionOrtherAITank(AITank aiTank) {
        boolean result = false;
        if (getRectangle().intersects(aiTank.getRectangle()) == true)
            result = true;
        return result;
    }

    public boolean checkCoillisionBullet(ArrayList<Bullet> bullets) {
boolean result=false;
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).type == Bullet.PLAYER_BULLET) {
                if (getRectangle().intersects(bullets.get(i).getRectangle()) == true) {
                    bullets.remove(i);
                    Gui.wavPlayers[2].loop(1);
                    Gui.wavPlayers[2].playSound();
                    result= true;
                }
            }
        }
return result;
    }
}
