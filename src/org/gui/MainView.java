package org.gui;

import java.awt.event.ActionListener;

import javax.swing.*;

import org.scheduler.Process;
import org.scheduler.Scheduler;
import org.scheduler.Processor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainView implements Runnable, ActionListener {

    // Components of the UI
    private JFrame frame;
    private JPanel panel;

    // The model table from which all process are seen
    private static ProcessTable tpt = new ProcessTable();
    // The model table from which all killed/finalized process are seem
    private static ProcessTable tpt2 = new ProcessTable();

    // Table for processes on ready/blocked processes
    private JTable ttable = new JTable(tpt);
    // Table for finalized process
    private JTable ttable2 = new JTable(tpt2);
    private JScrollPane sptable = new JScrollPane(ttable);
    private JScrollPane sptable2 = new JScrollPane(ttable2);

    private JLabel ltable = new JLabel("Tabela de processos");
    private JLabel ltable2 = new JLabel("Tabela de processos finalizados");
    private JLabel lexecuting =  new JLabel("Executando o processo");
    public static JLabel lexecdescription =  new JLabel();
    private JLabel lprocessor = new JLabel("Processador RAMIx86_64");

    private JButton bnew = new JButton("Novo processo");
    private JButton bremove = new JButton("Matar processo");
    private JButton breset = new JButton("Resetar simulação");
    private JButton binit = new JButton("Iniciar simulação");

    // The scheduler used 
    static Scheduler scheduler = new Scheduler();

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

        lexecuting.setBounds(10, 80, 250, 20);
        panel.add(lexecuting);

        lexecdescription.setBounds( 10, 130, 250, 20);
        panel.add(lexecdescription);

        ltable.setBounds(10, 180, 150, 20);
        panel.add(ltable);

        ltable2.setBounds(400, 180, 200, 20);
        panel.add(ltable2);

        sptable.getViewport().add(ttable);
        sptable.setBounds(10, 200, 380, 320);
        panel.add(sptable);

        ttable2.setEnabled(false);
        sptable2.getViewport().add(ttable2);
        sptable2.setBounds(400, 200, 390, 320);
        panel.add(sptable2);

        bnew.setBounds(frame.getWidth()-220, frame.getHeight()-70, 100, 30);
        panel.add(bnew);

        bremove.setBounds(frame.getWidth()-110, frame.getHeight()-70, 100, 30);
        panel.add(bremove);

        binit.setBounds(10, frame.getHeight()-70, 130, 30);
        panel.add(binit);

        breset.setBounds(160, frame.getHeight()-70, 130, 30);
        panel.add(breset);

        bnew.addActionListener(this);
        bremove.addActionListener(this);
        breset.addActionListener(this);
        binit.addActionListener(this);

        frame.getContentPane().add(panel);
        frame.setVisible(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    Processor pc = new Processor(100);

    public static void update(Process p) {
        scheduler.remove(p);

        Object[] data = new Object[4];

        data[0] = p.pid;
        data[1] = p.name;
        data[2] = p.getExecutionTime();
        data[3] = p.getPriority();

        tpt.remove(data);
        tpt2.push(data);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bnew) {
            Process p = scheduler.createProcess();
            p.properties();

            scheduler.add(p);

            // What I'm about to do looks like poop but it will work
            tpt.clear();

            for (Object[] o : scheduler.get()) {
                tpt.push(o);
            }

        } else if (e.getSource() == bremove) {
            int row = ttable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um processo",
                        "Erro de seleção", JOptionPane.WARNING_MESSAGE);
            } else {
                Object[] process = tpt.getValue(row);
                int pid = (int) process[0];
                scheduler.remove(pid);

                tpt.remove(row);
                tpt2.push(process);
            }
        } else if (e.getSource() == breset) {
            scheduler.clear();
            tpt.clear();
            tpt2.clear();
        } else if(e.getSource() == binit) {
            while(!scheduler.queueIsEmpty) {
                scheduler.update(pc);
            }
        }

    }
}