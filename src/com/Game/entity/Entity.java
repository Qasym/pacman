package com.Game.entity;

import java.awt.Graphics;

/*
 * Entity is anything that is not a tile
 * Every item is an entity
 * Every enemy is an entity
 * Player himself is an entity
 * */
public abstract class Entity {
    protected float x, y; //position variables, they are floating point to achieve smoothness
    public int speed;

    public Entity(float x, float y) { //we need to give a position to an entity
        this.x = x; this.y = y;
        speed = 5; //default speed, this value is temporary
    }

    public abstract void tick();
    public abstract void render(Graphics g);
}
