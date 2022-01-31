package com.Game.tile;

import com.Game.gfx.Assets;

/* This class holds the sprite of speedBuff apple
    Whenever player eats this apple, he gets speedBuff
    increases speed and gives bonus x2 per eaten apple
 */
public class SpeedBuff extends Tile {
    public SpeedBuff(int id) {
        super(Assets.getSpeedSprite(), id);
        tiles[id] = this;
    }
}
