package com.Game.world;

import com.Game.entity.EntityManager;
import com.Game.entity.moving.Monster;
import com.Game.entity.moving.Pacman;
import com.Game.entity.statics.apples.AngryBuff;
import com.Game.entity.statics.apples.Apple;
import com.Game.entity.statics.apples.SpeedBuff;
import com.Game.intelligence.characters.Billy;
import com.Game.intelligence.characters.Lilly;
import com.Game.intelligence.characters.Silly;
import com.Game.intelligence.characters.Tilly;
import com.Game.tile.Tile;
import com.Game.utils.Handler;
import com.Game.utils.Utils;

import java.awt.Graphics;

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

    /*
    * There are two tiles that teleport Entity that came to it
    * to a specific place in the map
    *
    * When pacman or monsters reach the exit from the map
    * they are going to be teleported to the other end of the corridor
    * that forms those exits
    * */
    private int portal1X, portal1Y, portal2X, portal2Y;

    public World(Handler handler, String path) {
        this.handler = handler;

        loadWorld(path);

        // Adding pacman
        this.entityManager = new EntityManager(handler,
                                               new Pacman(handler,
                                                          spawnPoints[0] * Tile.WIDTH,
                                                          spawnPoints[1] * Tile.HEIGHT));
        // Adding monsters
        initializeMonsters();

        // Adding apples
        initializeApples();
    }

    // This method adds apples to the game
    private void initializeApples() {
        int[] speedBuffPositions = new int[]{26, 6, 1, 1};
        int[] powerBuffPositions = new int[]{26, 21, 1, 26};

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == spawnPoints[0] && j == spawnPoints[1]) {
                    // except for the pacman spawn point put apple everywhere
                    continue;
                }
                if (tilePositions != null && tilePositions[i][j] == 1) {
                    if (i == speedBuffPositions[0]) {
                        if (j == speedBuffPositions[1]) {
                            entityManager.addEntity(new SpeedBuff(handler, i * Tile.WIDTH, j * Tile.HEIGHT));
                            continue;
                        } else if (j == powerBuffPositions[1]) {
                            entityManager.addEntity(new AngryBuff(handler, i * Tile.WIDTH, j * Tile.HEIGHT));
                            continue;
                        }
                    } else if (i == speedBuffPositions[2]) {
                        if (j == speedBuffPositions[3]) {
                            entityManager.addEntity(new SpeedBuff(handler, i * Tile.WIDTH, j * Tile.HEIGHT));
                            continue;
                        } else if (j == powerBuffPositions[3]) {
                            entityManager.addEntity(new AngryBuff(handler, i * Tile.WIDTH, j * Tile.HEIGHT));
                            continue;
                        }
                    }
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

    // Loads up all the fields of this class
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

        // Retrieving the positions of the portals
        portal1X = Utils.parseInt(tokens[12]);
        portal1Y = Utils.parseInt(tokens[13]);
        portal2X = Utils.parseInt(tokens[14]);
        portal2Y = Utils.parseInt(tokens[15]);

        tilePositions = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tilePositions[i][j] = Utils.parseInt(tokens[16 + height * i + j]);
                // I converted 2d array indexing into 1d array indexing and added 16
                // because I already assigned 16 tokens to other variables
            }
        }
    }

    private void initializeMonsters() {
        // Billy spawn begins
        Billy billyBrain = new Billy(handler, spawnPoints[0] * Tile.WIDTH,
                -1 * Tile.HEIGHT);
        Monster billy = new Monster(handler,
                                    spawnPoints[2] * Tile.WIDTH,
                                    spawnPoints[3] * Tile.HEIGHT, billyBrain);
        entityManager.addMonster(billy);
        // Billy spawn ends

        // Lilly spawn begins
        Monster lilly = new Monster(handler,
                                    spawnPoints[4] * Tile.WIDTH,
                                    spawnPoints[5] * Tile.HEIGHT,
                                    new Lilly(handler, 0,
                                              spawnPoints[1] * Tile.HEIGHT));
        entityManager.addMonster(lilly);
        // Lilly spawn ends

        // Tilly spawn begins
        Monster tilly = new Monster(handler,
                                    spawnPoints[6] * Tile.WIDTH,
                                    spawnPoints[7] * Tile.HEIGHT,
                                    new Tilly(handler,
                                              0,
                                              -1 * Tile.HEIGHT, billyBrain));
        entityManager.addMonster(tilly);
        // Tilly spawn ends

        // Silly spawn begins
        entityManager.addMonster(new Monster(handler,
                                             spawnPoints[8] * Tile.WIDTH,
                                             spawnPoints[9] * Tile.HEIGHT,
                                             new Silly(handler,
                                                       spawnPoints[0] * Tile.WIDTH,
                                                       spawnPoints[1] * Tile.HEIGHT)));
        // Silly spawn ends
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

    public int getPortal1X() {
        return portal1X;
    }

    public int getPortal1Y() {
        return portal1Y;
    }

    public int getPortal2X() {
        return portal2X;
    }

    public int getPortal2Y() {
        return portal2Y;
    }
}