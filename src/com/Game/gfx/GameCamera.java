package com.Game.gfx;

import com.Game.entity.Entity;
import com.Game.launcher.Game;
/*
* This class is the implementation of the game camera
* It isn't the actual camera but rather a control
* of where tiles will be rendered
* */
public class GameCamera {
    private float xOffset, yOffset; // these variables tell how much "off" you want to draw tiles
    private final Game game;

    public GameCamera(Game game, float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.game = game;
    }

    /*
    * This method takes entity as a parameter makes the "camera" follow it with the entity in center
    *
    * We take the position of the entity and subtract the half of screen width/height
    * (remember that axis directions are reversed, )*/
    public void centerOnEntity(Entity entity) {
        setxOffset(entity.getX() - (float)(game.getWidth() / 2) + (float)(entity.getWidth() / 2));
        setyOffset(entity.getY() - (float)(game.getHeight() / 2) + (float)(entity.getHeight() / 2));
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }
}
