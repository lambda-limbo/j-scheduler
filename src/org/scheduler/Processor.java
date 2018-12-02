package org.scheduler;

import org.scheduler.Process;

public class Processor {

    // The execution limit that a processor can give to a process in milliseconds
    private long timeSlice;

    public Processor(long timeSlice) {
        this.timeSlice = timeSlice;
    }

    public void feed(Process p) {
        p.processIt(timeSlice);
        // This gives the behavior of processing a process
        try {
            Thread.sleep(timeSlice);
        } catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}