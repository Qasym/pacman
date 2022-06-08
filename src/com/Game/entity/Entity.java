package com.Game.entity;

import com.Game.tile.Tile;
import com.Game.utils.Handler;

import java.awt.*;

/*
 * Entity is anything that is not a tile
 * Every item is an entity
 * Every enemy is an entity
 * Player himself is an entity
 * */
public abstract class Entity {
    protected float spawnPosX, spawnPosY;
    protected float x, y; //position variables, they are floating point to achieve smoothness
    protected Handler handler; //handler holds all the necessary objects such as world and game
    protected Rectangle collisionBox; // Rectangle object to hold the collision box
    public static final int DEFAULT_SPEED = 7;

    /*
     * I have to explain about collision bounds
     * 0 is the top left corner of entity sprite
     * 1 is the top left corner of collision box
     * 2 is the top right corner of collision box
     * 3 is the bottom left corner of collision box
     * 4 is the bottom right corner of collision box
     * 5 is the bottom right corner of entity sprite
     *
     * 0
     * |
     * | <- DEFAULT_COLLISION_BOUNDS_Y
     * |
     * |   DEFAULT_COLLISION_BOUNDS_X
     * |    |
     * |    \/
     * -----------1 -DEFAULT_COLLISION_BOUNDS_WIDTH- 2
     *            D
     *            E
     *            F
     *            H
     *            E
     *            I
     *            G
     *            H
     *            T
     *            3 -DEFAULT_COLLISION_BOUNDS_WIDTH- 4
     *
     *                                                           5
     *
     * */
    public static final int   DEFAULT_COLLISION_BOUNDS_X = 7, // these variables are relative to the pacman sprite
                        DEFAULT_COLLISION_BOUNDS_Y = 7, // meaning that the bounds are from top-left corner of pacman sprite
                        DEFAULT_COLLISION_BOUNDS_WIDTH = 18,
                        DEFAULT_COLLISION_BOUNDS_HEIGHT = 18;


    protected int speed; //the "speed" at which our entities move
    protected int width, height; //size of our entity
    public static final byte DEFAULT_ENTITY_WIDTH = 32;
    public static final byte DEFAULT_ENTITY_HEIGHT = 32;

    public Entity(Handler handler, float x, float y, int width, int height) { //we need to give a position to an entity
        this.handler = handler;
        this.x = x; this.y = y; // initially position and spawn point has to be same
        this.spawnPosX = x; this.spawnPosY = y;
        this.width = width; this.height = height;
        speed = DEFAULT_SPEED;

        collisionBox = new Rectangle((int) (x + DEFAULT_COLLISION_BOUNDS_X),
                                     (int) (y + DEFAULT_COLLISION_BOUNDS_Y),
                                     DEFAULT_COLLISION_BOUNDS_WIDTH, DEFAULT_COLLISION_BOUNDS_HEIGHT);
    }

    // This method returns true if a tile at [x, y] is solid
    // This method is to check if *this* entity collides with a tile at x, y
    public boolean collidesWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }

    public float getSpawnPosX() {
        return this.spawnPosX;
    }

    public float getSpawnPosY() {
        return this.spawnPosY;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void teleportAtPortal() {
        if (isAtTile(handler.getWorld().getPortal1X(), handler.getWorld().getPortal1Y())) {
            x = (handler.getWorld().getPortal2X() - 1) * Tile.WIDTH;
            y = handler.getWorld().getPortal2Y() * Tile.HEIGHT;
        } else if (isAtTile(handler.getWorld().getPortal2X(), handler.getWorld().getPortal2Y())) {
            x = (handler.getWorld().getPortal1X() + 1) * Tile.WIDTH;
            y = handler.getWorld().getPortal1Y() * Tile.HEIGHT;
        }
    }

    private boolean isAtTile(int x, int y) {
        return collisionBox.x / Tile.WIDTH == x && collisionBox.y / Tile.HEIGHT == y &&
               (collisionBox.x + collisionBox.width) / Tile.WIDTH == x &&
               (collisionBox.y + collisionBox.height) / Tile.HEIGHT == y;
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public Rectangle getCollisionBox() {
        return collisionBox;
    }
}
