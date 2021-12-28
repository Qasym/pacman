package com.Game.launcher;

import com.Game.display.Display;
import com.Game.launcher.gfx.Assets;
import com.Game.launcher.gfx.ImageLoader;
import com.Game.launcher.gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferStrategy;

/*
* Main game class
* Contains main game logic
* */
public class Game implements Runnable {
    private Display display; //Display object from the Display class
    private final String title; //Game title
    private final int width, height; //Display size

    private Thread thread; //The thread in which the game is running
    private boolean isRunning; //This is the boolean needed to represent the game state

    private BufferStrategy bufferStrategy; //A way for computer to draw things into the screen; Refer to the class description for more information (done easily in IDE)
    private Graphics graphics; //Graphics class allows us to draw things into the Canvas

    public Game(String title, int width, int height) {
        this.isRunning = false;
        this.width = width;
        this.height = height;
        this.title = title;
    }

    /*
    * This method is for successful start of the game as a
    * separate process.
    *
    * Q: What is synchronized method?
    * A:
    * 1) It is not possible for two invocations of
    * synchronized methods on the same object to interleave.
    * When one thread is executing a synchronized method for an object,
    * all other threads that invoke synchronized methods for the same
    * object block (suspend execution) until the first thread is done
    * with the object.
    * 2) When a synchronized method exits, it automatically establishes
    * a happens-before relationship with any subsequent invocation of a
    * synchronized method for the same object. This guarantees that the
    * change to the state of the object are visible to all threads.
    * */
    public synchronized void start() {
        if (isRunning) return;
        isRunning = true;

        thread = new Thread(this);
        thread.start();
    }

    /*
     * Main operator of the game. This method is called only once/
     * This method contains the game loop.
     * Update -> Render -> loop
     * */
    @Override
    public void run() {
        init();

        while (isRunning) {
            update();
            render();
        }

        stop();
    }

    private void init() {
        display = new Display(title, width, height);
        Assets.init();
    }

    private void update() {

    }

    /*
    * Every time this method is called, we want to
    * clear the screen and draw what we want on the new screen
    * (Otherwise we would be drawing on a "dirty" screen)
    * */
    private void render() {
        bufferStrategy = display.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            /*
            * At first, we don't have anything to draw, hence there is no buffer.
            * This if statement checks for that (.getBufferStrategy() returns null)
            * and creates a new BufferStrategy
            * */
            display.getCanvas().createBufferStrategy(3);
            return; //exit the method
        }
        graphics = bufferStrategy.getDrawGraphics();

        graphics.clearRect(0, 0, width, height); //Clears the entire screen

        // Drawing starts here  ////////
        graphics.drawImage(Assets.getPacmanRight(), 15, 15, null);
        // Drawing ends here    ////////

        bufferStrategy.show(); //This line updates the screen by working with buffers
        graphics.dispose(); //Disposes of this graphics context and releases any system resources that it is using
    }



    /*
    * This method successfully stops the thread
    * */
    public synchronized void stop() {
        if (!isRunning) return;

        isRunning = false;
        try {
            thread.join();  //Waits for this thread to die.
                            //An invocation of this method behaves in exactly the same way as the invocation join(0)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
