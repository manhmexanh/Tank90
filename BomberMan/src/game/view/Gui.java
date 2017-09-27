package game.view;

import game.manager.GameManager;
import game.model.Bomb;
import game.model.WavPlayer;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame implements
        MenuPanel.OnPlayGameButtonClickListener,
        GamePlayPanel.OnBackButtonClickListener,
        MenuPanel.OnContinueGameButtonClickListener,
        MenuPanel.OnLoadGameButtonClickListener,
        GamePlayPanel.OnDieBackMenu
        {

    public static final int WIDTH_FRAME = 28 * 25 + 6 + 200;
    public static final int HEIGHT_FRAME = 29 * 25 + 30;

    private MenuPanel menuPanel;
    private GamePlayPanel gamePlayPanel;
    public static WavPlayer[] wavPlayer;

    public Gui() {
        initGui();
        initComponent();
    }

    private void initGui() {
        setTitle("");
        setLayout(new CardLayout());
        setSize(WIDTH_FRAME, HEIGHT_FRAME);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initComponent() {
        menuPanel = new MenuPanel();
        menuPanel.setOnPlayGameButtonClickListener(this);
        menuPanel.setOnContinueGameButtonClickListener(this);
        menuPanel.setOnLoadGameButtonClickListener(this);

        wavPlayer = new WavPlayer[8];
        wavPlayer[0] = new WavPlayer("menu");
        wavPlayer[1] = new WavPlayer("playgame");
        wavPlayer[2] = new WavPlayer("lose");
        wavPlayer[3] = new WavPlayer("win");
        wavPlayer[4] = new WavPlayer("bomb_bang");
        wavPlayer[5] = new WavPlayer("item");
        wavPlayer[6] = new WavPlayer("monster_die");
        wavPlayer[7] = new WavPlayer("newbomb");
        add(menuPanel);
        wavPlayer[0].loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void onPlayGameButtonClicked() {
        GameManager.slBombs = 1;
        GameManager.level = 1;
        Bomb.power = 1;
        remove(menuPanel);
        gamePlayPanel = new GamePlayPanel();
        add(gamePlayPanel);
        validate();
        gamePlayPanel.requestFocus();
        gamePlayPanel.setOnBackButtonClickListener(this);
        gamePlayPanel.setOnDieBackMenu(this);
        wavPlayer[0].stopSound();
        wavPlayer[1].loop(Clip.LOOP_CONTINUOUSLY);
        wavPlayer[1].playSound();
    }

    @Override
    public void onBackButtonClicked() {
        MenuPanel.btnContinueGame.setEnabled(true);
        remove(gamePlayPanel);
        if (menuPanel == null) {
            menuPanel = new MenuPanel();
        }
        add(menuPanel);
        validate();
        wavPlayer[1].stopSound();
        wavPlayer[0].loop(Clip.LOOP_CONTINUOUSLY);
        wavPlayer[0].playSound();
    }

    @Override
    public void onContinueButtonClicked() {
        remove(menuPanel);
        add(gamePlayPanel);
        validate();
        gamePlayPanel.requestFocus();
        gamePlayPanel.setOnBackButtonClickListener(this);
        wavPlayer[0].stopSound();
        wavPlayer[1].loop(Clip.LOOP_CONTINUOUSLY);
        wavPlayer[1].playSound();
    }

    @Override
    public void onLoadGameButtonClicked(int level, int slBombs,int power) {
        remove(menuPanel);
        gamePlayPanel = new GamePlayPanel(level,slBombs,power);
        GamePlayPanel.lbLevel.setText("Level: "+GameManager.level);
        GamePlayPanel.lbPower.setText("Power: "+Bomb.power);
        GamePlayPanel.lbSlbomb.setText("MaxBomb: "+GameManager.slBombs);
        add(gamePlayPanel);
        validate();
        gamePlayPanel.requestFocus();
        gamePlayPanel.setOnBackButtonClickListener(this);
        gamePlayPanel.setOnDieBackMenu(this);
        wavPlayer[0].stopSound();
        wavPlayer[1].loop(Clip.LOOP_CONTINUOUSLY);
        wavPlayer[1].playSound();
    }

    @Override
    public void backMenuOnDie() {
        MenuPanel.btnContinueGame.setEnabled(false);
        remove(gamePlayPanel);
        if (menuPanel == null) {
            menuPanel = new MenuPanel();
        }
        add(menuPanel);
        validate();
        wavPlayer[0].loop(Clip.LOOP_CONTINUOUSLY);
        wavPlayer[0].playSound();
    }


}

