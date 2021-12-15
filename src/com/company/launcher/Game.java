package com.company.launcher;

import com.company.display.Display;

public class Game implements Runnable {
    private Display display;

    private Thread thread;
    private boolean isRunning;

    private int width, height;
    private String title;

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
     * Main operator of the game.
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

    private void update() {

    }

    private void render() {

    }

    private void init() {
        display = new Display(title, width, height);
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
