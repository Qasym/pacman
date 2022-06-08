package com.Game.entity.statics.apples;

import com.Game.gfx.Assets;
import com.Game.utils.Handler;

/* This class holds the sprite of speedBuff apple
    Whenever player eats this apple, he gets speedBuff
    increases speed and gives bonus x2 per eaten apple
 */
public class SpeedBuff extends Apple {
    /*
     * These variables have to be static because they're
     * affecting the same pacman
     *
     * A problem arises when pacman eats one SpeedBuff and then
     * reaches the other when the effect from the first one is not over yet
     * This results in the early termination of the effect of the second buff
     * */
    private static double buffDelta;
    private static long buffLastTime;

    public SpeedBuff(Handler handler, float x, float y) {
        super(handler, x, y);
        appleSprite = Assets.getSpeedSprite();
    }

    @Override
    public void pacmanAteMe() {
        if (!isEaten()) {
            eaten = true;
            handler.getPacman().giveSpeedBuff();
            buffLastTime = System.currentTimeMillis();
            buffDelta = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (handler.getPacman().hasSpeedBuff()) {
            long now = System.currentTimeMillis();
            buffDelta += (now - buffLastTime) / 1000.0;
            buffLastTime = now;
            if (buffDelta > 8) {
                handler.getPacman().removeSpeedBuff();
                buffDelta = 0;
            }
        }
    }
}
