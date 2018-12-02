package org.gui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.WindowConstants;

public class Greetings implements ActionListener, Runnable {

    private JFrame frame;
    private JPanel panel;

    private JLabel title = new JLabel("JScheduler");
    private JLabel information = new JLabel("A Java Scheduler");
    private JLabel copyleft = new JLabel("UTFPR");

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

        title.setBounds(250, 50, 200, 30);
        panel.add(title);

        information.setBounds(250, 120, 200, 30);
        panel.add(information);

        copyleft.setBounds(400, 380, 100, 10);
        panel.add(copyleft);

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