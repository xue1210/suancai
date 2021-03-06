package com.xue.atk.view;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MenuItem {

    private static final int WIDGET_WIDTH = 26;
    private static final int WIDGET_HEIGHT = 26;

    private JFrame mContext;
    private CButton mWidget;

    private boolean enable;

    private MenuItemView mMenuItemView;

    public MenuItem(JFrame context, CButton widget, MenuItemView menuItemView) {
        this.mContext = context;
        this.mMenuItemView = menuItemView;
        this.mWidget = widget;
    }

    public void showItemView() {
        mMenuItemView.showView();
    }

    public void hideItemView() {
        mMenuItemView.hideView();
    }

    public void setEnabled(boolean enable) {
        mWidget.setEnabled(enable);
    }

    public CButton getWidget() {
        return mWidget;
    }

    public void setWidget(CButton mWidget) {
        this.mWidget = mWidget;
    }

    public MenuItemView getMenuItemView() {
        return mMenuItemView;
    }

    public void setMenuItemView(MenuItemView mMenuItemView) {
        this.mMenuItemView = mMenuItemView;
    }

    public void addMouseListener(ActionListener l) {
        this.mWidget.addActionListener(l);
    }

}
