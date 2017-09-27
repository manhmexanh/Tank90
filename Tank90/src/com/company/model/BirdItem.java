package com.company.model;

import com.company.maganer.ImageStore;

import java.awt.*;
import java.util.ArrayList;

public class BirdItem extends BaseItem {
    public static final int SIZE =4*25;
    public static final int TYPE_BIRD = 9;
    private int type;

    public BirdItem(int x, int y, int type) {
        super(x, y);
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
            case TYPE_BIRD:
                graphics2D.drawImage(ImageStore.IMAGE_BIRD, x, y, SIZE, SIZE, null);
                break;
            default:
                break;
        }
    }

}
