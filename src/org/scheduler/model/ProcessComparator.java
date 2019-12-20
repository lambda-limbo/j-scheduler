package org.scheduler.model;

import java.util.*;

import org.scheduler.model.Process;
import static org.scheduler.model.Process.PRIORITY.*;

/**
 * @brief This is the sorter function, it orders elements accordingly with our scheduler algorithm.
 */
public class ProcessComparator implements Comparator<Process> {

    ProcessComparator() {}

    @Override
    public int compare(Process p1, Process p2) {
        int r = 0;

        Process.PRIORITY pp1 =  p1.getPriority();
        Process.PRIORITY pp2 =  p2.getPriority();

        int te1 = p1.getExecuted();
        int te2 = p2.getExecuted();

        if (pp1 == pp2) {
            // if they have the same priority, the amount of times executed will decide who goes first
            if (te1 >= te2) {
                r = 1;
            } else {
                r = -1;
            }
        } else if (pp1 == HIGH) {
            // If a process has a high priority but it has been executed at least 2 times more the amount of time
            // the other process has used the processor, the latter will take the lead on the queue.
            if (te1 > 2*te2) {
                r = 1;
            } else {
                r = -1;
            }
        } else if (pp2 == HIGH) {
            // analogously to the previous else-if-block comment
            if (te1 > 2*te2) {
                r = -1;
            } else {
                r = 1;
            }
        }

        return r;
    }
}
