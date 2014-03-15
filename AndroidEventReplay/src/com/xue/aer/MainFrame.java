package com.xue.aer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import com.xue.res.AER;
import com.xue.util.Util;

public class MainFrame extends JFrame {

	public JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setUndecorated(true);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		setContentPane(contentPane);
	
		
		TitleBar titleBar = new TitleBar(this);
		contentPane.add(titleBar);
		
		
		ToolBar toolBar = new ToolBar(0, titleBar.getHeight());
		contentPane.add(toolBar);
		
	}

}
