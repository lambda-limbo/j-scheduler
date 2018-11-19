package org.scheduler;

import java.util.Queue;
import java.util.PriorityQueue;

public class Scheduler {

    Queue<Process> processQueue = new PriorityQueue<>();

    public Scheduler() {
        
    }
}