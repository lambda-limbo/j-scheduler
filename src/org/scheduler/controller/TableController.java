package org.scheduler.controller;

import org.scheduler.view.ProcessTable;
import org.scheduler.model.Scheduler;


public  class TableController {

    /**
     * Describes which queue will be used to update the tables
     */
    public enum QueueType {
        PROCESS_QUEUE,
        DEAD_QUEUE
    }

    public QueueType queueType = QueueType.PROCESS_QUEUE;

    private TableController() {}

    public synchronized static void update(ProcessTable pt, Scheduler scheduler, QueueType queueType) {
        // What I'm about to do looks like MacGyverism but it will work
        pt.clear();

        for (Object[] o : scheduler.get(queueType)) {
            pt.push(o);
        }
    }

    public synchronized static void add(ProcessTable pt, Object[] p) {
        pt.push(p);
    }

    public synchronized static void remove(ProcessTable pt, int row) {
        pt.remove(row);
    }

    public synchronized static void clear(ProcessTable pt) {
        pt.clear();
    }

}
