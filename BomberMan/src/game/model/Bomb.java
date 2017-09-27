package game.model;

import game.manager.ImageStores;

import java.awt.*;

public class Bomb extends BaseItem {

     public static final int SIZE = 50;
    Rectangle rectangle;
    public static int power = 1;
    private boolean tt;
    private long time1;
    private long time2;
    private boolean bang = false;
    Fire fire;

    public Bomb(int x, int y, boolean tt, long time1) {
        super(x, y);
        this.tt = tt;
        this.time1 = time1;
        if (x % 50 != 0) {
            int a = x / 50;
            if (x % 50 >= 25) {

                x = a * 50 + 50;
            } else {
                x = a * 50;
            }
        }
        if ((y - 25) % 50 != 0) {
            int a = (y - 25) / 50;
            if ((y - 25) % 50 >= 25) {
                y = a * 50 + 50 + 25;
            } else {
                y = a * 50 + 25;
            }
        }
        this.x = x;
        this.y = y;
        rectangle = new Rectangle(x, y, SIZE, SIZE);
        fire =new Fire(x,y);
    }

    public long getTime2() {
        return time2;
    }

    public void setTime2(long time2) {
        this.time2 = time2;
    }

    public boolean isBang() {
        return bang;
    }

    public long getTime1() {
        return time1;
    }

    public void setBang(boolean bang) {
        this.bang = bang;
    }

    public boolean isTt() {
        return tt;
    }

    public void setTt(boolean tt) {
        this.tt = tt;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void draw(Graphics2D graphics2D) {
        if (!bang) {
            graphics2D.drawImage(ImageStores.IMG_BOMB, x, y, SIZE, SIZE, null);
        } else {
            fire.draw(graphics2D);
            graphics2D.drawImage(ImageStores.IMG_BOMBBANG, x, y, SIZE, SIZE, null);
        }
    }

    public void moveFireL(long numberOfsleep){
        fire.moveL(numberOfsleep);
    }
    public void moveFireU(long numberOfsleep){
        fire.moveU(numberOfsleep);
    }
    public void moveFireR(long numberOfsleep){
        fire.moveR(numberOfsleep);
    }
    public void moveFireD(long numberOfsleep){
        fire.moveD(numberOfsleep);
    }

    public Rectangle getBoundFireL(){
        return fire.getRectangleL();
    }
    public Rectangle getBoundFireU(){
        return fire.getRectangleU();
    }
    public Rectangle getBoundFireR(){
        return fire.getRectangleR();
    }
    public Rectangle getBoundFireD(){
        return fire.getRectangleD();
    }

    public void setFireL(boolean l){
        fire.setL(l);
    }
    public void setFireU(boolean u){
        fire.setU(u);
    }
    public void setFireR(boolean r){
        fire.setR(r);
    }
    public void setFireD(boolean d){
        fire.setD(d);
    }
}
