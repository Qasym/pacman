package com.Game.ui;

import java.awt.*;
import java.awt.event.MouseEvent;

/*
* Class that is responsible for creating UI objects
* such as buttons
* */
public abstract class UIObject {
    protected float x, y; // position variables (top-left corner)
    protected int width, height; // width & height
    protected Rectangle bounds; // similar to a collision box of an entity
    protected boolean hovering = false; // variable to indicate if a mouse is hovering over the UIObject or not

    public UIObject(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int)x, (int)y, width, height);
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract void onClick();


    // Every time mouse moves we check if mouse is over the UIObject
    public void onMouseMove(MouseEvent e) {
        setHovering(bounds.contains(e.getX(), e.getY()));
    }

    // Releasing the mouse button is equivalent of click, so if user clicks, we call onClick()
    public void onMouseRelease(MouseEvent e) {
        if (hovering) { // if mouse was on the button
            onClick();
        }
    }

    // Getters and Setters

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHovering() {
        return hovering;
    }
}
