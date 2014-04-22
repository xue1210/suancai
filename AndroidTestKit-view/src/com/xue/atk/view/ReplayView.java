package com.xue.atk.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.xue.atk.file.FileScanner;
import com.xue.atk.util.Util;

public class ReplayView extends CBaseTabView implements MouseListener,ItemListener {

	private static final int DIVIDE_LINE_WIDTH = 70;
	private static final int DIVIDE_LINE_HEIGHT = 1;

	private static final int PROGRESSBAR_SIDE_LENGHT = 25;

	private CButton mRecordBtn;
	private CButton mReplayBtn;

	private ImageIcon mRecordIconUp;
	private ImageIcon mRecordIconDown;
	private ImageIcon mReplayIconUp;
	private ImageIcon mReplayIconDown;
	private ImageIcon mStopIconUp;
	private ImageIcon mStopIconDown;

	private JLabel mDivideLineLabel;
	private ProgressBar mProgress;
	   
    private JComboBox mProjectComboBox;
    
	private ListSource mListSource;
	private BaseList mTransferList;
	private FileScanner mFileScanner;

	private boolean isRunning;

	public ReplayView(String tabName) {
		super(tabName);
		initView();
	}

	public void initView() {

		mRecordIconUp = Util.getImageIcon("record_btn.png");
		mRecordIconDown = Util.getImageIcon("record_btn_down.png");
		mReplayIconUp = Util.getImageIcon("replay_btn.png");
		mReplayIconDown = Util.getImageIcon("replay_btn_down.png");
		mStopIconUp = Util.getImageIcon("stop_up.png");
		mStopIconDown = Util.getImageIcon("stop_down.png");

		initLeftView();
		initCenterView();
	}
	
	private void initLeftView(){
		mLeftPanel.setLayout(null);
		JLabel label = new JLabel("Project:");
		label.setBounds(0, 0, 80, 25);
		
		mLeftPanel.add(label);
		
		mProjectComboBox = new JComboBox();
		mProjectComboBox.setBounds(label.getWidth(), 0, mLeftPanel.getWidth()-label.getWidth(), 25);
		mProjectComboBox.setMaximumRowCount(20);
		mProjectComboBox.addItemListener(this);

		mProjectComboBox.setUI(new BasicComboBoxUI() {
            public void installUI(JComponent comboBox) {
                super.installUI(comboBox);
                listBox.setForeground(Color.GRAY);
                listBox.setBackground(Color.WHITE);
                listBox.setSelectionBackground(Color.WHITE);
                listBox.setSelectionForeground(Color.BLACK);
            }
             
            protected JButton createArrowButton() {
            	JButton button = new BasicArrowButton(BasicArrowButton.SOUTH,
            			Color.WHITE,
            			Color.WHITE,
    				    UIManager.getColor("ComboBox.buttonDarkShadow"),
    				    Color.WHITE){
            		public void paint(Graphics g){
            			super.paint(g);
            			int h = getSize().width;
            			int w = getSize().height;
            			g.setColor(Color.WHITE);
            			g.drawLine(0, h-1, w-1, h-1);
                        g.drawLine(w-1, h-1, w-1, 0);
            		}
            	};
            button.setName("ComboBox.arrowButton");
                return button;
            }
        });

		mLeftPanel.add(mProjectComboBox);
		
		mFileScanner = new FileScanner();

		mProjectComboBox.setModel(new DefaultComboBoxModel(mFileScanner.getProjectList()));
		mProjectComboBox.setSelectedItem(0);
		
		List source = Arrays.asList(mFileScanner.getEventList(mProjectComboBox.getSelectedItem().toString()));
	
		mListSource = new ListSource();
		mListSource.setSources(source);
		
		
		mTransferList = new BaseList();
		mTransferList.setBounds(0, 0, mLeftPanel.getWidth(), mLeftPanel.getHeight()-label.getHeight());
		
		
		mTransferList.setCellIface(new CellPanel());
		mTransferList.setSource(mListSource);
		
		JScrollPane scrollpane = new JScrollPane(mTransferList);
        scrollpane.setBounds(0, mProjectComboBox.getHeight(), mLeftPanel.getWidth(),
                mLeftPanel.getHeight() - mProjectComboBox.getHeight());
        scrollpane.setOpaque(false);
        scrollpane.getViewport().setOpaque(false);
        scrollpane.setBorder(null);
        mLeftPanel.add(scrollpane);
		
	}

	private void initCenterView() {
		mCenterPanel.setLayout(null);

		JLayeredPane pane = new JLayeredPane();
		pane.setLayout(null);
		pane.setBounds(0, 0, mCenterPanel.getWidth(), mCenterPanel.getHeight());

		mCenterPanel.add(pane);

		mDivideLineLabel = new JLabel();
		ImageIcon divideIcon = Util.scaleImage(Util.getImageIcon("tabbar_select.png"),
				mCenterPanel.getWidth(), DIVIDE_LINE_HEIGHT);
		mDivideLineLabel.setIcon(divideIcon);
		mDivideLineLabel.setBounds((pane.getWidth() - divideIcon.getIconWidth()) / 2,
				(pane.getHeight() - divideIcon.getIconHeight()) / 2, divideIcon.getIconWidth(),
				divideIcon.getIconHeight());

		//System.out.println((pane.getHeight() - divideIcon.getIconHeight()) / 2);
		mRecordBtn = new CButton(mRecordIconUp, mRecordIconDown);
		mRecordBtn.setBounds(mDivideLineLabel.getX(),
				mDivideLineLabel.getY() - mRecordIconUp.getIconHeight() - 5,
				mRecordIconUp.getIconWidth(), mRecordIconUp.getIconHeight());
		mRecordBtn.addMouseListener(this);

		mReplayBtn = new CButton(mReplayIconUp, mReplayIconDown);
		mReplayBtn.setBounds(mDivideLineLabel.getX(), mDivideLineLabel.getY() + 5,
				mReplayIconUp.getIconWidth(), mReplayIconUp.getIconHeight());
		mReplayBtn.addMouseListener(this);

		pane.add(mDivideLineLabel, JLayeredPane.DEFAULT_LAYER);

		pane.add(mRecordBtn, JLayeredPane.PALETTE_LAYER);
		pane.add(mReplayBtn, JLayeredPane.PALETTE_LAYER);

		/* load images */
		ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
		for (int i = 1; i < 13; i++) {
			ImageIcon icon = Util.scaleImage(Util.getImageIcon("progress_bars" + i + ".png"),
					PROGRESSBAR_SIDE_LENGHT, PROGRESSBAR_SIDE_LENGHT);
			icons.add(icon);
		}

		mProgress = new ProgressBar();
		mProgress.setProgressImages(icons);

		pane.add(mProgress);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!focusable){
			return;
		}
		if (e.getSource() == mRecordBtn && mRecordBtn.getEnabled()) {
			mRecordBtn.pressDown();

		}
		if (e.getSource() == mReplayBtn && mReplayBtn.getEnabled()) {
			mReplayBtn.pressDown();

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!focusable){
			return;
		}
		if (e.getSource() == mRecordBtn && mRecordBtn.getEnabled()) {
			if (isRunning) {

				mRecordBtn.setIconUp(mRecordIconUp);
				mRecordBtn.setIconDown(mRecordIconDown);

				mProgress.stop();
				isRunning = false;

				mReplayBtn.setEnabled(true);

			} else {

				mRecordBtn.setIconUp(mStopIconUp);
				mRecordBtn.setIconDown(mStopIconDown);

				mProgress.setLocation(mDivideLineLabel.getX() + mDivideLineLabel.getWidth()
						- PROGRESSBAR_SIDE_LENGHT, mDivideLineLabel.getY()
						- PROGRESSBAR_SIDE_LENGHT);
				mProgress.start();

				mReplayBtn.setEnabled(false);
				isRunning = true;
			}

			mRecordBtn.pressUp();

			return;
		}
		if (e.getSource() == mReplayBtn && mReplayBtn.getEnabled()) {

			if (isRunning) {

				mReplayBtn.setIconUp(mReplayIconUp);
				mReplayBtn.setIconDown(mReplayIconDown);

				mProgress.stop();
				isRunning = false;

				mRecordBtn.setEnabled(true);
			} else {
				mReplayBtn.setIconUp(mStopIconUp);
				mReplayBtn.setIconDown(mStopIconDown);

				mProgress.setLocation(mDivideLineLabel.getX() + mDivideLineLabel.getWidth()
						- PROGRESSBAR_SIDE_LENGHT, mDivideLineLabel.getY());
				mProgress.start();

				mRecordBtn.setEnabled(false);
				isRunning = true;
			}

			mReplayBtn.pressUp();

			return;

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        if (e.getStateChange() == ItemEvent.SELECTED){
            System.out.println("xxxx:"+e.getItem().toString());
        }
    }

}
