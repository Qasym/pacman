package com.company.display;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;

public class Display {
    private JFrame frame; //The appearing window
    private Canvas canvas; //Game graphics, we add graphics/sprites through this

    private int width, height; //These two variable actually represent pixels;
    private String title;

    public Display(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;

        createDisplay();
    }

    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height); //Sets up our size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //This line is needed to close the game when the window is closed
        frame.setResizable(false); //turn off resizability
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        canvas = new Canvas();
        Dimension dimension = new Dimension(width, height);

        //Below 3 lines is to guarantee that window will not be changed
        canvas.setPreferredSize(dimension);
        canvas.setMaximumSize(dimension);
        canvas.setMinimumSize(dimension);

        frame.add(canvas);
        frame.pack();
    }
}
