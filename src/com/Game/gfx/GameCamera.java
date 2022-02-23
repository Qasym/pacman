package com.Game.gfx;

import com.Game.entity.Entity;
import com.Game.tile.Tile;
import com.Game.utils.Handler;

/*
* This class is the implementation of the game camera
* It isn't the actual camera but rather a control
* of where tiles will be rendered
* */
public class GameCamera {
    private float xOffset, yOffset; // these variables tell how much "off" you want to draw tiles
    private final Handler handler;

    public GameCamera(Handler handler, float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.handler = handler;
    }

    /*
    * When pacman moves out of the map, we see the blank(white) space
    * This method is needed to check if there is a blank space in the
    * game camera
    *
    * It operates this way:
    * We know that our map starts at [0, 0] and ends at
    * [Tile.TILE_WIDTH * World.getColumns(), Tile.TILE_HEIGHT * World.getRows()]
    * (we take Columns for x coordinates because columns contribute to x-axis)
    * We simply check if our offsets are out of those bounds, and if they are
    * we adjust them accordingly
    *
    * Commented out code is for the case when the screen size is equal to the
    * world size in pixels
    * */
    public void checkBlankSpace() {
//        xOffset = 0;
//        yOffset = 0;
        if (xOffset < 0) {
            xOffset = 0;
        } else if (xOffset + handler.getGameWidth() > Tile.TILE_WIDTH * handler.getWorld().getWidth()) {
            xOffset = handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getGameWidth();
        }
        if (yOffset < 0) {
            yOffset = 0;
        } else if (yOffset + handler.getGameHeight() > Tile.TILE_HEIGHT * handler.getWorld().getHeight()) {
            yOffset = handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getGameHeight();
        }
    }

    /*
    * This method takes entity as a parameter makes the "camera" follow it with the entity in center
    *
    * We take the position of the entity and subtract the half of screen width/height
    * (remember that y-axis' direction is reversed, it grows downwards
    * So if we subtract from x-position of the entity the half of the screen it appears in the middle of the screen
    * Think about it as moving the screen to the left
    * Subtracting from y-position of the entity works because top-left corner is at position [0,0],
    * and it grows downwards, subtracting simply means to shift the camera upwards)
    * We add the half of the entity's width and height to center the camera at the center of the entity
    * (Otherwise it'd be centered at top-left corner of the entity sprite)*/
    public void centerOnEntity(Entity entity) {
        setxOffset(entity.getX() - (float)(handler.getGameWidth() / 2) + (float)(entity.getWidth() / 2));
        setyOffset(entity.getY() - (float)(handler.getGameHeight() / 2) + (float)(entity.getHeight() / 2));

        checkBlankSpace();
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
