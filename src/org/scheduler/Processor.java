package org.scheduler;

public class Processor {

    // The execution limit that a processor can give to a process in milliseconds
    private long timeSlice;

    public Processor(long timeSlice) {
        this.timeSlice = timeSlice;
    }

    public void feed(Process p) {
        // SOME WEIRD BUG IS TURNING ONE BOOLEAN VARIABLE TO TRUE (THE FINISHED VARIABLE)
        // SO I'M EXPLICITLY ATTRIBUTING THIS VARIABLE TO FALSE.
        p.finished = false;
        p.processIt(timeSlice);
        // This gives the behavior of processing a process
        try {
            Thread.sleep(timeSlice);
        } catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}