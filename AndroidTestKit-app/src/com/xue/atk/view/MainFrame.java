package com.xue.atk.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.EmptyBorder;

import com.xue.atk.replay.ReplayView;
import com.xue.atk.res.ATK;
import com.xue.atk.util.Util;

public class MainFrame extends JFrame {

    // public JPanel contentPane;

    public JLayeredPane layeredPane;

    /**
     * Create the frame.
     */
    public MainFrame() {
        layeredPane = new JLayeredPane();

        setUndecorated(true);

        // contentPane = new JPanel();
        layeredPane.setBackground(Color.WHITE);
        layeredPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        layeredPane.setLayout(null);

        setLayeredPane(layeredPane);

        TitleBar titleBar = new TitleBar(this);
        titleBar.setBounds(0, 0, ATK.TITLE_BAR_WIDTH, ATK.TITLE_BAR_HEIGHT);
        titleBar.setBackground(Color.WHITE);
        layeredPane.add(titleBar, JLayeredPane.DEFAULT_LAYER);

        MenuBar menuBar = new MenuBar(this);
        menuBar.setBounds(0, ATK.TITLE_BAR_HEIGHT, ATK.TOOL_BAR_WIDTH, ATK.TOOL_BAR_HEIGHT);
        menuBar.setBackground(Color.WHITE);
        layeredPane.add(menuBar, JLayeredPane.DEFAULT_LAYER);

        CButton info = new CButton(Util.getImageIcon("about_up_24.png"),
                Util.getImageIcon("about_down_24.png"));
        SoftwareInfoView infoView = new SoftwareInfoView();
        MenuItem infoItem = new MenuItem(this, info, infoView);
        menuBar.addMenuItem(infoItem);

        CButton settings = new CButton(Util.getImageIcon("settings_up_25.png"),
                Util.getImageIcon("settings_down_25.png"));
        MenuItemView settingsView = new MenuItemView();
        MenuItem settingsItem = new MenuItem(this, settings, settingsView);
        menuBar.addMenuItem(settingsItem);

        TabHost tabHost = new TabHost(this);
        tabHost.setBounds(ATK.TAB_HOST_LOCATION_X, ATK.TAB_HOST_LOCATION_Y, ATK.TAB_HOST_WIDTH,
                ATK.TAB_HOST_HEIGHT);
        layeredPane.add(tabHost, JLayeredPane.DEFAULT_LAYER);

        ReplayView recordView = new ReplayView("Replay");
        recordView.setBackground(Color.GRAY);
   
        ReplayView replayView2 = new ReplayView("More");
        replayView2.setBackground(Color.BLUE);
        tabHost.addTab(recordView);
  
        tabHost.addTab(replayView2);

        BottomBar bottomBar = new BottomBar();
        bottomBar.setBounds(ATK.BOTTOM_BAR_X, ATK.BOTTOM_BAR_Y, ATK.BOTTOM_BAR_WIDTH,
                ATK.BOTTOM_BAR_HEIGHT);
        bottomBar.setBackground(Color.WHITE);
        layeredPane.add(bottomBar, JLayeredPane.DEFAULT_LAYER);

    }
    

    public void setLocation(Point p) {
        super.setLocation(p);

    }

}