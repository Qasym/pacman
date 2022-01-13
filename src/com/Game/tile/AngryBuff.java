package com.Game.tile;

import com.Game.gfx.Assets;

/* This class holds the angryBuff apple
   Whenever player eats angryApple, he can eat monsters and get 10 points per each eaten monster
 */
public class AngryBuff extends Tile {
    public AngryBuff(int id) {
        super(Assets.getAngrySprite(), id);
    }
}
