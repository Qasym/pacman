package com.Game.entity;

import com.Game.launcher.Game;
import com.Game.utils.Handler;

import java.awt.Graphics;

/*
 * Entity is anything that is not a tile
 * Every item is an entity
 * Every enemy is an entity
 * Player himself is an entity
 * */
public abstract class Entity {
    protected float x, y; //position variables, they are floating point to achieve smoothness
    protected Handler handler;

    public float speed; //the "speed" at which our entities move
    public int width, height; //size of our entity
    public static final byte DEFAULT_ENTITY_WIDTH = 32;
    public static final byte DEFAULT_ENTITY_HEIGHT = 32;

    public Entity(Handler handler, float x, float y, int width, int height) { //we need to give a position to an entity
        this.handler = handler;
        this.x = x; this.y = y;
        this.width = width; this.height = height;
        speed = 5; //default speed, this value is temporary
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
}
