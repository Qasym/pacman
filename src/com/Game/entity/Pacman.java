package com.Game.entity;

import com.Game.gfx.Assets;
import com.Game.tile.Tile;
import com.Game.utils.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* This class is for the Pacman logic in Pacman game
* */
public class Pacman extends Entity {
    private boolean powerBuff = false, speedBuff = false; //buffs I would like to add to my Pacman implementation
    private BufferedImage pacmanSprite = Assets.getPacmanRight(); //pacman sprite that is going to change if we change direction

    /*
    * I have to explain about collision bounds
    * Zero is the top left corner of pacman sprite
    * One is the top left corner of collision box
    * Two is the top right corner of collision box
    * Three is the bottom left corner of collision box
    * Four is the bottom right corner of collision box
    * Five is the bottom right corner of pacman sprite
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
    private final int   DEFAULT_COLLISION_BOUNDS_X = 7, // these variables are relative to the pacman sprite
                        DEFAULT_COLLISION_BOUNDS_Y = 7, // meaning that the bounds are from top-left corner of pacman sprite
                        DEFAULT_COLLISION_BOUNDS_WIDTH = 18,
                        DEFAULT_COLLISION_BOUNDS_HEIGHT = 18;

    public Pacman(Handler handler, float x, float y) {
        super(handler, x, y, Entity.DEFAULT_ENTITY_WIDTH, Entity.DEFAULT_ENTITY_HEIGHT);

        collisionBox.x = (int)(x + DEFAULT_COLLISION_BOUNDS_X - handler.getGameCamera().getxOffset());
        collisionBox.y = (int)(y + DEFAULT_COLLISION_BOUNDS_Y - handler.getGameCamera().getyOffset());
        collisionBox.width = DEFAULT_COLLISION_BOUNDS_WIDTH;
        collisionBox.height = DEFAULT_COLLISION_BOUNDS_HEIGHT;
    }

    @Override
    public void tick() {
        move();
        handler.getGameCamera().centerOnEntity(this);

        // We have to update collision box each tick to keep up with the sprite (which is also updated each tick)
        /*
        * Position of collision box and where it is being drawn is actually different values
        * When we draw we assume the coordinate system of a screen, not the entire world coordinates
        * For instance, let's say our world and our screen both originate from [0, 0]
        * When we move pacman left enough, our camera will start to move leftwards
        * What does it mean for the screen and for the world coordinates?
        * The screen coordinate system is always the same, and it is used for rendering purposes
        * But, since our screen !visually! moved to the left one of the coordinate systems must change
        * because we see the different thing. We know that the screen stays constant all the time, so
        * the world coordinate system has to change.
        * Depending on how much we moved to the left, the origin of the world now is located at
        * [xOffset, yOffset]
        *
        * We have to be careful what coordinate we use when dealing with rendering and ticking */
        collisionBox.x = (int)(x + DEFAULT_COLLISION_BOUNDS_X);
        collisionBox.y = (int)(y + DEFAULT_COLLISION_BOUNDS_Y);
    }

    /*
    * There is actually no camera that works as we imagine it.
    * When we create a display with certain size, we actually create a space in which we can draw.
    * We can't move that display, we always look at the same space, though it is not limited to what we see
    * (we can go beyond the screen)
    *
    * What we CAN do is to decide what to draw on the screen
    * Drawing pacman simply by using its position will lead to a situation
    * where pacman is drawn beyond the screen if `x` and `y` are bigger than the boundaries
    * To avoid such situation we have to subtract the camera offset (which is basically points that tell us
    * where we start to draw. For ex. we can draw from positions -100, -100 to see the different parts of the image,
    * if our image is bigger than our display)
    *
    * For a pacman which is bounded to the world origin, not the display origin, it is very crucial to
    * subtract the camera offsets to display the right position of it
    * */
    @Override
    public void render(Graphics g) {
        g.drawImage(pacmanSprite,
                    (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()),
                    width, height, null);

        // Temporary code to display collision box
        g.setColor(Color.RED);
        g.fillRect( (int)(collisionBox.x - handler.getGameCamera().getxOffset()),
                    (int)(collisionBox.y - handler.getGameCamera().getyOffset()),
                    collisionBox.width, collisionBox.height);
    }

    /*! CHANGEABLE COMMENTS !
    *
    * Initially we check for the state of our key manager (up/down/left/right)
    * Without loss of generality, we assume state is up
    * We enter the corresponding if statement, and the fun begins
    *
    * We first check the position of our collision box, specifically its top left and right corners
    * because those are the corners that are going to collide with the wall pacman approaching.
    * In order to avoid getting inside the wall we have to check for the collision one step ahead,
    * otherwise (if we check after making a step) we will make a step inside the wall and then understand
    * that we have collided, which is not the desired behavior since it blocks our movement
    * to the left and right (top left & right corners are inside the wall, so we can only move downwards)
    * For that reason, we add the speed to the position of our points.
    * When we move upwards, there is no change in x-axis, so we don't add speed to the x coordinate
    * We only add speed to the y coordinate and check for the collision ahead, if we collide (or join the wall, so to say)
    * in our next step we simply stop, that will allow our corners to be not inside the wall but rather
    * right before it, so we can move to the left and to the right freely
    * */
    public void move() {
        // todo: make it so pacman doesn't stop movement if a player moves towards side walls
        // for ex. if pacman is in the corridor moving upwards, current implementation will stop
        // pacman when the player tries to move right, towards the wall
        // we need to make it that pacman continues to move upwards
        if (handler.getKeyManager().up) {
            // if we move upwards, we have to check top left&right corners of collision box
            if (!collidesWithTile(collisionBox.x / Tile.TILE_WIDTH, // top left corner
                                    (int)(collisionBox.y - speed) / Tile.TILE_HEIGHT)
                && // or
                !collidesWithTile((collisionBox.x + collisionBox.width) / Tile.TILE_WIDTH, // top right corner
                                    (int)(collisionBox.y - speed) / Tile.TILE_HEIGHT)) {
                y -= speed;
                pacmanSprite = Assets.getPacmanUp();
            }
        } else if (handler.getKeyManager().right) {
            // if we move rightwards, we have to check for top&bottom right corners
            if (!collidesWithTile((int)(speed + collisionBox.x + collisionBox.width) / Tile.TILE_WIDTH, // top right corner
                                    collisionBox.y / Tile.TILE_HEIGHT)
                && //or
                !collidesWithTile(  (int)(speed + collisionBox.x + collisionBox.width) / Tile.TILE_WIDTH, // bottom right corner
                                    (collisionBox.y + collisionBox.height) / Tile.TILE_HEIGHT)) {
                x += speed;
                pacmanSprite = Assets.getPacmanRight();
            }
        } else if (handler.getKeyManager().left) {
            // if we move leftwards, we have to check for top&bottom left corners
            if (!collidesWithTile((int)(collisionBox.x - speed) / Tile.TILE_WIDTH, // top left corner
                                    collisionBox.y / Tile.TILE_HEIGHT)
                && //or
                !collidesWithTile(  (int)(collisionBox.x - speed) / Tile.TILE_WIDTH, // bottom left corner
                                    (collisionBox.y + collisionBox.height) / Tile.TILE_HEIGHT)) {
                x -= speed;
                pacmanSprite = Assets.getPacmanLeft();
            }
        } else if (handler.getKeyManager().down) {
            // if we move downwards, we have to check for bottom left&right corners
            /*speed +*/
            if (!collidesWithTile(collisionBox.x / Tile.TILE_WIDTH, // bottom left corner
                                    (int)(speed + collisionBox.y + collisionBox.height) / Tile.TILE_HEIGHT)
                && //or
                !collidesWithTile(  (collisionBox.x + collisionBox.width) / Tile.TILE_WIDTH, // bottom right corner
                                    (int)(speed + collisionBox.y + collisionBox.height) / Tile.TILE_HEIGHT)) {
                y += speed;
                pacmanSprite = Assets.getPacmanDown();
            }
        }
    }

    // This method returns true if a tile at [x, y] is solid
    private boolean collidesWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }

}
