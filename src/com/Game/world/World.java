package com.Game.world;

import com.Game.entity.EntityManager;
import com.Game.entity.moving.Monster;
import com.Game.entity.moving.Pacman;
import com.Game.entity.statics.apples.Apple;
import com.Game.intelligence.characters.Billy;
import com.Game.tile.Tile;
import com.Game.utils.Handler;
import com.Game.utils.Utils;

import java.awt.Graphics;

/*
* */
public class World {
    /*
    * My map is transposed (I don't know why I made it that way)
    * Because of this given the size of the map 28x31 with width=28,
    * it makes sense that 28 is actually the width of the map
    *
    * rows*columns, 28x31, this is the height(31) and width(28) of my pacman map
    * Values are given in terms of tiles, so 31 means that I have 31 tiles in height
    * 28 means that I have 28 tiles in width
    *
    * 'tiles' is a matrix that stores the positions of tiles my world has
    * For ex, tiles[0][0] = 0, means that at position [0][0] I have a tile
    * with id == 0, which will load the wallTile in my world (Refer to Tile.java)
    *
    * spawnPoints[0] & spawnPoints[1] - variables store the spawn position for a player
    * in terms of tiles, to get the actual position in pixels have to multiply
    * by the tile width & height, respectively
    *
    * handler - read the description of Handler class
    *
    * entityManager - manages all the entities instead of hard-typing them
    * */
    private int width, height;
    private int[][] tilePositions;
    private int[] spawnPoints;
    private final Handler handler;
    private final EntityManager entityManager;

    public World(Handler handler, String path) {
        this.handler = handler;

        loadWorld(path);

        // Adding pacman
        this.entityManager = new EntityManager(handler,
                                            new Pacman(handler,
                                                    spawnPoints[0] * Tile.WIDTH,
                                                    spawnPoints[1] * Tile.HEIGHT));

        // Adding monsters
        entityManager.addEntity(new Monster(handler, spawnPoints[2] * Tile.WIDTH,
                                                     spawnPoints[3] * Tile.HEIGHT,
                                                     new Billy(handler, spawnPoints[0] * Tile.WIDTH,
                                                                        -1 * Tile.HEIGHT)));
        // Temporarily disabling all monsters except Billy
//        entityManager.addEntity(new Monster(handler, spawnPoints[4] * Tile.TILE_WIDTH,
//                                                     spawnPoints[5] * Tile.TILE_HEIGHT,
//                                                     new Lilly(handler, 0,
//                                                                        spawnPoints[1] * Tile.TILE_HEIGHT)));
//        entityManager.addEntity(new Monster(handler, spawnPoints[6] * Tile.TILE_WIDTH,
//                                                     spawnPoints[7] * Tile.TILE_HEIGHT,
//                                                     new Silly(handler, 0,
//                                                                        -1 * Tile.TILE_HEIGHT)));
//        entityManager.addEntity(new Monster(handler, spawnPoints[8] * Tile.TILE_WIDTH,
//                                                     spawnPoints[9] * Tile.TILE_HEIGHT,
//                                                     new Tilly(handler, spawnPoints[0] * Tile.TILE_WIDTH,
//                                                                        spawnPoints[1] * Tile.TILE_HEIGHT)));

        // Adding apples
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == spawnPoints[0] && j == spawnPoints[1]) {
                    // except for the pacman spawn point put apple everywhere
                    continue;
                }
                if (tilePositions != null && tilePositions[i][j] == 1) {
                    entityManager.addEntity(new Apple(handler, i * Tile.WIDTH, j * Tile.HEIGHT));
                }
            }
        }
    }

    public void tick() {
        // ticking all the entities
        entityManager.tick();
    }

    public void render(Graphics graphics) {
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.WIDTH),
            xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getGameWidth()) / Tile.WIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.HEIGHT),
            yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getGameHeight()) / Tile.HEIGHT + 1);

        for (int i = xStart; i < xEnd; i++) {
            for (int j = yStart; j < yEnd; j++) {
                getTile(i, j).render(graphics,
                                    (int)(i * Tile.WIDTH - handler.getGameCamera().getxOffset()),
                                    (int)(j * Tile.HEIGHT - handler.getGameCamera().getyOffset()));
            }
        }

        // Rendering all the entities
        entityManager.render(graphics);
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
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
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);

        spawnPoints = new int[10];

        // Retrieving spawn points of pacman and monsters
        for (int i = 0; i < 10; i++) {
            spawnPoints[i] = Utils.parseInt(tokens[i + 2]);
        }

        tilePositions = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tilePositions[i][j] = Utils.parseInt(tokens[12 + height * i + j]);
                // I converted 2d array indexing into 1d array indexing and added 4
                // because I already assigned 12 tokens to other variables
            }
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPacmanSpawnX() {
        return spawnPoints[0];
    }

    public int getPacmanSpawnY() {
        return spawnPoints[1];
    }
}
