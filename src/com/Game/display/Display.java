package com.Game.display;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;

/*
* This is the Display class
* it contains everything needed to display
* our game on a window
* */
public class Display {
    private JFrame frame; //The appearing window
    private Canvas canvas; //Game graphics, we add graphics/sprites through this, allows us to draw things into the screen

    private final int width, height; //These two variable actually represent pixels;
    private final String title; //Title of the game

    public Display(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;

        createDisplay();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }

    private void createDisplay() {
        frame = new JFrame(title); //Initializing the window
        frame.setSize(width, height); //Sets up the size
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
        canvas.setFocusable(false); //this line is needed to make our window focused, not the thing we draw with(canvas),
                                    //for ex. keyboard inputs will be sent to the window, not to canvas

        frame.add(canvas); //We need to add our graphics to the window we created
        frame.pack(); //This line is needed to "refresh" the window with the added canvas; similar to source ~/.bashrc
    }
}
