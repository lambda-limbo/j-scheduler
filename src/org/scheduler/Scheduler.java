package org.scheduler;

import java.util.*;

import org.scheduler.Processor;

import static org.scheduler.Process.PRIORITY.*;

public class Scheduler {

    private Comparator<Process> processComparator = new ProcessComparator();

    private Queue<Process> processQueue = new PriorityQueue<>(processComparator);
    private Queue<Process> deadProcessQueue = new PriorityQueue<>(processComparator);

    private static int pid;

    public Scheduler() {
        pid = 0;
    }

    public void add(Process p) {
        processQueue.add(p);
    }

    public void remove(Process p) {
        p.finished = true;

        deadProcessQueue.add(p);
        processQueue.remove(p);
    }

    public void remove(int pid) {
        Process toBeRemoved = null;

        for (Process p : processQueue) {
            if (p.pid == pid) {
                toBeRemoved = p;
            }
        }

        if (toBeRemoved != null) {
            toBeRemoved.finished = true;

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
        String possibleProcesses[] = {"gnutella", "kern", "emacs", "vim", "acpid", "alsa",
                                      "firefox", "chrome", "leafpad", "intellij"};

        Random random = new Random();

        int pid = this.pid;
        this.pid++;

        int index = random.nextInt(10);

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

    public void update(Processor processor) {
        for (Process p : processQueue) {
            // if the process is already finished remove it from the process queue
            if (p.finished) {
                processQueue.remove(p);
            }

            Process top = processQueue.peek();

            processor.feed(top);
        }
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
                // if they have the same priority the amount of times executed the process will decide
                if (te1 > te2) {
                    r = 1;
                } else {
                    r = -1;
                }
            } else if (pp1 == Process.PRIORITY.HIGH && pp2 == Process.PRIORITY.LOW ||
                        pp1 == Process.PRIORITY.HIGH && pp2 == Process.PRIORITY.MEDIUM) {
                // If a process has a high priority but it has been executed at least 2 times more the amount of time
                // the other process has used the processor, the latter will take the lead on the queue.
                if (te1 > 2*te2) {
                    r = 1;
                } else {
                    r = -1;
                }
            } else if (pp2 == Process.PRIORITY.HIGH && pp1 == Process.PRIORITY.LOW ||
                    pp2 == Process.PRIORITY.HIGH && pp1 == Process.PRIORITY.MEDIUM) {
                // If a process has a high priority but it has been executed at least 2 times more the amount of time
                // the other process has used the processor, the latter will take the lead on the queue.
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