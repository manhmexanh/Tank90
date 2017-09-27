package com.company.model;

import com.company.views.Gui;

import java.awt.*;
import java.util.ArrayList;

public abstract class BaseItem {
    protected int x;
    protected int y;
    protected int delay;

    public BaseItem(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public BaseItem(int x, int y, int delay) {
        this.x = x;
        this.y = y;
        this.delay = delay;
    }

    public void move(long numberOfSleep) {
        if (numberOfSleep % delay != 0) {
            return;
        }
    }
    public abstract void draw(Graphics2D graphics2D);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
