package com.Game.tile;

import com.Game.gfx.Assets;

//Class for Wall sprites
public class WallTile extends Tile {
    public WallTile(int id) {
        super(Assets.getWall(), id);
        tiles[id] = this;
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
