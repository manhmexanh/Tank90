package com.company.maganer;

import com.company.model.*;
import com.company.views.GamePanel;
import com.company.views.Gui;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    private int countAITank = 2;
    private ArrayList<MapItem> blockMapItems;
    private ArrayList<BirdItem> birdItems;
    private ArrayList<MapItem> trees;
    private ArrayList<MapItem> locations;
    private ArrayList<Bullet> bullets;
    private ArrayList<AITank> aiTanks;
    public File file;
    private int numberTankDie;
    private PlayerTank playerTank;
    public static int level = 1;
    public static int score = 0;
    public static int countDownBulletPlayerTank=0;
    public static boolean isPlayerTankDie =false;
    public static boolean isPlayerTankWin =false;
    private Random random = new Random();

    public GameManager() {
        blockMapItems = new ArrayList<>();
        trees = new ArrayList<>();
        locations = new ArrayList<>();
        bullets = new ArrayList<>();
        aiTanks = new ArrayList<>();
        birdItems =new ArrayList<>();
        createMapItems(level);
        createlistAITanks();
        createPlayerTank();

    }

    public void createMapItems(int level) {
        try {
            blockMapItems.clear();
            trees.clear();
            locations.clear();

            String path = getClass().getResource("/res/assets/map" + level + ".txt").getPath();
             file = new File(path);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            int j = 0;
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    int type = line.charAt(i) - 48;
                    switch (type) {
                        case BirdItem.TYPE_BIRD:
                            birdItems.add(new BirdItem(i*MapItem.SIZE,j*MapItem.SIZE,type));
                            break;
                        case MapItem.TYPE_BRICK:
                        case MapItem.TYPE_ROCK:
                        case MapItem.TYPE_WATER:
                            blockMapItems.add(new MapItem(i * MapItem.SIZE, j * MapItem.SIZE, type));
                            break;
                        case MapItem.TYPE_TREE:
                            trees.add(new MapItem(i * MapItem.SIZE, j * MapItem.SIZE, type));
                            break;
                        case MapItem.TYPE_LOCATION:
                            locations.add(new MapItem(i * MapItem.SIZE, j * MapItem.SIZE, type));
                        default:
                            break;
                    }
                }
                line = bufferedReader.readLine();
                j++;
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void drawBlockItem(Graphics2D graphics2D) {
        for (int i = 0; i < blockMapItems.size(); i++)
            blockMapItems.get(i).draw(graphics2D);
    }
    public void drawBirdItem(Graphics2D graphics2D){
        for (int i = 0; i < birdItems.size(); i++)
            birdItems.get(i).draw(graphics2D);
    }

    public void drawTrees(Graphics2D graphics2D) {
        for (int i = 0; i < trees.size(); i++) {
            trees.get(i).draw(graphics2D);
        }
    }

    private void createNumberAITankInMap() {
        for (int i = 1; i < level; i++) {
            countAITank++;
            if (countAITank >= 2) {
                countAITank = 2;
            }
        }

    }

    private void createAITank(int i) {
        int x = MapItem.SIZE * 2 + (i+1)*MapItem.SIZE*4;
        int y = MapItem.SIZE * 1;
        int oriention = random.nextInt(4);
        AITank aiTank = new AITank(x, y, 4, oriention);
        aiTanks.add(aiTank);
    }

    public void createlistAITanks() {
        createNumberAITankInMap();
        for (int i = 0; i < countAITank; i++) {
            createAITank(i);
        }
    }

    public void drawListAITank(Graphics2D graphics2D) {
        for (int i = 0; i < aiTanks.size(); i++) {
            aiTanks.get(i).draw(graphics2D);
        }
    }

    public void moveListAITank(long numberOfSleep) {
        for (int i = 0; i < aiTanks.size(); i++) {
            for (int j = 0; j < aiTanks.size(); j++) {
                if(i!=j)
                aiTanks.get(i).moveOrtherAITank(blockMapItems, numberOfSleep, playerTank, aiTanks.get(j));
                else
                    aiTanks.get(i).moveNomal(blockMapItems,numberOfSleep,playerTank,aiTanks.get(j));
            }
        }
    }



    public void dieAITank() {
        for (int i = 0; i < aiTanks.size(); i++) {
            if (aiTanks.get(i).checkCoillisionBullet(bullets)) {
                aiTanks.remove(i);
                score += 20;
                GamePanel.lbScore.setText("Score: " + GameManager.score);
                numberTankDie++;
            }
        }
        if(numberTankDie==2){
            isPlayerTankWin=true;
        }
    }

    public void autoFireAITank() {
        for (int i = 0; i < aiTanks.size(); i++)
            aiTanks.get(i).autoFire(bullets);
    }

    public void countDownBulletAITank() {
        for (int i = 0; i < aiTanks.size(); i++)
            aiTanks.get(i).countDownFireBulletAITank();
    }


    public void createPlayerTank() {
        if (PlayerTank.locationX == 0 && PlayerTank.locationY == 0) {
            playerTank = new PlayerTank(locations.get(locations.size() - 16).getX(), locations.get(locations.size() - 16).getY(),
                    4, PlayerTank.UP);
        } else {
            playerTank = new PlayerTank(PlayerTank.locationX, PlayerTank.locationY,
                    4, PlayerTank.UP);
        }

    }

    public void changePlayerTankOrientation(int newOrientation) {
        playerTank.changeOrientation(newOrientation);
    }


    public void movePlayerTank(long numberOfSleep) {
        playerTank.move(blockMapItems, numberOfSleep, aiTanks,birdItems);
    }

    public void fireForPlayerTank() {
        playerTank.fire(bullets);

    }

    public void countDownBulletPlayerTank() {
        if (countDownBulletPlayerTank > 0) {
            Gui.wavPlayers[4].loop(1);
            Gui.wavPlayers[4].playSound();
            countDownBulletPlayerTank -= 10;
        }

    }
    public boolean diePlayerTank() {
        boolean result = false;
        if (playerTank.checkCoillisionBullet(bullets)) {
            PlayerTank.lives--;
            GamePanel.lbLive.setText("Lives: " + PlayerTank.lives);
            if (PlayerTank.lives != 0) {
                createPlayerTank();
            } else {
                isPlayerTankDie=true;
            }
            result = true;
        }
        return result;
    }

    public void drawPlayerTank(Graphics2D graphics2D) {
        playerTank.draw(graphics2D);
    }

    public void drawBullets(Graphics2D graphics2D) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(graphics2D);

        }
    }

    public void moveBullets(long numberOfSleep) {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            bullets.get(i).move(numberOfSleep);
            if (bullets.get(i).getY() <= 0 || bullets.get(i).getX() <= 0 || bullets.get(i).getY() >= Gui.HEIGHT_FRAME || bullets.get(i).getX() >= Gui.WIDTH_FRAME) {
                bullets.remove(i);
            }
        }
        deleteBullets();
    }

    private void deleteBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).checkCoillisionItem(blockMapItems)||bullets.get(i).checkCoillisionBird(birdItems)) {
                if(bullets.get(i).getType()==Bullet.PLAYER_BULLET) {
                    Gui.wavPlayers[2].loop(1);
                    Gui.wavPlayers[2].playSound();
                }
                bullets.remove(i);
            }
        }

    }

    public void saveGame() {
        random = new Random();
        try {
            file = new File("/home/manhnt/Desktop/SaveTank");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write((level + "").getBytes());
            fos.write("-".getBytes());
            fos.write((playerTank.getX() + "").getBytes());
            fos.write("-".getBytes());
            fos.write((playerTank.getY() + "").getBytes());
            fos.close();
            System.out.println("file save thanh cong! " + "/home/manhnt/Desktop/SaveTank");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


