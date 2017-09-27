package com.company.views;


import com.company.maganer.GameManager;
import com.company.model.MapItem;
import com.company.model.WavPlayer;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Gui extends JFrame implements Setup,
        MenuPanel.OnPlayGameButtonClickListener,
        GamePanel.OnBackButtonClickListener,
        MenuPanel.OnContinueGameButtonClickListener,
        MenuPanel.OnLoadGameButtonClickListener,
        GamePanel.OnDieBackMenu,
        GamePanel.OnWinBackMenu {
    public static final int WIDTH_FRAME = 28 * MapItem.SIZE + 250;
    public static final int HEIGHT_FRAME = 28 * MapItem.SIZE;
    public static WavPlayer[] wavPlayers;
    private WindowAdapter windowAdapter;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;

    public Gui() {
        initializeContainer();
        initializeComponents();
        registerListeners();
    }

    @Override
    public void initializeContainer() {
        setTitle("Tank90");
        setLayout(new CardLayout());

        getContentPane().setPreferredSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
        setResizable(false);
        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    @Override
    public void initializeComponents() {
        menuPanel = new MenuPanel();
        wavPlayers = new WavPlayer[5];
        wavPlayers[0] = new WavPlayer("entergame");
        wavPlayers[1] = new WavPlayer("explosion");
        wavPlayers[2] = new WavPlayer("explosiontank");
        wavPlayers[3] = new WavPlayer("move");
        wavPlayers[4] = new WavPlayer("shoot");
        menuPanel.setOnPlayGameButtonClickListener(this);
        menuPanel.setOnContinueGameButtonClickListener(this);
        menuPanel.setOnLoadGameButtonClickListener(this);
        add(menuPanel);
    }


    @Override
    public void registerListeners() {
        windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Do you want exit?",
                        "Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_NO_OPTION) {
                    setVisible(false);
                    dispose();
                    System.exit(0);
                }
            }
        };
        addWindowListener(windowAdapter);
    }

    @Override
    public void onBackButtonClicked() {
        MenuPanel.btnContinueGame.setEnabled(true);
        remove(gamePanel);
        if (menuPanel == null) {
            menuPanel = new MenuPanel();
        }
        add(menuPanel);
        validate();
        wavPlayers[0].stopSound();
    }

    @Override
    public void backMenuOnDie() {
        MenuPanel.btnContinueGame.setEnabled(false);
        remove(gamePanel);
        gamePanel=null;
        if (menuPanel == null) {
            menuPanel = new MenuPanel();
        }
        add(menuPanel);
        validate();
    }

    @Override
    public void backMemuOnWin() {
        /*MenuPanel.btnContinueGame.setEnabled(false);
        remove(gamePanel);
        if (menuPanel == null) {
            menuPanel = new MenuPanel();
        }
        add(menuPanel);
        validate();*/
    }

    @Override
    public void onPlayGameButtonClicked() {
        GameManager.level = 1;
        remove(menuPanel);
        gamePanel = new GamePanel();
        add(gamePanel);
        validate();
        gamePanel.requestFocus();
        gamePanel.setOnBackButtonClickListener(this);
        gamePanel.setOnDieBackMenu(this);
        wavPlayers[0].loop(0);
        wavPlayers[0].playSound();
    }

    @Override
    public void onContinueButtonClicked() {
        remove(menuPanel);
        add(gamePanel);
        validate();
        gamePanel.requestFocus();
        gamePanel.setOnBackButtonClickListener(this);
    }

    @Override
    public void onLoadGameButtonClicked(int level, int locationXPlayerTank, int locationYPlayerTank) {
        remove(menuPanel);
        gamePanel = new GamePanel(level);
        GamePanel.lbLevel.setText("Level: " + GameManager.level);
        add(gamePanel);
        validate();
        gamePanel.requestFocus();
        gamePanel.setOnBackButtonClickListener(this);
        gamePanel.setOnDieBackMenu(this);
        wavPlayers[0].loop(1);
        wavPlayers[0].playSound();
    }


}
