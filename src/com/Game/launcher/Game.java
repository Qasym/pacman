package com.Game.launcher;

import com.Game.display.Display;
import com.Game.gfx.Assets;
import com.Game.gfx.GameCamera;
import com.Game.input.KeyManager;
import com.Game.input.MouseManager;
import com.Game.states.GameState;
import com.Game.states.MenuState;
import com.Game.states.SettingsState;
import com.Game.states.State;
import com.Game.utils.Handler;

import java.awt.Graphics;
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
    private boolean isRunning; //This is the boolean needed to represent the game states

    private BufferStrategy bufferStrategy; //A way for computer to draw things into the screen; Refer to the class description for more information (done easily in IDE)
    private Graphics graphics; //Graphics class allows us to draw things into the Canvas

    // States
    private State gameState, menuState, settingsState;

    // Input
    private KeyManager keyManager;
    private MouseManager mouseManager;

    // Camera
    private GameCamera gameCamera;

    //Handler
    private Handler handler;

    public Game(String title, int width, int height) {
        this.isRunning = false;
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
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
    * change to the states of the object are visible to all threads.
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
     *
     * Additional explanation on how fps works:
     * Let's say we measure 1 second in nanoseconds (for precision purposes), that is 1e9
     * fps signifies how many frames per second we want to have in our game, for my machine it is 30
     * timePerTick is a period at which we have to "tick" and proceed with the tick() and render() methods
     * timePerTick = 1e9 / fps, it means that we will have fps(=30) ticks in one second, thus fps(=30) tick() and render() calls
     * (now - lastTime) / timePerTick is actually a measurement of what proportion of time has passed so far, we add that to delta
     * if (delta == 1) it means that enough time has passed to achieve 1 "timePerTick", this means that we have to call tick() and render() methods
     * */
    @Override
    public void run() {
        init();

        //FPS control code
        byte fps = 30; //fps answers the question "how many times do I want to call tick() and render() methods in one second?"
        double timePerTick = 1e9 / fps; //timePerTick is the maximum amount of time in nanoseconds I have to execute tick() and render() methods to achieve desired fps
        double delta = 0; //delta specifies when we have to call the tick() and render() methods again
        long now, lastTime = System.nanoTime(); //now is the variable needed in the loop, and lastTime is the time before running the loop

        while (isRunning) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now; //lastTime is updated to "now" (which is actually in the past at this point)

            if (delta >= 1) { //when 1 tick passes
                tick();
                render();
                delta--;
            }
        }

        stop();
    }

    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager); //this line "connects" the keyboard and the opened window
        display.getFrame().addMouseListener(mouseManager); //this line "connects" the mouse buttons and the opened window
        display.getFrame().addMouseMotionListener(mouseManager); //this line "connects" the mouse movement and opened window
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        Assets.init(); //initializing all the assets, we need this to avoid cropping all images over and over again in render method

        handler = new Handler(this); //initializing the handler
        gameCamera = new GameCamera(handler,0, 0); //initializing the camera

        //initializing the states
        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        settingsState = new SettingsState(handler);

        State.setState(menuState); //temporary
    }

    private void tick() { //update or "tick"
        keyManager.tick(); //we have to tick so that our keyboard works properly

        if (State.getState() != null) { //if our state is not null, we have to tick
            State.getState().tick();
        }
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

        if (State.getState() != null) { //if our state is not null, we have to render
            State.getState().render(graphics);
        }

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

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
