package com.Game.gfx;

import java.awt.image.BufferedImage;

/*
* Animation class is responsible for animations
* Basically a way to switch sprites after some time
* */
public class Animation {
    private int changeRate;  // time after which we should change our frame
    private int index; // to track the index of a frame
    private BufferedImage[] frames;
    private long lastTime, timer;

    public Animation(int rate, BufferedImage[] frames) {
        this.changeRate = rate;
        this.frames = frames;
        index = 0;

        lastTime = System.currentTimeMillis();
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

    /*
    * How tick() method works?
    * Tick method will be called every tick (bu-dum-tss)
    * and we will count how much time have passed since
    * last time this method was called
    * The accumulated number is basically a representation of how much
    * time has passed so far, and if that time is greater than changeRate
    * we have to update 'index' to give a different frame
    * */
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > changeRate) {
            index++;
            timer = 0;

            if (index >= frames.length) {
                index = 0;
            }
        }
    }
}
