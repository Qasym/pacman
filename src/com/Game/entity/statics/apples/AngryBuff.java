package com.Game.entity.statics.apples;

import com.Game.utils.Handler;

/* This class holds the angryBuff apple
   Whenever player eats angryApple, he can eat monsters and get 10 points per each eaten monster
 */
public class AngryBuff extends Apple {
    public AngryBuff(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y);
    }
}
