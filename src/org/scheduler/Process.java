package org.scheduler;

public class Process {
    private int pid;
    private String name;
    private long runningTime;

    private static PRIORITY priority = PRIORITY.LOW;
    
    public enum PRIORITY {
        LOW,
        MEDIUM,
        HIGH
    }

    public Process(int pid, String name, long runningTime, PRIORITY priority) {
        this.pid = pid;
        this.name = name;
        this.runningTime = runningTime;
        this.priority = priority;
    }

    public void listProperties() {
        System.out.println("PID: " + this.pid + "\nName: " + name + "\nRunning time: " + this.runningTime + 
            "\nPriority: " + this.priority.toString());
    }


}