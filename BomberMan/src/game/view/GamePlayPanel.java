package game.view;

import game.manager.GameManager;
import game.model.Bomb;
import game.model.Bomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import static game.view.Gui.wavPlayer;

public class GamePlayPanel extends BaseContainer implements Runnable {

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnDieBackMenu onDieBackMenu;


    public void setOnDieBackMenu(OnDieBackMenu onDieBackMenu) {
        this.onDieBackMenu = onDieBackMenu;
    }
    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    private JButton btnBack;
    private JButton btnSave;

    private Font font;

    public static boolean isGamePlaying;
    public static boolean isGamePause;
    public static JLabel lbPower;
    public static JLabel lbSlbomb;
    public static JLabel lbLevel;
    private BitSet bitSet;

    GameManager gameManager;


    public GamePlayPanel() {
        super();
        startGame();
    }

    public GamePlayPanel(int level,int slBombs,int power){
        super();
        startGame();
        Bomb.power =power;
        GameManager.slBombs =slBombs;
        GameManager.level =level;
    }

    @Override
    protected void initPanel() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
    }

    @Override
    protected void initComponent() {
        isGamePause = true;
        gameManager = new GameManager();

        font = new Font("Arial",Font.BOLD,24);

        btnBack = new JButton("BACK");
        btnSave = new JButton("Save");
        lbLevel = new JLabel("Level: "+GameManager.level);
        lbPower = new JLabel("Power: "+Bomb.power);
        lbSlbomb = new JLabel("MaxBomb: "+GameManager.slBombs);

        lbLevel.setBounds(0,12,150,50);
        lbPower.setBounds(160,12,150,50);
        lbSlbomb.setBounds(320,12,200,50);
        btnBack.setBounds(750, 12, 100, 50);
        btnSave.setBounds(600, 12, 100, 50);

        lbLevel.setFont(font);
        lbPower.setFont(font);
        lbSlbomb.setFont(font);


        lbLevel.setOpaque(true);

        lbPower.setOpaque(true);

        lbSlbomb.setOpaque(true);

        add(btnBack);
        add(btnSave);

        add(lbLevel);
        add(lbPower);
        add(lbSlbomb);

        bitSet = new BitSet(256);
    }

    @Override
    protected void initListener() {
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGamePause = false;
                onBackButtonClickListener.onBackButtonClicked();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                isGamePause = true;
                bitSet.set(e.getKeyCode(), true);
                if (bitSet.get(KeyEvent.VK_ESCAPE)){
                    isGamePause = !isGamePause;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                bitSet.set(e.getKeyCode(), false);
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.saveGame();

                requestFocus();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        gameManager.drawMapBrick(g2D);
        gameManager.drawMapRock(g2D);
        gameManager.drawMapDoor(g2D);
        gameManager.drawBombs(g2D);
        gameManager.drawBomber(g2D);
        gameManager.drawAi(g2D);
        gameManager.drawItem(g2D);
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
            if (gameManager.isBomberDie()){
                isGamePlaying = false;
                int a = JOptionPane.showConfirmDialog(null,"You lose","Thông báo",JOptionPane.CLOSED_OPTION);
                if (a==JOptionPane.OK_OPTION){
                    wavPlayer[2].stopSound();
                    onDieBackMenu.backMenuOnDie();
                }
                return;
            }
            if (isGamePause){
                if (bitSet.get(KeyEvent.VK_SPACE)) {
                    gameManager.plantingBomb(numberOfSleep);
                }
                if (bitSet.get(KeyEvent.VK_LEFT)) {
                    gameManager.changeBomberOrientation(Bomber.LEFT);
                    gameManager.moveBomber(numberOfSleep);
                }
                if (bitSet.get(KeyEvent.VK_RIGHT)) {
                    gameManager.changeBomberOrientation(Bomber.RIGHT);
                    gameManager.moveBomber(numberOfSleep);
                }
                if (bitSet.get(KeyEvent.VK_UP)) {
                    gameManager.changeBomberOrientation(Bomber.UP);
                    gameManager.moveBomber(numberOfSleep);
                }
                if (bitSet.get(KeyEvent.VK_DOWN)) {
                    gameManager.changeBomberOrientation(Bomber.DOWN);
                    gameManager.moveBomber(numberOfSleep);
                }
                gameManager.changeBombsBang(numberOfSleep);
                gameManager.moveAi(numberOfSleep);
                numberOfSleep++;
            }
            repaint();
            if (numberOfSleep == Long.MAX_VALUE) {
                numberOfSleep = 0;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnBackButtonClickListener {
        void onBackButtonClicked();
    }

    public interface OnDieBackMenu{
        void backMenuOnDie();
    }
}
