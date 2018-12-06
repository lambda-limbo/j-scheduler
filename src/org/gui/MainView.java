package org.gui;

import java.awt.event.ActionListener;

import javax.swing.*;

import org.scheduler.Process;
import org.scheduler.Scheduler;
import org.scheduler.Processor;

import java.awt.event.ActionEvent;

public class MainView implements ActionListener {

    // Components of the UI
    private JFrame frame;
    private JPanel panel;

    // The model table from which all process are seen
    public static ProcessTable tpt = new ProcessTable();
    // The model table from which all killed/finalized process are seem
    public static ProcessTable tpt2 = new ProcessTable();

    // Table for processes on ready/blocked processes
    private JTable ttable = new JTable(tpt);
    // Table for finalized process
    private JTable ttable2 = new JTable(tpt2);
    private JScrollPane sptable = new JScrollPane(ttable);
    private JScrollPane sptable2 = new JScrollPane(ttable2);

    private JLabel ltable = new JLabel("Tabela de processos pronto(s)");
    private JLabel ltable2 = new JLabel("Tabela de processos finalizado(s)");
    private JLabel lprocessor = new JLabel("Processador RAMIx86_64");
    private static JLabel lexecuting = new JLabel("Processo em execução");
    private static JLabel lexecdescription =  new JLabel();

    private JButton bnew = new JButton("Novo processo");
    private JButton bremove = new JButton("Matar processo");
    private JButton breset = new JButton("Resetar simulação");
    private static JButton binit = new JButton("Iniciar simulação");

    // The scheduler used 
    static Scheduler scheduler = new Scheduler();
    // The processor used
    Processor cpu = new Processor(100);

    static boolean executionFinished;

    public MainView() {
        frame = new JFrame();
        panel = new JPanel();

        frame.setTitle("JScheduler");
        frame.setResizable(false);
        frame.setSize(800, 600);

        // I will manueally set the position of UI elements as the window size is constant.
        panel.setLayout(null);

        lprocessor.setFont(Fonts.bold);
        lprocessor.setBounds(10, 30, 250, 20);
        panel.add(lprocessor);

        lexecuting.setVisible(false);
        lexecuting.setBounds(10, 115, 250, 20);
        panel.add(lexecuting);

        lexecdescription.setVisible(false);
        lexecdescription.setFont(Fonts.newBoldItalic(12));
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

        breset.setBounds(150, frame.getHeight()-70, 130, 30);
        panel.add(breset);

        bnew.addActionListener(this);
        bremove.addActionListener(this);
        breset.addActionListener(this);
        binit.addActionListener(this);

        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void update(Process p) {
        tpt2.push(p.toObject());

        tpt.update();
        tpt2.update();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bnew) {
            Process p = scheduler.createProcess();
            p.properties();

            scheduler.add(p);
            updateTable(tpt);
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

            lexecdescription.setVisible(false);
            lexecuting.setVisible(false);
            binit.setEnabled(true);
        } else if(e.getSource() == binit) {
            executionFinished = false;

            lexecdescription.setVisible(true);
            lexecuting.setVisible(true);
            binit.setEnabled(false);

            Runnable r = () -> {
                while(!scheduler.queueIsEmpty) {
                    scheduler.update(cpu);
                }
            };

            Thread t = new Thread(r);
            t.start();
        }

    }

    public static void updateTable(ProcessTable pt) {
        // What I'm about to do looks like MacGyverism but it will work
        pt.clear();

        for (Object[] o : scheduler.get()) {
            pt.push(o);
        }
    }

    public static void updateGUI(Process p) {
        try {
            MainView.lexecdescription.setText(p.pid + " - " + p.name + " - " + p.getExecutionTime() + "ms");
        } catch (NullPointerException ex) {
            MainView.lexecdescription.setText("Finalizado");
        }
    }
}