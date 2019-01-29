package org.scheduler.view;

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

    private JButton binit = new JButton("Iniciar simulação");
    private JButton binformation = new JButton("Informações");
    private JButton bexit = new JButton("Sair do programa");

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

        binit.setBounds(225, 190, 150, 30);
        panel.add(binit);

        binformation.setBounds(225, 235, 150, 30);
        panel.add(binformation);

        bexit.setBounds(225, 275, 150, 30);
        panel.add(bexit);

        binit.addActionListener(this);
        binformation.addActionListener(this);
        bexit.addActionListener(this);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent  e) {

        if (e.getSource().equals(binit)) {
            frame.dispose();
            new MainView();
        } else if (e.getSource().equals(bexit)) {
            System.exit(0);
        } else if (e.getSource().equals(binformation)) {
            JOptionPane.showMessageDialog(frame, "<html>Autores do programa: <b>Rafael Campos Nunes</b> e <b>Mikael Pereira Messias</b>.<br>" +
                            "Disponível em <a href=''>https://github.com/rafaelcn/jscheduler</a>.</html>",
                    "Informações do programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}