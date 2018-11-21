package org.scheduler;

import java.util.Queue;
import java.util.PriorityQueue;

import org.scheduler.Processor;

public class Scheduler {

    private Queue<Process> processQueue = new PriorityQueue<>();

    public Scheduler() {
        
    }

    public void add(Process p) {
        processQueue.add(p);
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


}