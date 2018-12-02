package org.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KillProcess implements ActionListener {

    // Components of the UI
    private JFrame frame;
    private JPanel panel;

    private JLabel lpid = new JLabel("PID: ");
    //
    private JTextField tfpid = new JTextField();
    //
    private JButton bconfirm = new JButton("Confirmar");

    public KillProcess() {
        frame = new JFrame();
        panel = new JPanel();

        frame.setTitle("Novo processo");
        frame.setResizable(false);
        frame.setSize(300, 200);

        lpid.setBounds(10, 10, 50, 30);
        panel.add(lpid);

        tfpid.setBounds(70, 10, frame.getWidth()-10, 40);
        panel.add(tfpid);

        bconfirm.setBounds(frame.getWidth()-150, 70, 130, 30);
        panel.add(bconfirm);


        bconfirm.addActionListener(this);

        frame.getContentPane().add(panel);
        frame.setVisible(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bconfirm) {
            // TODO: Search for PID on the scheduler and remove it if it exists.
        }
    }
}
