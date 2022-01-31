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
    public static Tile  wallTile = new WallTile(0),
                        background = new Background(1),
                        apple = new Apple(2),
                        speedBuff = new SpeedBuff(3),
                        angryBuff = new AngryBuff(4);

    //Class stuff below
    protected BufferedImage texture;
    protected final int id;

    public static final int TILE_WIDTH = 32,
                            TILE_HEIGHT = 32;

    public Tile(BufferedImage image, int id) {
        texture = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void tick() {


    }

    // if tile is solid, it means player cannot pass through it
    public boolean isSolid() {
        return false;
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }
}
