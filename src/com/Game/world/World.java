package com.Game.world;

import com.Game.tile.Tile;
import com.Game.utils.Handler;
import com.Game.utils.Utils;

import java.awt.Graphics;

/*
* */
public class World {
    /*
    * rows*columns, 31x28, this is the height(31) and width(28) of my pacman map
    * Values are given in terms of tiles, so 31 means that I have 31 tiles in height(rows)
    * 28 means that I have 28 tiles in width(columns)
    *
    * 'tiles' is a matrix that stores the positions of tiles my world has
    * For ex, tiles[0][0] = 0, means that at position [0][0] I have a tile
    * with id == 0, which will load the wallTile in my world (Refer to Tile.java)
    *
    * playerX & playerY variables store the spawn position for a player
    * */
    private int rows, columns;
    private int[][] tilePositions;
    private int playerX, playerY;
    private Handler handler;

    public World(Handler handler, String path) {
        loadWorld(path);
        this.handler = handler;
    }

    public void tick() {

    }

    public void render(Graphics graphics) {
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH),
            xEnd = (int) Math.min(rows, (handler.getGameCamera().getxOffset() + handler.getGameWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT),
            yEnd = (int) Math.min(columns, (handler.getGameCamera().getyOffset() + handler.getGameHeight()) / Tile.TILE_HEIGHT + 1);

        for (int i = xStart; i < xEnd; i++) {
            for (int j = yStart; j < yEnd; j++) {
                getTile(i, j).render(graphics,
                                    (int)(i * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                                    (int)(j * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
            }
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= rows || y >= columns) {
            return Tile.tiles[1];
        } else {
            return Tile.tiles[tilePositions[x][y]];
        }
    }

    private void loadWorld(String path) {
        // A string (split by any whitespace) representation of base_world file
        String[] tokens = Utils.loadFileAsString(path).split("\\s+");
        // **** **** **** **** **** //
        // Now we retrieve the useful information
        rows = Utils.parseInt(tokens[0]);
        columns = Utils.parseInt(tokens[1]);
        playerX = Utils.parseInt(tokens[2]);
        playerY = Utils.parseInt(tokens[3]);

        tilePositions = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                tilePositions[i][j] = Utils.parseInt(tokens[4 + columns * i + j]);
                // I converted 2d array indexing into 1d array indexing and added 4
                // because I already assigned 4 tokens to other variables
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}
