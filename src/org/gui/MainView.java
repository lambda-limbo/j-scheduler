package org.gui;

import java.awt.event.ActionListener;

import javax.swing.*;

import org.scheduler.Process;
import org.scheduler.Scheduler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainView implements Runnable, ActionListener {

    // Components of the UI
    private JFrame frame;
    private JPanel panel;

    // The model table from which all process are seen
    private ProcessTable tpt = new ProcessTable();
    // The model table from which all killed/finalized process are seem
    private ProcessTable tpt2 = new ProcessTable();

    // Table for processes on ready/blocked processes
    private JTable ttable = new JTable(tpt);
    // Table for finalized process
    private JTable ttable2 = new JTable(tpt2);
    private JScrollPane sptable = new JScrollPane(ttable);
    private JScrollPane sptable2 = new JScrollPane(ttable2);

    private JLabel ltable = new JLabel("Tabela de processos");
    private JLabel ltable2 = new JLabel("Tabela de processos finalizados");
    private JLabel lexecuting =  new JLabel("Executando de número PID: ");
    private JLabel lprocessor = new JLabel("Processador RAMIx86_64");

    private JButton bnew = new JButton("Novo processo");
    private JButton bremove = new JButton("Matar processo");

    // The scheduler used 
    Scheduler scheduler = new Scheduler();

    public void run() {}

    public MainView() {
        frame = new JFrame();
        panel = new JPanel();

        frame.setTitle("JScheduler");
        frame.setResizable(false);
        frame.setSize(800, 600);

        // I will manueally set the position of UI elements as the window size is constant.
        panel.setLayout(null);

        lprocessor.setBounds(10, 30, 250, 20);
        panel.add(lprocessor);

        lexecuting.setBounds(10, 50, 250, 20);
        panel.add(lexecuting);

        ltable.setBounds(10, 180, 150, 20);
        panel.add(ltable);

        ltable2.setBounds(400, 180, 200, 20);
        panel.add(ltable2);

        sptable.getViewport().add(ttable);
        sptable.setBounds(10, 200, 380, 320);
        panel.add(sptable);

        sptable2.getViewport().add(ttable2);
        sptable2.setBounds(400, 200, 390, 320);
        panel.add(sptable2);

        bnew.setBounds(frame.getWidth()-220, frame.getHeight()-70, 100, 30);
        panel.add(bnew);

        bremove.setBounds(frame.getWidth()-110, frame.getHeight()-70, 100, 30);
        bremove.setEnabled(false);
        panel.add(bremove);


        bnew.addActionListener(this);
        bremove.addActionListener(this);

        frame.getContentPane().add(panel);
        frame.setVisible(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bnew) {
            // TODO: Create a process and add it to the Scheduler
            Process p = Scheduler.createProcess();
            p.properties();

            scheduler.add(p);

            // What I'm about to do looks like poop but it will work
            tpt.clear();

            for (Object[] o : scheduler.get()) {
                tpt.push(o);
            }

            tpt.update();
        } else if (e.getSource() == bremove) {
            // TODO: Open the window to prompt for a process
            new KillProcess();
        }
    }
}