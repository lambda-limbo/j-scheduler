package org.scheduler.view;

import java.awt.event.ActionListener;

import javax.swing.*;

import org.scheduler.model.Process;
import org.scheduler.model.Scheduler;
import org.scheduler.model.Processor;

import org.scheduler.controller.TableController;
import org.scheduler.controller.TableController.QueueType;
import org.scheduler.controller.ProcessController;

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
    private JLabel lprocessor = new JLabel("Processador: RAMI");
    private JLabel lprocessorspeedtext = new JLabel();
    private JTextField lprocessorspeed = new JTextField("100");
    private static JLabel lexecuting = new JLabel("Processo em execução");
    private static JLabel lexecdescription =  new JLabel();

    private JButton bnew = new JButton("Novo processo");
    private JButton bremove = new JButton("Matar processo");
    private JButton breset = new JButton("Resetar simulação");
    private JButton bclock = new JButton("Alterar clock");
    private static JButton binit = new JButton("Iniciar simulação");

    // The scheduler used
    public static Scheduler scheduler = new Scheduler();
    // The processor used
    Processor cpu = new Processor(100);

    // The thread that runs the processes
    private Thread t = null;

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

        lprocessorspeedtext.setFont(Fonts.bold);
        lprocessorspeedtext.setBounds(10, 55, 130, 20);
        lprocessorspeedtext.setText("Clock " + cpu.getTimeSlice() + " ms/s");
        panel.add(lprocessorspeedtext);

        lprocessorspeed.setBounds( frame.getWidth() - 200, 30, 50, 29);
        lprocessorspeed.setText((Long.toString(cpu.getTimeSlice())));
        panel.add(lprocessorspeed);

        bclock.setBounds(frame.getWidth() - 145, 30, 130, 30);
        panel.add(bclock);

        lexecuting.setVisible(false);
        lexecuting.setBounds(10, 115, 250, 20);
        panel.add(lexecuting);

        lexecdescription.setVisible(false);
        lexecdescription.setFont(Fonts.newBoldItalic(12));
        lexecdescription.setBounds( 10, 130, 250, 20);
        panel.add(lexecdescription);

        ltable.setBounds(10, 180, 200, 20);
        panel.add(ltable);

        ltable2.setBounds(400, 180, 200, 20);
        panel.add(ltable2);

        sptable.getViewport().add(ttable);
        sptable.setBounds(10, 200, 380, 320);
        panel.add(sptable);

        ttable2.setEnabled(false);
        sptable2.getViewport().add(ttable2);
        sptable2.setBounds(400, 200, 385, 320);
        panel.add(sptable2);

        bnew.setBounds(frame.getWidth()-265, frame.getHeight()-70, 120, 30);
        panel.add(bnew);

        bremove.setBounds(frame.getWidth()-135, frame.getHeight()-70, 120, 30);
        panel.add(bremove);

        binit.setBounds(10, frame.getHeight()-70, 130, 30);
        panel.add(binit);

        breset.setBounds(150, frame.getHeight()-70, 130, 30);
        panel.add(breset);

        bnew.addActionListener(this);
        bremove.addActionListener(this);
        breset.addActionListener(this);
        binit.addActionListener(this);
        bclock.addActionListener(this);

        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bnew) {
            Process p = ProcessController.create();
            p.properties();

            scheduler.add(p);
            TableController.update(tpt, scheduler, QueueType.PROCESS_QUEUE);
        } else if (e.getSource() == bremove) {
            int row = ttable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um processo",
                        "Erro de seleção", JOptionPane.WARNING_MESSAGE);
            } else {
                Object[] process = tpt.getValue(row);
                int pid = (int) process[0];
                scheduler.remove(pid);

                TableController.remove(tpt, row);
                TableController.add(tpt2, process);
            }
        } else if (e.getSource() == breset) {
            scheduler.clear();
            TableController.clear(tpt);
            TableController.clear(tpt2);

            lexecdescription.setVisible(false);
            lexecuting.setVisible(false);
            binit.setEnabled(true);

            if (t != null) {
                t.interrupt();
            }
        } else if(e.getSource() == binit) {
            if (!scheduler.queueIsEmpty) {
                executionFinished = false;

                lexecdescription.setVisible(true);
                lexecuting.setVisible(true);
                binit.setEnabled(false);

                // Creates a Runnable which runs on a separate thread of execution
                Runnable r = () -> {
                    while (!scheduler.queueIsEmpty) {
                        scheduler.update(cpu);
                    }
                };

                t = new Thread(r);
                t.start();
            }
        } else if (e.getSource() == bclock) {
            cpu.setTimeSlice(Long.parseLong(lprocessorspeed.getText()));
            lprocessorspeedtext.setText("Clock " + cpu.getTimeSlice() + " ms/s");
        }

    }

    public static void update(Process p) {
        TableController.add(tpt2, p.toObject());

        TableController.update(tpt, scheduler, QueueType.PROCESS_QUEUE);
        TableController.update(tpt2, scheduler, QueueType.DEAD_QUEUE);
    }

    public static void updateGUI(Process p) {
        try {
            if (!scheduler.queueIsEmpty) {
                MainView.lexecdescription.setText(p.pid + " - " + p.name + " - " +
                                                  p.getExecutionTime() + "ms");
            } else {
                MainView.lexecdescription.setText("");
                JOptionPane.showMessageDialog(null, "Execução de processos finalizada",
                                              "Informação", JOptionPane.INFORMATION_MESSAGE);
                MainView.lexecuting.setVisible(false);
                MainView.lexecdescription.setVisible(false);

                binit.setEnabled(true);
            }
        } catch(NullPointerException ex) {

        }
    }
}
