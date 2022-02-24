package com.Game.entity.statics.apples;

import com.Game.entity.Entity;
import com.Game.entity.statics.StaticEntity;
import com.Game.gfx.Assets;
import com.Game.tile.Tile;
import com.Game.utils.Handler;

import java.awt.*;

/*
* Apple class is a class that is responsible for
* giving a score to our player.
* Those yellow balls in the original pacman game
* */
public class Apple extends StaticEntity {
    private final int   DEFAULT_COLLISION_BOUNDS_X = 2, // these variables are relative to the apple sprite origin
                        DEFAULT_COLLISION_BOUNDS_Y = 2, // meaning that the bounds are from top-left corner of apple sprite
                        DEFAULT_COLLISION_BOUNDS_WIDTH = 4,
                        DEFAULT_COLLISION_BOUNDS_HEIGHT = 4;
    private boolean eaten = false; // this variable controls if this apple is interactable&visible or not
    private boolean isCentered = false; // flag to indicate if our apple is centered or not

    // apple respawn timer variables
    // operates in a very similar way as animations (in the same way)
    private static final long respawnRate = 60000; // in milliseconds
    private long lastTime, timer;

    public Apple(Handler handler, float x, float y) {
        // I want my apples to be 4 times smaller than the pacman
        super(handler, x, y, Entity.DEFAULT_ENTITY_WIDTH / 4, Entity.DEFAULT_ENTITY_HEIGHT / 4);

        // Setting up a collisionBox;
        // collisionBox.x & collisionBox.y - are a position of top-left of our collisionBox
        collisionBox.x = (int)(x + DEFAULT_COLLISION_BOUNDS_X);
        collisionBox.y = (int)(y + DEFAULT_COLLISION_BOUNDS_Y);
        collisionBox.width = DEFAULT_COLLISION_BOUNDS_WIDTH;
        collisionBox.height = DEFAULT_COLLISION_BOUNDS_HEIGHT;
    }

    @Override
    public void tick() {
        if (eaten) {
            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if (timer >= respawnRate) {
                eaten = false;
                tick();
            }
        } else {
            // centering apple in the tile it stands
            centerOnTile();

            // collisionBox update
            collisionBox.x = (int)(x + DEFAULT_COLLISION_BOUNDS_X);
            collisionBox.y = (int)(y + DEFAULT_COLLISION_BOUNDS_Y);
        }
    }

    @Override
    public void render(Graphics g) {
        if (eaten) return; // if this apple is eaten we don't draw it

        boolean checkingCollisions = Handler.DEBUG; // variable for testing purposes
        if (checkingCollisions) {
            // Temporary code to check collision related stuff;
            g.setColor(Color.BLUE);
            g.drawRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height);
            g.setColor(Color.RED);
            g.fillRect( (int)(collisionBox.x - handler.getGameCamera().getxOffset()),
                    (int)(collisionBox.y - handler.getGameCamera().getyOffset()),
                    collisionBox.width, collisionBox.height);
        } else {
            g.drawImage(Assets.getAppleSprite(),
                    (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), null);
        }
    }

    /*
    * When the apple is created initially, it is spawned in the
    * top left corner of a tile it was initially spawned on
    * This method moves it to the center of the tile
    * */
    public void centerOnTile() {
        if (!isCentered) {
            isCentered = true;
            x = x + (Tile.TILE_WIDTH / 2f) - (width / 2f);
            y = y + (Tile.TILE_HEIGHT / 2f) - (height / 2f);
        }
    }

    public void pacmanAteMe() {
        eaten = true;
        lastTime = System.currentTimeMillis();
        timer = 0;
    }
}
