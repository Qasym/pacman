package com.Game.entity.moving;

import com.Game.entity.Entity;
import com.Game.gfx.Animation;
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
    private int score;
    private boolean dead; // to indicate if pacman is dead;

    public Pacman(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_ENTITY_WIDTH, DEFAULT_ENTITY_HEIGHT);

        score = 0;
        dead = false;

        // collisionBox.x & collisionBox.y - are a position of top-left of our collisionBox
        collisionBox.x = (int)(x + DEFAULT_COLLISION_BOUNDS_X);
        collisionBox.y = (int)(y + DEFAULT_COLLISION_BOUNDS_Y);
        collisionBox.width = DEFAULT_COLLISION_BOUNDS_WIDTH;
        collisionBox.height = DEFAULT_COLLISION_BOUNDS_HEIGHT;

        animationDown = new Animation(50, Assets.getPacmanDownAnimation());
        animationUp = new Animation(50, Assets.getPacmanUpAnimation());
        animationLeft = new Animation(50, Assets.getPacmanLeftAnimation());
        animationRight = new Animation(50, Assets.getPacmanRightAnimation());
    }

    private BufferedImage pacmanSprite = null; //pacman sprite that is going to change if we change direction

    // Animations
    private final Animation animationUp, animationDown, animationLeft, animationRight;

    @Override
    public void tick() {
        if (dead) {
            setSpeed(0);
        }

        // Update animations
        animationDown.tick();
        animationLeft.tick();
        animationRight.tick();
        animationUp.tick();

        // Move
        move();

        // Camera
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
        if (pacmanSprite == null) {
            pacmanSprite = animationRight.getCurrentFrame();
        }

        boolean checkingCollisions = Handler.DEBUG; // variable for testing purposes
        if (checkingCollisions) {
            // Temporary code to check collision related stuff;
            g.setColor(Color.BLUE);
            g.drawRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height);
            g.setColor(Color.YELLOW);
            g.fillRect( (int)(collisionBox.x - handler.getGameCamera().getxOffset()),
                    (int)(collisionBox.y - handler.getGameCamera().getyOffset()),
                    collisionBox.width, collisionBox.height);
        } else {
            g.drawImage(pacmanSprite,
                        (int) (x - handler.getGameCamera().getxOffset()),
                        (int) (y - handler.getGameCamera().getyOffset()),
                        width, height, null);
        }
    }

    /*! CHANGEABLE COMMENTS !
    *
    * Initially we check for the state of our key manager (up/down/left/right)
    * Without loss of generality, we assume state is UP
    * We enter the corresponding if statement, and the fun begins
    *
    * -- if statement --
    * We first check the position of our collision box, specifically its top left and right corners
    * because those are the corners that are going to collide with the wall pacman approaches.
    * In order to avoid getting inside the wall we have to check for the collision one step ahead,
    * otherwise (if we check after making a step) we will make a step inside the wall and then understand
    * that we have collided, which is not the desired behavior since it blocks our movement
    * to the left and right (top left & right corners are inside the wall, so we can only move downwards)
    * For that reason, we add the 'speed' (or size of our step) to the position of our points
    * to check for the collision.
    * When we move upwards, there is no change in x-axis, so we don't add speed to the x coordinate
    * We only add speed to the y coordinate and check for the collision ahead, if we collide (or join the wall, so to say)
    * in our next step we simply stop, that will allow our corners to be not inside the wall but rather
    * right before it, so we can move to the left and to the right freely
    *
    * -- else --
    * If statement code is good, but what if our 'speed' (size of our step) is too big?
    * Let's say speed is 30 pixels/keyEvent, and we happen to stop 29 pixels before the wall
    * That means there is a 29 pixel long gap between the collision box and the wall
    * To fix that I count how many pixels are there till the collision happens
    * When I find the value, I subtract(when moving up, y-axis decreases) (tillCollision-1) number of pixels
    * from the top-left corner, and now the collisionBox is right before the wall and not inside it
    * To draw pacman properly I also have to subtract DEFAULT_COLLISION_BOUNDS from the obtained value
    *
    * note: this else statement code can be made faster by using binary search
    *
    * */
    public void move() {
        // if we move upwards, we have to check top left&right corners of collision box
        if (handler.getKeyManager().up) {
            if (!collidesWithTile(collisionBox.x / Tile.WIDTH, // top left corner
                                    (int)(collisionBox.y - speed) / Tile.HEIGHT)
                &&
                !collidesWithTile((collisionBox.x + collisionBox.width) / Tile.WIDTH, // top right corner
                                    (int)(collisionBox.y - speed) / Tile.HEIGHT)) {
                y -= speed;
            } else {
                // if we do have a collision
                int tillCollision = 0;
                for (; tillCollision < speed; tillCollision++) {
                    if (collidesWithTile(collisionBox.x / Tile.WIDTH, // top left corner
                                    (int)(collisionBox.y - tillCollision) / Tile.HEIGHT)
                        || // or
                        collidesWithTile((collisionBox.x + collisionBox.width) / Tile.WIDTH, // top right corner
                                    (int)(collisionBox.y - tillCollision) / Tile.HEIGHT)) {
                        break;
                    }
                }
                y = collisionBox.y - --tillCollision - DEFAULT_COLLISION_BOUNDS_Y;
            }
            pacmanSprite = animationUp.getCurrentFrame();

        //////////////////////////////////////////////////////////////////////////////

        // if we move rightwards, we have to check for top&bottom right corners
        } else if (handler.getKeyManager().right) {
            if (!collidesWithTile((int)(speed + collisionBox.x + collisionBox.width) / Tile.WIDTH, // top right corner
                                    collisionBox.y / Tile.HEIGHT)
                &&
                !collidesWithTile(  (int)(speed + collisionBox.x + collisionBox.width) / Tile.WIDTH, // bottom right corner
                                    (collisionBox.y + collisionBox.height) / Tile.HEIGHT)) {
                x += speed;
            } else {
                // if we do have a collision
                int tillCollision = 0;
                for (; tillCollision < speed; tillCollision++) {
                    if (collidesWithTile((int)(tillCollision + collisionBox.x + collisionBox.width) / Tile.WIDTH, // top right corner
                                    collisionBox.y / Tile.HEIGHT)
                        || //or
                        collidesWithTile(  (int)(tillCollision + collisionBox.x + collisionBox.width) / Tile.WIDTH, // bottom right corner
                                    (collisionBox.y + collisionBox.height) / Tile.HEIGHT)) {
                        break;
                    }
                }
                x = collisionBox.x + --tillCollision - DEFAULT_COLLISION_BOUNDS_X;
            }
            pacmanSprite = animationRight.getCurrentFrame();

        //////////////////////////////////////////////////////////////////////////////

        // if we move leftwards, we have to check for top&bottom left corners
        } else if (handler.getKeyManager().left) {
            if (!collidesWithTile((int)(collisionBox.x - speed) / Tile.WIDTH, // top left corner
                                    collisionBox.y / Tile.HEIGHT)
                &&
                !collidesWithTile(  (int)(collisionBox.x - speed) / Tile.WIDTH, // bottom left corner
                                    (collisionBox.y + collisionBox.height) / Tile.HEIGHT)) {
                x -= speed;
            } else {
                // if we do have a collision
                int tillCollision = 0;
                for (; tillCollision < speed; tillCollision++) {
                    if (collidesWithTile((int)(collisionBox.x - tillCollision) / Tile.WIDTH, // top left corner
                                    collisionBox.y / Tile.HEIGHT)
                        || //or
                        collidesWithTile(  (int)(collisionBox.x - tillCollision) / Tile.WIDTH, // bottom left corner
                                    (collisionBox.y + collisionBox.height) / Tile.HEIGHT)) {
                        break;
                    }
                }
                x = collisionBox.x - --tillCollision - DEFAULT_COLLISION_BOUNDS_X;
            }
            pacmanSprite = animationLeft.getCurrentFrame();

        //////////////////////////////////////////////////////////////////////////////

        // if we move downwards, we have to check for bottom left&right corners
        } else if (handler.getKeyManager().down) {
            if (!collidesWithTile(collisionBox.x / Tile.WIDTH, // bottom left corner
                                    (int)(speed + collisionBox.y + collisionBox.height) / Tile.HEIGHT)
                &&
                !collidesWithTile(  (collisionBox.x + collisionBox.width) / Tile.WIDTH, // bottom right corner
                                    (int)(speed + collisionBox.y + collisionBox.height) / Tile.HEIGHT)) {
                y += speed;
            } else {
                // if we do have a collision
                int tillCollision = 0;
                for (; tillCollision < speed; tillCollision++) {
                    if (collidesWithTile(collisionBox.x / Tile.WIDTH, // bottom left corner
                                            (collisionBox.y + tillCollision + collisionBox.height) / Tile.HEIGHT)
                            ||
                        collidesWithTile((collisionBox.x + collisionBox.width) / Tile.WIDTH, // bottom right corner
                                            (collisionBox.y + tillCollision + collisionBox.height) / Tile.HEIGHT)) {
                        break;
                    }
                }
                y = collisionBox.y + --tillCollision - DEFAULT_COLLISION_BOUNDS_Y;
            }
            pacmanSprite = animationDown.getCurrentFrame();
        }
    }

    public void eatenByMonster() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void updateScore() {
        score++;
    }
}
