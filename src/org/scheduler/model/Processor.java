package org.scheduler.model;

public class Processor {

    // The execution limit that a processor can give to a process in milliseconds. This is analogous to the clock of the
    // processor
    private long timeSlice;

    public long getTimeSlice() {
        return timeSlice;
    }
    public void setTimeSlice(long timeSlice) { this.timeSlice = timeSlice; }

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