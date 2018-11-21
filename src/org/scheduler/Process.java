package org.scheduler;

public class Process {
    ///
    public int pid;
    ///
    public String name;
    ///
    private long executionTime;
    ///
    private PRIORITY priority = PRIORITY.LOW;
    ///
    public Boolean finished = false;
    
    public enum PRIORITY {
        LOW,
        MEDIUM,
        HIGH
    }

    public Process(int pid, String name, long executionTime, PRIORITY priority) {
        this.pid = pid;
        this.name = name;
        this.executionTime = executionTime;
        this.priority = priority;
    }

    public void properties() {
        System.out.println("PID: " + this.pid + "\nName: " + name + "\nRunning time: " + this.executionTime + 
            "\nPriority: " + this.priority.toString());
    }

    public void processIt(long timeSlice) {
        if (executionTime < 0 ) {
            executionTime = 0;
            finished = true;
        } else {
            executionTime -= timeSlice;
        }
    }
}