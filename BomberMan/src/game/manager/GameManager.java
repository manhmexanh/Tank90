package game.manager;

import game.model.*;
import game.view.GamePlayPanel;
import game.view.MenuPanel;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import static game.view.Gui.wavPlayer;

public class GameManager {

    File file;
    Random random;

    private Bomber bomber;
    private ArrayList<MapItem> mapBrick;
    private ArrayList<MapItem> mapRock;
    private ArrayList<MapItem> mapDoor;
    private ArrayList<Bomb> bombs;
    private ArrayList<Ai> ais;
    private ArrayList<Item> items;
    private boolean isBomberDie = false;


    public static int slBombs = 1;
    public static int level = 1;
    public static String codeSave;

    public GameManager() {
        mapBrick = new ArrayList<>();
        mapRock = new ArrayList<>();
        mapDoor = new ArrayList<>();
        initMap(level);
        bomber = new Bomber(50, 125, Bomber.DOWN, 5);
        bombs = new ArrayList<>();
        ais = new ArrayList<>();
        items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            createAi();
        }
    }

    public boolean isBomberDie() {
        return isBomberDie;
    }

    public void initMap(int level) {
        try {
            mapBrick.clear();
            mapRock.clear();

            String path = getClass().getResource("/res/data/map" + level + ".txt").getPath();
            file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == '0') {
                        continue;
                    }
                    if (line.charAt(j) == '1') {
                        mapRock.add(new MapItem(j * MapItem.SIZE, i * MapItem.SIZE + 75, line.charAt(j) - 48));
                    } else {
                        if (line.charAt(j) == '2') {
                            mapBrick.add(new MapItem(j * MapItem.SIZE, i * MapItem.SIZE + 75, line.charAt(j) - 48));
                        } else {
                            MapItem item = new MapItem(j * MapItem.SIZE, i * MapItem.SIZE + 75, line.charAt(j) - 48);
                            mapDoor.add(item);
                        }
                    }
                }
                i++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createAi() {
        random = new Random();
        int rx;
        int ry;
        rx = random.nextInt(16) + 1;
        ry = random.nextInt(11) + 1;
        if (ry < 5 && rx < 5) {
            createAi();
            return;
        }
        if (ry % 2 == 0 || rx % 2 == 0) {
            createAi();
            return;
        }
        for (int i = 0; i < mapBrick.size(); i++) {
            if ((rx * 50 == mapBrick.get(i).getX() && ry * 50 + 75 == mapBrick.get(i).getY())) {
                createAi();
                return;
            }
        }
        ais.add(new Ai(rx * 50, ry * 50 + 75, 0, 10));
        return;
    }
    private void createItem(int x, int y, int type) {
        wavPlayer[5].loop(1);
        items.add(new Item(x, y, type));
    }


    public void drawMapBrick(Graphics2D g2D) {
        for (int i = 0; i < mapBrick.size(); i++) {
            mapBrick.get(i).draw(g2D);
        }
    }
    public void drawMapRock(Graphics2D g2D) {
        for (int i = 0; i < mapRock.size(); i++) {
            mapRock.get(i).draw(g2D);
        }
    }
    public void drawMapDoor(Graphics2D g2D) {
        for (int i = 0; i < mapDoor.size(); i++) {
            mapDoor.get(i).draw(g2D);
        }
    }
    public void drawBombs(Graphics2D g2D) {
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).draw(g2D);
        }
    }
    public void drawBomber(Graphics2D g2D) {
        bomber.draw(g2D);
    }
    public void drawAi(Graphics2D graphics2D) {
        for (int i = 0; i < ais.size(); i++) {
            ais.get(i).draw(graphics2D);
        }
    }
    public void drawItem(Graphics2D graphics2D) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).draw(graphics2D);
        }
    }


    public void moveBomber(long numberOfSleep) {

        if (checkBomberCollisionMapRock()) {
            return;
        }
        if (checkBomberCollisionMapBrick()) {
            return;
        }
        for (int i = 0; i < mapDoor.size(); i++) {
            if (checkBomberCollisionDoor(i)) {
                wavPlayer[1].stopSound();
                wavPlayer[3].loop(1);
                GamePlayPanel.isGamePlaying = false;
                JOptionPane.showMessageDialog(null, "You Win!");
                System.exit(0);
            }
        }
        for (int i = 0; i < items.size(); i++) {
            switch (checkBomberCollisionItem(i)) {
                case Item.ITEM1:
                    Bomb.power += 1;
                    GamePlayPanel.lbPower.setText("Power: "+Bomb.power);
                    break;
                case Item.ITEM2:
                    slBombs += 1;
                    GamePlayPanel.lbSlbomb.setText("MaxBomb: "+GameManager.slBombs);
                    break;
                default:
                    break;
            }
        }
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).isTt()) {
                if (checkBomberCollisionBombs(i)) {
                    return;
                }
            } else {
                if (!checkBomberCollisionBombs(i)) {
                    bombs.get(i).setTt(true);
                }
            }
        }
        bomber.move(numberOfSleep);
    }
    public void moveAi(long numberOfSleep) {
        for (int i = 0; i < ais.size(); i++) {
            if (checkAiConllisionBombs(i)) {
                random = new Random();
                ais.get(i).setOrientation(random.nextInt(4));
            }
            if (checkAiConllisionMap(i)) {
                random = new Random();
                ais.get(i).setOrientation(random.nextInt(4));
            }
            if (ais.get(i).getX() % 50 == 0 && numberOfSleep % 15 == 0 && ais.get(i).getY() % 50 == 25) {
                ais.get(i).setOrientation(random.nextInt(4));
            }
            ais.get(i).move(numberOfSleep);

            if (checkDieAi(i)) {
                wavPlayer[1].stopSound();
                wavPlayer[2].loop(Clip.LOOP_CONTINUOUSLY);
                isBomberDie = true;
                return;
            }
        }
    }


    public void changeBomberOrientation(int orientation) {
        bomber.setOrientation(orientation);
    }


    public void changeBombsBang(long a) {
        for (int i = 0; i < bombs.size(); i++) {
            if (!bombs.get(i).isBang()) {
                if (a - bombs.get(i).getTime1() >= 1500) {
                    bombs.get(i).setBang(true);
                    bombs.get(i).setTime2(a);
                    wavPlayer[4].loop(1);
                }
                if (checkFireLCollisionBombs(i) ||
                        checkFireUCollisionBombs(i) ||
                        checkFireRCollisionBombs(i) ||
                        checkFireDCollisionBombs(i)) {
                    bombs.get(i).setBang(true);
                    bombs.get(i).setTime2(a);
                    wavPlayer[4].loop(1);
                }
            }
            if (bombs.get(i).isBang()) {


                if (!checkFireLCollisionRock() && !checkFireLCollisionBrick()) {
                    bombs.get(i).moveFireL(a);
                }
                if (!checkFireUCollisionRock() && !checkFireUCollisionBrick()) {
                    bombs.get(i).moveFireU(a);
                }
                if (!checkFireRCollisionRock() && !checkFireRCollisionBrick()) {
                    bombs.get(i).moveFireR(a);
                }
                if (!checkFireDCollisionRock() && !checkFireDCollisionBrick()) {
                    bombs.get(i).moveFireD(a);
                }
                checkFireLCollisionAi();
                checkFireUCollisionAi();
                checkFireRCollisionAi();
                checkFireDCollisionAi();

                if (checkDieBombBang()) {
                    wavPlayer[1].stopSound();
                    wavPlayer[2].loop(Clip.LOOP_CONTINUOUSLY);
                        isBomberDie = true;
                    return;
                }
            }
            if (bombs.get(i).isBang()) {
                if (a - bombs.get(i).getTime2() > 95) {
                    bombs.remove(i);
                }
            }
        }
    }


    private boolean checkBomberCollisionMapBrick() {
        for (int i = 0; i < mapBrick.size(); i++) {
            if (bomber.getRectangle().intersects(mapBrick.get(i).getRectangle())) {
                return true;
            }
        }
        return false;
    }
    private boolean checkBomberCollisionMapRock() {
        for (int i = 0; i < mapRock.size(); i++) {
            if (bomber.getRectangle().intersects(mapRock.get(i).getRectangle())) {
                return true;
            }
        }
        return false;
    }
    private boolean checkBomberCollisionBombs(int i) {
        return bomber.getRectangle().intersects(bombs.get(i).getRectangle());
    }
    private boolean checkAiConllisionMap(int i) {
        for (int j = 0; j < mapRock.size(); j++) {
            if (ais.get(i).getRectangle().intersects(mapRock.get(j).getRectangle())) {
                return true;
            }
        }
        for (int j = 0; j < mapBrick.size(); j++) {
            if (ais.get(i).getRectangle().intersects(mapBrick.get(j).getRectangle())) {
                return true;
            }
        }
        return false;
    }
    private boolean checkAiConllisionBombs(int i) {
        for (int j = 0; j < bombs.size(); j++) {
            if (!bombs.get(j).isBang()) {
                if (ais.get(i).getRectangle().intersects(bombs.get(j).getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }


    public void plantingBomb(long time1) {
        if (bombs.size() >= slBombs) {
            return;
        }
        for (int i = 0; i < bombs.size(); i++) {
            if (bomber.getRectangle().intersects(bombs.get(i).getRectangle())) {
                return;
            }
        }
        bombs.add(new Bomb(bomber.getX(), bomber.getY(), false, time1));
        wavPlayer[7].loop(1);
    }


    private boolean checkDieBombBang() {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).isBang()) {
                if (bomber.getRectangle().intersects(bombs.get(i).getBoundFireL()) ||
                        bomber.getRectangle().intersects(bombs.get(i).getBoundFireU()) ||
                        bomber.getRectangle().intersects(bombs.get(i).getBoundFireR()) ||
                        bomber.getRectangle().intersects(bombs.get(i).getBoundFireD())) {
                    Rectangle rectangle1 = new Rectangle(bomber.getRectangle().intersection(bombs.get(i).getBoundFireL()));
                    Rectangle rectangle2 = new Rectangle(bomber.getRectangle().intersection(bombs.get(i).getBoundFireU()));
                    Rectangle rectangle3 = new Rectangle(bomber.getRectangle().intersection(bombs.get(i).getBoundFireR()));
                    Rectangle rectangle4 = new Rectangle(bomber.getRectangle().intersection(bombs.get(i).getBoundFireD()));
                    if ((rectangle1.width>10&&rectangle1.height>10)||
                            (rectangle1.width>10&&rectangle1.height>10)||
                            (rectangle2.width>10&&rectangle2.height>10)||
                            (rectangle2.width>10&&rectangle2.height>10)||
                            (rectangle3.width>10&&rectangle3.height>10)||
                            (rectangle3.width>10&&rectangle3.height>10)||
                            (rectangle4.width>10&&rectangle4.height>10)||
                            (rectangle4.width>10&&rectangle4.height>10)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean checkDieAi(int i) {
        if (bomber.getRectangle().intersects(ais.get(i).getRectangle())) {
            Rectangle rectangle = new Rectangle(bomber.getRectangle().intersection(ais.get(i).getRectangle()));
            if ((rectangle.width>10&&rectangle.height>10)||
                    (rectangle.width>10&&rectangle.height>10)){
                return true;
            }
        }
        return false;
    }


    private boolean checkFireLCollisionBrick() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < mapBrick.size(); j++) {
                if (bombs.get(i).getBoundFireL().intersects(mapBrick.get(j).getRectangle())) {
                    bombs.get(i).setFireL(true);
                    if (random.nextInt(20) == 1) {
                        createItem(mapBrick.get(j).getX(), mapBrick.get(j).getY(), random.nextInt(2) + 1);
                    }
                    mapBrick.remove(j);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkFireUCollisionBrick() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < mapBrick.size(); j++) {
                if (bombs.get(i).getBoundFireU().intersects(mapBrick.get(j).getRectangle())) {
                    bombs.get(i).setFireU(true);
                    if (random.nextInt(2) == 1) {
                        createItem(mapBrick.get(j).getX(), mapBrick.get(j).getY(), random.nextInt(2) + 1);
                    }
                    mapBrick.remove(j);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkFireRCollisionBrick() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < mapBrick.size(); j++) {
                if (bombs.get(i).getBoundFireR().intersects(mapBrick.get(j).getRectangle())) {
                    bombs.get(i).setFireR(true);
                    if (random.nextInt(20) == 1) {
                        createItem(mapBrick.get(j).getX(), mapBrick.get(j).getY(), random.nextInt(2) + 1);
                    }
                    mapBrick.remove(j);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkFireDCollisionBrick() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < mapBrick.size(); j++) {
                if (bombs.get(i).getBoundFireD().intersects(mapBrick.get(j).getRectangle())) {
                    bombs.get(i).setFireD(true);
                    if (random.nextInt(20) == 1) {
                        createItem(mapBrick.get(j).getX(), mapBrick.get(j).getY(), random.nextInt(2) + 1);
                    }
                    mapBrick.remove(j);
                    return true;
                }
            }
        }
        return false;
    }


    private boolean checkFireLCollisionRock() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < mapRock.size(); j++) {
                if (bombs.get(i).getBoundFireL().intersects(mapRock.get(j).getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkFireUCollisionRock() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < mapRock.size(); j++) {
                if (bombs.get(i).getBoundFireU().intersects(mapRock.get(j).getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkFireRCollisionRock() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < mapRock.size(); j++) {
                if (bombs.get(i).getBoundFireR().intersects(mapRock.get(j).getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkFireDCollisionRock() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < mapRock.size(); j++) {
                if (bombs.get(i).getBoundFireD().intersects(mapRock.get(j).getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean checkFireLCollisionAi() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < ais.size(); j++) {
                if (bombs.get(i).getBoundFireL().intersects(ais.get(j).getRectangle())) {
                    wavPlayer[6].loop(1);
                    ais.remove(j);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkFireUCollisionAi() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < ais.size(); j++) {
                if (bombs.get(i).getBoundFireU().intersects(ais.get(j).getRectangle())) {
                    wavPlayer[6].loop(1);
                    ais.remove(j);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkFireRCollisionAi() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < ais.size(); j++) {
                if (bombs.get(i).getBoundFireR().intersects(ais.get(j).getRectangle())) {
                    wavPlayer[6].loop(1);
                    ais.remove(j);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkFireDCollisionAi() {
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < ais.size(); j++) {
                if (bombs.get(i).getBoundFireD().intersects(ais.get(j).getRectangle())) {
                    wavPlayer[6].loop(1);
                    ais.remove(j);
                    return true;
                }
            }
        }
        return false;
    }


    private boolean checkFireLCollisionBombs(int j) {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).isBang()) {
                if (j == i) {
                    return false;
                }
                if (!bombs.get(j).isBang()) {
                    if (bombs.get(i).getBoundFireL().intersects(bombs.get(j).getRectangle())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean checkFireUCollisionBombs(int j) {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).isBang()) {
                if (j == i) {
                    return false;
                }
                if (!bombs.get(j).isBang()) {
                    if (bombs.get(i).getBoundFireU().intersects(bombs.get(j).getRectangle())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean checkFireRCollisionBombs(int j) {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).isBang()) {
                if (j == i) {
                    return false;
                }
                if (!bombs.get(j).isBang()) {
                    if (bombs.get(i).getBoundFireR().intersects(bombs.get(j).getRectangle())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean checkFireDCollisionBombs(int j) {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).isBang()) {
                if (j == i) {
                    return false;
                }
                if (!bombs.get(j).isBang()) {
                    if (bombs.get(i).getBoundFireD().intersects(bombs.get(j).getRectangle())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private int checkBomberCollisionItem(int i) {
        int a;
        if (bomber.getRectangle().intersects(items.get(i).getRectangle())) {
            a = items.get(i).getType();
            items.remove(i);
            return a;
        }
        return -1;
    }
    private boolean checkBomberCollisionDoor(int i) {
        if (bomber.getRectangle().intersects(mapDoor.get(i).getRectangle()) && ais.isEmpty()) {
            return true;
        }
        return false;
    }


    public void saveGame() {
        try {
            file = new File("/home/manhnt/Desktop/savebomb");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write((level + "").getBytes());
            fos.write("-".getBytes());
            fos.write((slBombs + "").getBytes());
            fos.write("-".getBytes());
            fos.write((Bomb.power + "").getBytes());
            fos.close();
            System.out.println("file save thanh cong! ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
