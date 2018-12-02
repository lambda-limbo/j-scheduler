package org.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.WindowConstants;

public class Greetings implements ActionListener, Runnable {

    private JFrame frame;
    private JPanel panel;

    private JLabel ltitle = new JLabel("JScheduler");
    private JLabel linformation = new JLabel("A Java Scheduler");
    private JLabel lcopyleft = new JLabel("UTFPR - SH");

    private JButton buttonInit = new JButton("Iniciar simulação");
    private JButton buttonExit = new JButton("Sair do programa");

    public void run() {}

    public Greetings() {
        frame = new JFrame();
        panel = new JPanel();

        frame.setTitle("JScheduler");
        frame.setResizable(false);
        frame.setSize(600, 400);
    
        panel.setLayout(null);

        ltitle.setFont(Fonts.newBold(20));
        ltitle.setBounds(240, 50, 200, 30);
        panel.add(ltitle);

        linformation.setBounds(250, 80, 200, 30);
        panel.add(linformation);

        lcopyleft.setFont(Fonts.newItalic(13));
        lcopyleft.setBounds(520, 340, 100, 30);
        panel.add(lcopyleft);

        buttonInit.setBounds(225, 190, 150, 30);
        panel.add(buttonInit);

        buttonExit.setBounds(225, 235, 150, 30);
        panel.add(buttonExit);

        buttonInit.addActionListener(this);
        buttonExit.addActionListener(this);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent  e) {

        if (e.getSource().equals(buttonInit)) {
            frame.dispose();
            new MainView();
        } else if (e.getSource().equals(buttonExit)) {
            System.exit(0);
        }
    }
}