package org.scheduler.model;

import java.util.*;

import org.scheduler.view.MainView;

import static org.scheduler.model.Process.PRIORITY.*;

public class Scheduler {

    private Comparator<Process> processComparator = new ProcessComparator();

    public boolean queueIsEmpty = false;
    private Queue<Process> processQueue = new PriorityQueue<>(processComparator);
    private Queue<Process> deadProcessQueue = new PriorityQueue<>(processComparator);

    private static int pid;

    public Scheduler() {
        pid = 0;
    }

    public void add(Process p) {
        processQueue.add(p);

        queueIsEmpty = false;
    }

    public void remove(Process p) {
        if (p != null ) {
            p.finished = true;

            deadProcessQueue.add(p);
            processQueue.remove(p);
        }
    }

    public void remove(int pid) {
        Process toBeRemoved = null;

        for (Process p : processQueue) {
            if (p.pid == pid) {
                toBeRemoved = p;
            }
        }

        if (toBeRemoved != null) {
            if (toBeRemoved.getExecutionTime() <= 0) {
                toBeRemoved.finished = true;
            }

            deadProcessQueue.add(toBeRemoved);
            processQueue.remove(toBeRemoved);
        }
    }

    public void clear() {
        processQueue.clear();
        deadProcessQueue.clear();
    }

    public List<Object[]> get() {
        List<Object[]> processes = new ArrayList<>();

        // copy of the original processQueue
        Queue<Process> copy = processQueue;

        for (Process p : copy) {
            Object[] o = new Object[4];

            o[0] = p.pid;
            o[1] = p.name;
            o[2] = p.getExecutionTime();
            o[3] = p.getPriority();

            processes.add(o);
        }

        return processes;
    }

    public Process createProcess() {
        String[] possibleProcesses = new String[]{"gnutella", "kern", "emacs", "vim", "acpid", "alsa",
                                      "firefox", "chrome", "leafpad", "intellij", "0ad", "word", "WoW",
                                      "calendar", "music", "steam", "origin", "git", "code", "xorg", "wayland",
                                      "gcc", "go"};


        Random random = new Random();

        int pid = Scheduler.pid;
        Scheduler.pid++;

        int index = random.nextInt(possibleProcesses.length);

        long exec = (long) random.nextInt(100)*100/(random.nextInt(4)+1);

        int priority = random.nextInt(19)+1;
        Process.PRIORITY p = LOW;

        if (priority >= 8 && priority < 15) {
            p = MEDIUM;
        } else if (priority > 15) {
            p = HIGH;
        }

        Process process = new Process(pid, possibleProcesses[index], exec, p);

        return process;
    }

    public synchronized void update(Processor processor) {
        // Process to be removed
        Process tbr = null;

        for (Process p : processQueue) {
            // if the process is already finished remove it from the process queue
            if (p.finished) {
                MainView.update(p);
                tbr = p;
            }
        }
        // Removing the process and placing it on the deadProcessQueue
        remove(tbr);
        MainView.updateTable(MainView.tpt);

        if (!processQueue.isEmpty()) {
            Process top = processQueue.peek();

            remove(top.pid);
            MainView.updateTable(MainView.tpt);

            MainView.updateGUI(top);
            processor.feed(top);

            add(top);
            MainView.updateTable(MainView.tpt);
        }

        queueIsEmpty = processQueue.isEmpty();
    }


    /**
     * @brief This is the sorter function, it orders elements accordingly with our scheduler algorithm.
     */
    private class ProcessComparator implements Comparator<Process> {
        @Override
        public int compare(Process p1, Process p2) {
            int r = 0;

            Process.PRIORITY pp1 =  p1.getPriority();
            Process.PRIORITY pp2 =  p2.getPriority();

            int te1 = p1.getExecuted();
            int te2 = p2.getExecuted();

            if (pp1 == pp2) {
                // if they have the same priority, the amount of times executed will decide who goes first
                if (te1 >= te2) {
                    r = 1;
                } else {
                    r = -1;
                }
            } else if (pp1 == HIGH && pp2 == LOW || pp1 == HIGH && pp2 == MEDIUM) {
                // If a process has a high priority but it has been executed at least 2 times more the amount of time
                // the other process has used the processor, the latter will take the lead on the queue.
                if (te1 > 2*te2) {
                    r = 1;
                } else {
                    r = -1;
                }
            } else if (pp2 == HIGH && pp1 == LOW || pp2 == HIGH && pp1 == MEDIUM) {
                // analogously to the previous comment
                if (te1 > 2*te2) {
                    r = -1;
                } else {
                    r = 1;
                }
            }

            return r;
        }
    }
}