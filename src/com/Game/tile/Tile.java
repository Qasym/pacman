package com.Game.tile;

import java.awt.*;
import java.awt.image.BufferedImage;

//Tile class is responsible for tiles
public class Tile {
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
