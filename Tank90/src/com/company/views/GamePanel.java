package com.company.views;

import com.company.maganer.GameManager;
import com.company.maganer.ImageStore;
import com.company.model.MapItem;
import com.company.model.PlayerTank;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.BitSet;

public class GamePanel extends BasePanel implements Runnable {

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnDieBackMenu onDieBackMenu;
    private OnWinBackMenu onWinBackMenu;

    public void setOnDieBackMenu(OnDieBackMenu onDieBackMenu) {
        this.onDieBackMenu = onDieBackMenu;
    }

    public void setOnWinBackMenu(OnWinBackMenu onWinBackMenu) {
        this.onWinBackMenu = onWinBackMenu;
    }

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    private JButton btnBack;
    private JButton btnSave;
    private Font font;

    public static boolean isGamePlaying;
    public static boolean isGamePause;
    public static JLabel lbLive;
    public static JLabel lbLevel;
    public static JLabel lbScore;
    private GameManager gameManager;
    private BitSet bitSet;

    public GamePanel() {
        super();
        startGame();

    }

    public GamePanel(int level) {
        super();
        startGame();
        GameManager.level = level;
    }

    @Override
    public void initializeContainer() {
        setBackground(Color.BLACK);
        setLayout(null);
        setFocusable(true);
    }

    @Override
    public void initializeComponents() {
        isGamePause = true;
        gameManager = new GameManager();
        font = new Font("Arial", Font.BOLD, 24);

        btnBack = new JButton("Back");
        btnSave = new JButton("Save");
        lbLevel = new JLabel("Level: " + GameManager.level);
        lbLive = new JLabel("Lives: " + PlayerTank.lives);
        lbScore = new JLabel("Score: " + GameManager.score);

        lbLevel.setBounds(Gui.WIDTH_FRAME - 150 - 50, 100, 150, 50);
        lbLive.setBounds(Gui.WIDTH_FRAME - 150 - 50, lbLevel.getY() + 20 + 50, 150, 50);
        lbScore.setBounds(Gui.WIDTH_FRAME - 150 - 50, lbLive.getY() + 20 + 50, 150, 50);
        btnSave.setBounds(Gui.WIDTH_FRAME - 150 - 50, lbScore.getY() + 20 + 50, 100, 50);
        btnBack.setBounds(lbLevel.getX(), btnSave.getY() + 50 + 20, 100, 50);
        lbLevel.setFont(font);
        lbLive.setFont(font);
        lbScore.setFont(font);

        lbLevel.setBackground(Color.BLACK);
        lbLevel.setOpaque(true);
        lbLive.setBackground(Color.BLACK);
        lbLive.setOpaque(true);
        lbScore.setBackground(Color.BLACK);
        lbScore.setOpaque(true);

        add(btnBack);
        add(btnSave);
        add(lbLevel);
        add(lbLive);
        add(lbScore);
        bitSet = new BitSet();
        requestFocus();
    }

    @Override
    public void registerListeners() {
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGamePause = false;
                onBackButtonClickListener.onBackButtonClicked();
                Gui.wavPlayers[1].loop(1);
                Gui.wavPlayers[1].playSound();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                isGamePause = true;
                bitSet.set(e.getKeyCode(), true);

                Gui.wavPlayers[0].stopSound();

            }

            @Override
            public void keyReleased(KeyEvent e) {
                bitSet.set(e.getKeyCode(), false);
                Gui.wavPlayers[3].stopSound();
                Gui.wavPlayers[4].stopSound();

            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.saveGame();
                Gui.wavPlayers[1].loop(1);
                Gui.wavPlayers[1].playSound();
            }
        });
    }

    public void startGame() {
        Thread thread = new Thread(this);
        isGamePlaying = true;
        thread.start();
    }

    @Override
    public void run() {
        long numberOfSleep = 0;
            while (isGamePlaying) {
                if (GameManager.isPlayerTankDie == true) {
                    isGamePlaying = false;
                    int a = JOptionPane.showConfirmDialog(null, "You lose", "Thông báo", JOptionPane.CLOSED_OPTION);
                    if (a == JOptionPane.OK_OPTION) {
                        onDieBackMenu.backMenuOnDie();
                    }
                    GameManager.isPlayerTankDie=false;
                    PlayerTank.lives=2;
                }
                if (GameManager.isPlayerTankWin == true) {
                    isGamePlaying = false;
                    int a = JOptionPane.showConfirmDialog(null, "You Win\nYourScore="+GameManager.score, "Thông báo", JOptionPane.CLOSED_OPTION);
                    if (a == JOptionPane.OK_OPTION) {
                        onDieBackMenu.backMenuOnDie();
                    }
                    GameManager.isPlayerTankDie=false;
                    PlayerTank.lives=2;
                }
                if (isGamePause) {
                    if (bitSet.get(KeyEvent.VK_UP)) {
                        gameManager.changePlayerTankOrientation(PlayerTank.UP);
                        gameManager.movePlayerTank(numberOfSleep);
                        Gui.wavPlayers[3].loop(Clip.LOOP_CONTINUOUSLY);
                        Gui.wavPlayers[3].playSound();
                    } else if (bitSet.get(KeyEvent.VK_LEFT)) {
                        gameManager.changePlayerTankOrientation(PlayerTank.LEFT);
                        gameManager.movePlayerTank(numberOfSleep);
                        Gui.wavPlayers[3].loop(Clip.LOOP_CONTINUOUSLY);
                        Gui.wavPlayers[3].playSound();
                    } else if (bitSet.get(KeyEvent.VK_RIGHT)) {
                        gameManager.changePlayerTankOrientation(PlayerTank.RIGHT);
                        gameManager.movePlayerTank(numberOfSleep);
                        Gui.wavPlayers[3].loop(Clip.LOOP_CONTINUOUSLY);
                        Gui.wavPlayers[3].playSound();
                    } else if (bitSet.get(KeyEvent.VK_DOWN)) {
                        gameManager.changePlayerTankOrientation(PlayerTank.DOWN);
                        gameManager.movePlayerTank(numberOfSleep);
                        Gui.wavPlayers[3].loop(Clip.LOOP_CONTINUOUSLY);
                        Gui.wavPlayers[3].playSound();
                    }
                    if (bitSet.get(KeyEvent.VK_SPACE)) {
                        gameManager.fireForPlayerTank();

                    }

                    gameManager.moveBullets(numberOfSleep);
                    numberOfSleep++;
                    if (numberOfSleep == Long.MAX_VALUE) {
                        numberOfSleep = 0;
                    }
                    gameManager.diePlayerTank();
                    gameManager.moveListAITank(numberOfSleep);
                    gameManager.autoFireAITank();
                    gameManager.countDownBulletPlayerTank();
                    gameManager.countDownBulletAITank();
                    gameManager.dieAITank();

                }

                repaint();
                try {

                    Thread.sleep(4);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

            gameManager.drawBlockItem(graphics2D);
            gameManager.drawBullets(graphics2D);
            gameManager.drawPlayerTank(graphics2D);
            gameManager.drawListAITank(graphics2D);
            gameManager.drawTrees(graphics2D);
            gameManager.drawBirdItem(graphics2D);

    }
    public interface OnBackButtonClickListener {
        void onBackButtonClicked();
    }

    public interface OnDieBackMenu {
        void backMenuOnDie();
    }

    public interface OnWinBackMenu {
        void backMemuOnWin();
    }
}
