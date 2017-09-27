package com.company.views;

import com.company.maganer.GameManager;
import com.company.maganer.ImageStore;
import com.company.model.PlayerTank;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MenuPanel extends BasePanel {
    public static JButton btnContinueGame;
    public static JButton btnPlayGame;
    public static JButton btnExitGame;
    public static JButton btnLoadGame;
    public File file;
    private Font font;
    public JLabel jLabel;
    private int levelSave;
    private int locationXPlayerTank;
    private int locationYPlayerTank;
    private OnPlayGameButtonClickListener onPlayGameButtonClickListener;
    private OnContinueGameButtonClickListener onContinueGameButtonClickListener;
    private OnLoadGameButtonClickListener onLoadGameButtonClickListener;

    public void setOnPlayGameButtonClickListener(OnPlayGameButtonClickListener onPlayGameButtonClickListener) {
        this.onPlayGameButtonClickListener = onPlayGameButtonClickListener;
    }

    public void setOnContinueGameButtonClickListener(OnContinueGameButtonClickListener onContinueGameButtonClickListener) {
        this.onContinueGameButtonClickListener = onContinueGameButtonClickListener;
    }

    public void setOnLoadGameButtonClickListener(OnLoadGameButtonClickListener onLoadGameButtonClickListener) {
        this.onLoadGameButtonClickListener = onLoadGameButtonClickListener;
    }

    public MenuPanel() {
        super();
    }

    @Override
    public void initializeContainer() {
        setLayout(null);
        setBackground(Color.BLACK);

    }

    @Override
    public void initializeComponents() {
        font = new Font("Arial", Font.BOLD, 24);

        btnPlayGame = new JButton("GAME PLAY");
        btnContinueGame = new JButton("GAME CONTINUE");
        btnLoadGame = new JButton("LOAD GAME");
        btnExitGame = new JButton("EXIT GAME");

        btnContinueGame.setEnabled(false);
        btnPlayGame.setBounds((Gui.WIDTH_FRAME - 200) / 2, 350, 200, 50);
        btnContinueGame.setBounds((Gui.WIDTH_FRAME - 200) / 2, 425, 200, 50);
        btnExitGame.setBounds((Gui.WIDTH_FRAME - 200) / 2, 575, 200, 50);
        btnLoadGame.setBounds((Gui.WIDTH_FRAME - 200) / 2, 500, 200, 50);

        jLabel = new JLabel(new ImageIcon(ImageStore.IMAGE_PANEL));
        jLabel.setBounds(0, 0, 28 * 25 + 6 + 200, 300);

        add(jLabel);
        add(btnPlayGame);
        add(btnExitGame);
        add(btnContinueGame);
        add(btnLoadGame);
    }

    @Override
    public void registerListeners() {
        btnPlayGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onPlayGameButtonClickListener.onPlayGameButtonClicked();
                Gui.wavPlayers[1].loop(1);
                Gui.wavPlayers[1].playSound();
            }
        });

        btnContinueGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onContinueGameButtonClickListener.onContinueButtonClicked();
                Gui.wavPlayers[1].loop(1);
                Gui.wavPlayers[1].playSound();
            }
        });

        btnLoadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loadFileSave()) {
                    onLoadGameButtonClickListener.onLoadGameButtonClicked(levelSave,locationXPlayerTank,locationYPlayerTank);
                    Gui.wavPlayers[1].loop(1);
                    Gui.wavPlayers[1].playSound();
                }
            }
        });

        btnExitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.wavPlayers[1].loop(1);
                Gui.wavPlayers[1].playSound();
                System.exit(0);

            }
        });

    }

    public interface OnPlayGameButtonClickListener {
        void onPlayGameButtonClicked();
    }

    public interface OnContinueGameButtonClickListener {
        void onContinueButtonClicked();
    }

    public interface OnLoadGameButtonClickListener {
        void onLoadGameButtonClicked(int levelSave, int locationXPlayerTank, int locationYPlayerTank);
    }

    private boolean loadFileSave() {


        file = new File("/home/manhnt/Desktop/SaveTank");
        if (!file.exists()) {
            System.out.println("file ko tồn tại");
            return false;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
               String[] strings= line.split("-");
                levelSave = Integer.parseInt(strings[0]);
                GameManager.level=levelSave;
                locationXPlayerTank=Integer.parseInt(strings[1]);
                PlayerTank.locationX=locationXPlayerTank;
                locationYPlayerTank=Integer.parseInt(strings[2]);
                PlayerTank.locationY=locationYPlayerTank;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
