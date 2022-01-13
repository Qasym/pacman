package com.Game.tile;

import com.Game.gfx.Assets;

//This class holds the background wall
public class Background extends Tile {
    public Background(int id) {
        super(Assets.getBackgroundSprite(), id);
    }
}
