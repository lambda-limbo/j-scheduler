package org.scheduler.model;

import java.util.*;

import org.scheduler.view.MainView;

import org.scheduler.controller.TableController;
import org.scheduler.controller.TableController.QueueType;

public class Scheduler {

    private Comparator<Process> processComparator = new ProcessComparator();

    public boolean queueIsEmpty = true;
    private Queue<Process> processQueue = new PriorityQueue<>(processComparator);
    private Queue<Process> deadProcessQueue = new PriorityQueue<>(processComparator);

    public Scheduler() {}

    public void add(Process p) {
        processQueue.add(p);
        deadProcessQueue.remove(p);
        queueIsEmpty = false;
    }

    /**
     * Removes a process permanently from the process queue and add the dead process
     * on the dead process queue.
     * @param p The process to be removed.
     */
    public void remove(Process p) {
        if (p != null ) {
            p.finished = true;

            deadProcessQueue.add(p);
            processQueue.remove(p);
        }
    }

    /**
     * Removes a process temporarily from the process queue and places it on the
     * dead process queue.
     * @param pid The process identification of the process to be removed.
     */
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

    public List<Object[]> get(QueueType queueType) {
        List<Object[]> processes = new Vector<>();

        Queue<Process> copy = null;

        // copy of the original processQueue
        if (queueType == QueueType.PROCESS_QUEUE) {
            copy = processQueue;
        } else {
            copy = deadProcessQueue;
        }

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

    public synchronized void update(Processor processor) {
        // Process to be removed
        Process tbr = null;

        //if (!processQueue.isEmpty()) {
            for (Process p : processQueue) {
                // if the process is already finished remove it from the process queue
                if (p.finished) {
                    MainView.update(p);
                    tbr = p;
                }
            }
            // Removing the process and placing it on the deadProcessQueue
            remove(tbr);
            TableController.update(MainView.tpt, MainView.scheduler, QueueType.PROCESS_QUEUE);

            if (!processQueue.isEmpty()) {
                Process top = processQueue.peek();

                remove(top.pid);
                TableController.update(MainView.tpt, MainView.scheduler, QueueType.PROCESS_QUEUE);

                MainView.updateGUI(top);
                processor.feed(top);

                add(top);
                TableController.update(MainView.tpt, MainView.scheduler, QueueType.PROCESS_QUEUE);
            }
        //}

        queueIsEmpty = processQueue.isEmpty();

        if (queueIsEmpty) {
            MainView.updateGUI(null);
        }
    }
}
