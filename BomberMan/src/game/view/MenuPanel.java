package game.view;

import game.manager.ImageStores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPanel extends BaseContainer {

    File file;

    public static JButton btnContinueGame;
    public static JButton btnPlayGame;
    public static JButton btnExitGame;
    public static JButton btnLoadGame;

    JLabel jLabel;
    private int levelSave;
    private int slBombsSave;
    private int powerSave;


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
    protected void initPanel() {
        setLayout(null);
        setBackground(Color.BLACK);
    }

    @Override
    protected void initComponent()  {

        btnPlayGame = new JButton("GAME PLAY");
        btnContinueGame = new JButton("GAME CONTINUE");
        btnLoadGame = new JButton("LOAD GAME");
        btnExitGame = new JButton("EXIT GAME");

        btnContinueGame.setEnabled(false);
        btnPlayGame.setBounds((Gui.WIDTH_FRAME - 200) / 2, 350, 200, 50);
        btnContinueGame.setBounds((Gui.WIDTH_FRAME - 200) / 2, 425, 200, 50);

        btnExitGame.setBounds((Gui.WIDTH_FRAME - 200) / 2, 650, 200, 50);
        btnLoadGame.setBounds((Gui.WIDTH_FRAME - 200) / 2, 500, 200, 50);


        jLabel = new JLabel(new ImageIcon(ImageStores.IMG_PANEL));
        jLabel.setBounds(0, 0, 28 * 25 + 6 + 200, 300);

        add(jLabel);
        add(btnPlayGame);
        add(btnExitGame);
        add(btnContinueGame);
        add(btnLoadGame);
    }

    @Override
    protected void initListener() {
        btnPlayGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onPlayGameButtonClickListener.onPlayGameButtonClicked();
            }
        });

        btnContinueGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onContinueGameButtonClickListener.onContinueButtonClicked();
            }
        });

        btnLoadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loadFileSave()) {
                    onLoadGameButtonClickListener.onLoadGameButtonClicked(levelSave, slBombsSave, powerSave);
                }
            }
        });

        btnExitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        void onLoadGameButtonClicked(int level, int slBombs, int power);
    }

    public interface OnManualButtonClickListener{
        void onManualButtonClicked();
    }

    private boolean loadFileSave() {

        file = new File("/home/manhnt/Desktop/savebomb");
        if (!file.exists()) {
            System.out.println("ko load dc file 3 - file ko tồn tại");

            return false;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                levelSave = (int) line.charAt(0)-48;
                slBombsSave = (int) line.charAt(2)-48;
                powerSave = (int) line.charAt(4)-48;

            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
