package com.Game.world;

import com.Game.launcher.Game;
import com.Game.tile.Tile;
import com.Game.utils.Utils;
import jdk.jshell.execution.Util;

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
    private Game game;

    public World(Game game, String path) {
        loadWorld(path);
        this.game = game;
    }

    public void tick() {

    }

    public void render(Graphics graphics) {
        int xStart = (int) Math.max(0, game.getGameCamera().getxOffset() / Tile.TILE_WIDTH),
            xEnd = (int) Math.min(rows, (game.getGameCamera().getxOffset() + game.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, game.getGameCamera().getyOffset() / Tile.TILE_HEIGHT),
            yEnd = (int) Math.min(columns, (game.getGameCamera().getyOffset() + game.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int i = xStart; i < xEnd; i++) {
            for (int j = yStart; j < yEnd; j++) {
                getTile(i, j).render(graphics,
                                    (int)(i * Tile.TILE_WIDTH - game.getGameCamera().getxOffset()),
                                    (int)(j * Tile.TILE_HEIGHT - game.getGameCamera().getyOffset()));
            }
        }
    }

    private Tile getTile(int x, int y) {
        try { // Try-catch statement just in case if I mess up with indices
            return Tile.tiles[tilePositions[x][y]] != null ? Tile.tiles[tilePositions[x][y]] : Tile.tiles[0];
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("%d rows, requested: %d\n", rows, x);
            System.out.printf("%d columns, requested: %d\n", columns, y);
            System.exit(-1);
        }
        return null;
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

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}
