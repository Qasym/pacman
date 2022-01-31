package com.Game.tile;

import com.Game.gfx.Assets;

/*
* Whenever player eats an apple, he gets 1 score
* */
public class Apple extends Tile {
    public Apple(int id) {
        super(Assets.getAppleSprite(), id);
        tiles[id] = this;
    }
}
