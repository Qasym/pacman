package com.Game.entity.statics.apples;

import com.Game.gfx.Assets;
import com.Game.tile.Tile;
import com.Game.ui.Text;
import com.Game.utils.Handler;

import java.awt.*;

/* This class holds the angryBuff apple
   Whenever player eats angryApple, he can eat monsters and get 10 points per each eaten monster
 */
public class AngryBuff extends Apple {
    /*
    * These variables have to be static because they're
    * affecting the same pacman
    *
    * A problem arises when pacman eats one AngryBuff and then
    * reaches the other when the effect from the first one is not over yet
    * This results in the early termination of the effect of the second buff
    * */
    private static double buffDelta;
    private static long buffLastTime;

    public AngryBuff(Handler handler, float x, float y) {
        super(handler, x, y);
        appleSprite = Assets.getAngrySprite();
    }

    @Override
    public void pacmanAteMe() {
        if (!isEaten()) {
            eaten = true;
            handler.getPacman().givePowerBuff();
            buffLastTime = System.currentTimeMillis();
            buffDelta = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (handler.getPacman().hasPowerBuff()) {
            long now = System.currentTimeMillis();
            buffDelta += (now - buffLastTime) / 1000.0;
            buffLastTime = now;
            if (buffDelta > 8) {
                handler.getPacman().removePowerBuff();
                buffDelta = 0;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        if (handler.getPacman().hasPowerBuff()) {
            Text.drawString(g, "Can eat Monsters", false, Tile.WIDTH, Tile.HEIGHT, Assets.getFont(), Color.RED);
        }
    }
}
