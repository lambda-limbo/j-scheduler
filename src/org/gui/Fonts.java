package org.gui;

import java.awt.*;

public class Fonts {

    private Fonts() {}

    public static final Font normal = new Font(null, Font.PLAIN, 13);

    public static final Font bold = new Font(null, Font.BOLD, 15);
    public static final Font italic = new Font(null, Font.BOLD, 15);
    public static final Font bold_italic  = new Font(null, Font.BOLD, 15);

    public static Font newBold(int size) {
        return new Font(null, Font.BOLD, size);
    }

    public static Font newItalic(int size) {
        return new Font(null, Font.ITALIC, size);
    }
}
