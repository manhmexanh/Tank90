package game.view;

import javax.swing.*;

public abstract class BaseContainer extends JPanel {


    public BaseContainer() {
        initPanel();
        initComponent();
        initListener();
    }

    protected abstract void initPanel();

    protected abstract void initComponent();

    protected abstract void initListener();
}
