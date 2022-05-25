package com.Game.tile;

import java.awt.*;
import java.awt.image.BufferedImage;

//Tile class is responsible for tiles
public class Tile {
    /*
    * The code structure below allows to store a single instance of a tile
    * and reuse it in multiple places
    * Access is also simplified through tiles array
    *
    * Each id of a tile is used to refer to that tile
    * */
    public static Tile[] tiles = new Tile[256]; // array to store all the tiles I initialize
    public static Tile  wallTile = new WallTile(0), // These IDs are important to access the tiles
                        background = new Background(1);

    //Class stuff below
    protected BufferedImage texture;
    protected final int id;

    public static final int WIDTH = 40,
                            HEIGHT = 40;

    public Tile(BufferedImage image, int id) {
        texture = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void tick() {}

    // if tile is solid, it means player cannot pass through it
    public boolean isSolid() {
        return false;
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, WIDTH, HEIGHT, null);
    }
}
