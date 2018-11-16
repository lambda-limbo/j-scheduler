package org;

import javax.swing.*;

import org.gui.Greetings;

public class Main {

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Greetings());
    }
}