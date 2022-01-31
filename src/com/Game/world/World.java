package com.Game.world;

import com.Game.tile.Tile;

import java.awt.Graphics;

/*
* */
public class World {
    /*
    * 31x28, this is the height(31) and width(28) of my pacman map
    * Values are given in terms of tiles, so 31 means that I have 31 tiles in height
    * 28 means that I have 28 tiles in width
    *
    * 'tiles' is a matrix that stores the positions of tiles my world has
    * For ex, tiles[0][0] = 0, means that at position [0][0] I have a tile
    * with id == 0, which will load the wallTile in my world (Refer to Tile.java)\
    * */
    private int height, width;
    private int[][] tilePositions;

    public World(String path) {
        loadWorld(path);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                getTile(i, j).render(g, i * Tile.TILE_WIDTH, j * Tile.TILE_HEIGHT);
            }
        }
    }

    private Tile getTile(int x, int y) {
        return Tile.tiles[tilePositions[x][y]] == null ? Tile.tiles[tilePositions[x][y]] : Tile.tiles[0];
    }

    private void loadWorld(String path) {

    }
}
