package game.model;

import game.manager.ImageStores;

import java.awt.*;

public class Fire extends BaseItem {
    int l = 1;
    int u = 1;
    int r = 1;
    int d = 1;
    public static final int SIZE = 50;
    Rectangle rectangleL;
    Rectangle rectangleU;
    Rectangle rectangleR;
    Rectangle rectangleD;
    private boolean isL;
    private boolean isU;
    private boolean isR;
    private boolean isD;

    public void setL(boolean l) {
        isL = l;
    }

    public void setU(boolean u) {
        isU = u;
    }

    public void setR(boolean r) {
        isR = r;
    }

    public void setD(boolean d) {
        isD = d;
    }

    public Rectangle getRectangleL() {
        return rectangleL;
    }

    public Rectangle getRectangleU() {
        return rectangleU;
    }

    public Rectangle getRectangleR() {
        return rectangleR;
    }

    public Rectangle getRectangleD() {
        return rectangleD;
    }

    public Fire(int x, int y) {
        super(x, y);
        rectangleL = new Rectangle(x, y, SIZE, SIZE);
        rectangleU = new Rectangle(x, y, SIZE, SIZE);
        rectangleR = new Rectangle(x, y, SIZE, SIZE);
        rectangleD = new Rectangle(x, y, SIZE, SIZE);
        isL = false;
        isU = false;
        isR = false;
        isD = false;
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(ImageStores.IMG_BOMBBANG2, x - l, y, SIZE + l, SIZE, null);
        graphics2D.drawImage(ImageStores.IMG_BOMBBANG1, x, y - u, SIZE, SIZE + u, null);
        graphics2D.drawImage(ImageStores.IMG_BOMBBANG2, x, y, SIZE + r, SIZE, null);
        graphics2D.drawImage(ImageStores.IMG_BOMBBANG1, x, y, SIZE, SIZE + d, null);
        rectangleL.setSize(SIZE + l, SIZE);
        rectangleL.setLocation(x - l, y);
        rectangleU.setSize(SIZE, SIZE + u);
        rectangleU.setLocation(x, y - u);
        rectangleR.setSize(SIZE + r, SIZE);
        rectangleR.setLocation(x, y);
        rectangleD.setSize(SIZE, SIZE + d);
        rectangleD.setLocation(x, y);
    }

    public void moveL(long numberOfsleep) {
        if (isL) {
            return;
        }

        if (numberOfsleep % 2 != 0) {
            return;
        }
        l += Bomb.power;
    }

    public void moveU(long numberOfsleep) {
        if (isU) {
            return;
        }

        if (numberOfsleep % 2 != 0) {
            return;
        }
        u += Bomb.power;
    }

    public void moveR(long numberOfsleep) {
        if (isR) {
            return;
        }

        if (numberOfsleep % 2 != 0) {
            return;
        }
        r += Bomb.power;
    }

    public void moveD(long numberOfsleep) {
        if (isD) {
            return;
        }

        if (numberOfsleep % 2 != 0) {
            return;
        }
        d += Bomb.power;
    }
}
