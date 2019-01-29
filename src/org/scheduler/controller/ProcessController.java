package org.scheduler.controller;

import org.scheduler.model.Process;
import org.scheduler.model.Scheduler;

import java.util.Random;

import static org.scheduler.model.Process.PRIORITY.*;

public class ProcessController {

    private static Random random;
    private static Integer pid;

    private ProcessController() {}

    public static Process create() {
        if (random == null) {
            random = new Random();
        }

        if (pid == null) {
            pid = 0;
        }

        String[] possibleProcesses = new String[]{"gnutella", "kern", "emacs", "vim", "acpid", "alsa",
                "firefox", "chrome", "leafpad", "intellij", "0ad", "word", "WoW",
                "calendar", "music", "steam", "origin", "git", "code", "xorg", "wayland",
                "gcc", "go"};

        int index = random.nextInt(possibleProcesses.length);

        long exec = (long) random.nextInt(100)*100/(random.nextInt(4)+1);

        int priority = random.nextInt(19)+1;
        Process.PRIORITY p = LOW;

        if (priority >= 8 && priority < 15) {
            p = MEDIUM;
        } else if (priority > 15) {
            p = HIGH;
        }

        Process process = new Process(pid, possibleProcesses[index], exec, p);
        pid++;

        return process;
    }
}
