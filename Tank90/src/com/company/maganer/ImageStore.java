package com.company.maganer;


import javax.swing.*;
import java.awt.*;

public class ImageStore {
    public static final Image IMAGE_BRICK = getImage("/res/drawable/brick.png");
    public static final Image IMAGE_ROCK = getImage("/res/drawable/rock.png");
    public static final Image IMAGE_TREE = getImage("/res/drawable/tree.png");
    public static final Image IMAGE_WATER = getImage("/res/drawable/water.png");
    public static final Image IMAGE_BIRD = getImage("/res/drawable/bird.png");
    public static final Image IMAGE_BULLET = getImage("/res/drawable/bullet.png");
    public static final Image IMAGE_PLAYER_TANK_UP = getImage("/res/drawable/player_green_up.png");
    public static final Image IMAGE_PLAYER_TANK_DOWN = getImage("/res/drawable/player_green_down.png");
    public static final Image IMAGE_PLAYER_TANK_LEFT = getImage("/res/drawable/player_green_left.png");
    public static final Image IMAGE_PLAYER_TANK_RIGHT = getImage("/res/drawable/player_green_right.png");
    public static final Image IMAGE_ENEMY_TANK_UP = getImage("/res/drawable/bossyellow_up.png");
    public static final Image IMAGE_ENEMY_TANK_DOWN = getImage("/res/drawable/bossyellow_down.png");
    public static final Image IMAGE_ENEMY_TANK_LEFT = getImage("/res/drawable/bossyellow_left.png");
    public static final Image IMAGE_ENEMY_TANK_RIGHT = getImage("/res/drawable/bossyellow_right.png");
    public static final Image IMAGE_PANEL = getImage("/res/drawable/panel.jpg");

    private static Image getImage(String path) {
        return new ImageIcon(ImageStore.class.getResource(path)).getImage();
    }

}