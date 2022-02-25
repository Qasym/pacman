package com.Game.entity;

import com.Game.utils.Handler;

import java.awt.*;

/*
 * Entity is anything that is not a tile
 * Every item is an entity
 * Every enemy is an entity
 * Player himself is an entity
 * */
public abstract class Entity {
    protected float x, y; //position variables, they are floating point to achieve smoothness
    protected Handler handler; //handler holds all the necessary objects such as world and game
    protected Rectangle collisionBox; // Rectangle object to hold the collision box
    protected static final int DEFAULT_SPEED = 7;

    /*
     * I have to explain about collision bounds
     * Zero is the top left corner of entity sprite
     * One is the top left corner of collision box
     * Two is the top right corner of collision box
     * Three is the bottom left corner of collision box
     * Four is the bottom right corner of collision box
     * Five is the bottom right corner of entity sprite
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
    protected final int   DEFAULT_COLLISION_BOUNDS_X = 7, // these variables are relative to the pacman sprite
                        DEFAULT_COLLISION_BOUNDS_Y = 7, // meaning that the bounds are from top-left corner of pacman sprite
                        DEFAULT_COLLISION_BOUNDS_WIDTH = 18,
                        DEFAULT_COLLISION_BOUNDS_HEIGHT = 18;


    public float speed; //the "speed" at which our entities move
    public int width, height; //size of our entity
    public static final byte DEFAULT_ENTITY_WIDTH = 32;
    public static final byte DEFAULT_ENTITY_HEIGHT = 32;

    public Entity(Handler handler, float x, float y, int width, int height) { //we need to give a position to an entity
        this.handler = handler;
        this.x = x; this.y = y;
        this.width = width; this.height = height;
        speed = DEFAULT_SPEED;

        collisionBox = new Rectangle(0, 0, width, height);
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

    public float getSpeed() {
        return speed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public Rectangle getCollisionBox() {
        return collisionBox;
    }
}
