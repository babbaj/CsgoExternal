package com.me.overlay;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ExternalOverlay extends JFrame implements ActionListener {

    public Timer timer;

    public ExternalOverlay() {
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        float[] res = this.getRes();
        this.setBounds(0, 0, (int)res[0], (int)res[1]);
        this.setAlwaysOnTop(true);
        this.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);
        this.getContentPane().setLayout(new java.awt.FlowLayout());
        this.setVisible(true);
        this.setAutoRequestFocus(false);
        this.timer = new Timer(1000/60, this);
        timer.start();
    }

    private float[] getRes() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        return new float[] {(float) width, (float) height};
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }


    public void draw(Graphics g) {

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        final String title = "gay external";
        g.setColor(new Color(255, 255, 255));
        Font font = new Font("Verdana", Font.BOLD, 12);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int hgt = metrics.getHeight();
        int adv = metrics.stringWidth(title);
        Dimension size = new Dimension(adv + 2, hgt + 2);
        float[] res = this.getRes();
        g.drawString(title, (int)(res[0] - size.getWidth()) - 5, 16);
        draw(g);
    }
}
