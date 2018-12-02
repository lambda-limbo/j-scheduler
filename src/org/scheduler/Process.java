package org.scheduler;

import java.util.Comparator;

public class Process {
    /// The process identifier, an unique number that identifies the process within the process table
    public int pid;
    /// The name of the process
    public String name;
    /// How long does the process will take to finish its task
    private long executionTime;
    /// How many times a process has been executed, it helps the scheduler
    private int executed;
    /// The priority of execution of the process, it helps the scheduler as well
    private PRIORITY priority = PRIORITY.LOW;
    /// Does the process has finished its task?
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

    public long getExecutionTime() {
        return executionTime;
    }

    public PRIORITY getPriority() {
        return priority;
    }

    public int getExecuted() {
        return executed;
    }
}