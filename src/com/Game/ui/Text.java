package com.Game.ui;

import com.Game.utils.Utils;

import java.awt.*;
import java.io.File;

/*
* This simple class is needed to display text on the screen
*
* One usage is to display score after Pacman dies
* */
public class Text {
    // Loads font for the game
    public static Font loadFont(String path, float size) {
        try {
            File fontFile = Utils.getResourceAsFile(path);
            assert fontFile != null;
            return Font.createFont(Font.TRUETYPE_FONT, fontFile)
                       .deriveFont(Font.PLAIN, size);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(102);
        }
        return null;
    }

    // Draws a string in a given point with a given font
    public static void drawString(Graphics g, String text, boolean center, int x, int y, Font font,
                                  Color color) {
        g.setColor(color);
        g.setFont(font);
        if (center) {
            FontMetrics fm = g.getFontMetrics(font);
            x -= fm.stringWidth(text) / 2;
            y -= fm.getHeight() / 2 + fm.getAscent();
        }
        g.drawString(text, x, y);
    }
}
