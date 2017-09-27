package com.company.views;

import javax.swing.*;

public abstract class BasePanel extends JPanel implements Setup{
    public BasePanel() {
        initializeContainer();
        initializeComponents();
        registerListeners();
    }
}
