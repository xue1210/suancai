package com.xue.atk.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.xue.atk.util.Util;

public class AlertDialog extends Dialog implements ActionListener {

    private static final int ICON_WIDTH = 50;
    private static final int ICON_HEIGHT = 50;

    private static final int BTN_WIDTH = 95;
    private static final int BTN_HEIGHT = 40;

    private static final int MSG_WIDTH = 30;
    private static final int MSG_HEIGHT = 35;

    public static final int EXIT_DIALOG = 1;
    public static final int MSG_DIALOG = 2;

    private int mFlag;
    private String[] message;

    protected JLabel mIconLable;
    protected JButton mPositiveBtn;
    protected JButton mNegativeBtn;
    private JLabel mMsgLabel;

    public AlertDialog(Component owner) {
        super(owner);

    }

    public AlertDialog(Component owner, int width, int height, int flag) {
        super(owner, width, height);
        // TODO Auto-generated constructor stub
        setFlag(flag);
    }

    public void setFlag(int flag) {
        this.mFlag = flag;
    }

    public void onCreate() {
        mIconLable = new JLabel();
        mIconLable.setBounds((getDialogWidth() - ICON_WIDTH) / 2, ICON_WIDTH / 2, ICON_WIDTH,
                ICON_HEIGHT);

        if (message.length == 1) {
            mMsgLabel = new JLabel();
            mMsgLabel.setBounds(20, getDialogHeight() / 2 - 20, getDialogWidth() - 40, MSG_HEIGHT);

            StringBuilder builder = new StringBuilder("<html>");
            char[] chars = message[0].toCharArray();

            FontMetrics fontMetrics = mMsgLabel.getFontMetrics(mMsgLabel.getFont());
            for (int beginIndex = 0, limit = 1;; limit++) {

                if (fontMetrics.charsWidth(chars, beginIndex, limit) < mMsgLabel.getWidth()) {
                    if (beginIndex + limit < chars.length) {
                        continue;
                    }
                    builder.append(chars, beginIndex, limit);
                    break;
                }
                builder.append(chars, beginIndex, limit - 1).append("<br/>");
                beginIndex = limit - 1;
                limit = 0;
            }

            builder.append("</html>");

            mMsgLabel.setText(builder.toString());

            getLayeredPane().add(mMsgLabel, new Integer(300));
        }else{
            
            int y = getDialogHeight() / 2 - 45;
            
            for (int i = 0 ;i< message.length ;i++){
                JLabel label = new JLabel(message[i]);
                label.setBounds(20, y, getDialogWidth() - 40, MSG_HEIGHT);
                getLayeredPane().add(label, new Integer(300));
                y+= 30;
            }
            
        }

        switch (mFlag) {
        case EXIT_DIALOG:
            mIconLable.setIcon(Util.getImageIcon("warn.png"));

            mPositiveBtn = new JButton();
            mPositiveBtn.setIcon(Util.getImageIcon("ok_up.png"));

            mPositiveBtn.setPressedIcon(Util.getImageIcon("ok_down.png"));
            mPositiveBtn.setBounds(getDialogWidth() / 2 + 20, getDialogHeight() - BTN_HEIGHT - 20,
                    BTN_WIDTH, BTN_HEIGHT);
            mPositiveBtn.setOpaque(false);
            mPositiveBtn.setContentAreaFilled(false);
            mPositiveBtn.setMargin(new Insets(0, 0, 0, 0));
            mPositiveBtn.setBorder(null);
            mPositiveBtn.setFocusPainted(false);
            mPositiveBtn.setBorderPainted(false);

            mPositiveBtn.addActionListener(this);

            mNegativeBtn = new JButton();
            mNegativeBtn.setIcon(Util.getImageIcon("cancel_up.png"));

            mNegativeBtn.setPressedIcon(Util.getImageIcon("cancel_down.png"));
            mNegativeBtn.setBounds(getDialogWidth() / 2 - BTN_WIDTH - 20, getDialogHeight()
                    - BTN_HEIGHT - 20, BTN_WIDTH, BTN_HEIGHT);
            mNegativeBtn.setOpaque(false);
            mNegativeBtn.setContentAreaFilled(false);
            mNegativeBtn.setMargin(new Insets(0, 0, 0, 0));
            mNegativeBtn.setBorder(null);
            mNegativeBtn.setFocusPainted(false);
            mNegativeBtn.setBorderPainted(false);
            mNegativeBtn.addActionListener(this);

            getLayeredPane().add(mPositiveBtn, new Integer(300));
            getLayeredPane().add(mNegativeBtn, new Integer(300));
            this.setAlwaysOnTop(true);
            mContext.setEnabled(false);

            break;

        case MSG_DIALOG:

            mIconLable.setIcon(Util.getImageIcon("warn.png"));

            mPositiveBtn = new JButton();
            mPositiveBtn.setIcon(Util.getImageIcon("ok_up.png"));

            mPositiveBtn.setPressedIcon(Util.getImageIcon("ok_down.png"));
            mPositiveBtn.setBounds((getDialogWidth() - BTN_WIDTH) / 2, getDialogHeight()
                    - BTN_HEIGHT - 20, BTN_WIDTH, BTN_HEIGHT);
            mPositiveBtn.setOpaque(false);
            mPositiveBtn.setContentAreaFilled(false);
            mPositiveBtn.setMargin(new Insets(0, 0, 0, 0));
            mPositiveBtn.setBorder(null);
            mPositiveBtn.setFocusPainted(false);
            mPositiveBtn.setBorderPainted(false);

            mPositiveBtn.addActionListener(this);

            getLayeredPane().add(mPositiveBtn, new Integer(300));
            this.setAlwaysOnTop(true);
            mContext.setEnabled(false);

            break;
        default:
            break;
        }

        getLayeredPane().add(mIconLable, new Integer(300));
    }

    public void setMessage(String[] message) {
        this.message = message;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource().equals(mPositiveBtn)) {

            switch (mFlag) {
            case EXIT_DIALOG:
                this.dispose();
                ((JFrame) mContext).dispose();
                System.exit(0);
                break;
            case MSG_DIALOG:
                mContext.setEnabled(true);
                this.dispose();
                break;
            default:
                break;

            }

            return;
        }
        if (e.getSource().equals(mNegativeBtn)) {
            mContext.setEnabled(true);
            this.dispose();

            return;
        }
    }

}
