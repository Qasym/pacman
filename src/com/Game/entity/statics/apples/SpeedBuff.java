package com.Game.entity.statics.apples;

import com.Game.gfx.Assets;
import com.Game.utils.Handler;

/* This class holds the sprite of speedBuff apple
    Whenever player eats this apple, he gets speedBuff
    increases speed and gives bonus x2 per eaten apple
 */
public class SpeedBuff extends Apple {
    public SpeedBuff(Handler handler, float x, float y) {
        super(handler, x, y);
        appleSprite = Assets.getSpeedSprite();
    }
}
