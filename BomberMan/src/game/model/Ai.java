package game.model;

import game.manager.ImageStores;

import java.awt.*;

public class Ai extends Bomber {
    public Ai(int x, int y, int orientation, int delay) {
        super(x, y, orientation, delay);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(ImageStores.IMG_AI, x, y, SIZE, SIZE, null);
    }
}
