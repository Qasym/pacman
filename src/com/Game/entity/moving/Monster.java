package com.Game.entity.moving;

import com.Game.entity.Entity;
import com.Game.gfx.Assets;
import com.Game.intelligence.MonsterBrain;
import com.Game.tile.Tile;
import com.Game.utils.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* This class is for the monster logic in Pacman game
*
* This class deals with the "physical" aspects of the monster
* */
public class Monster extends Entity {
    private final MonsterBrain brain;
    public final static int SLOWDOWN = DEFAULT_SPEED - 3;
    private BufferedImage sprite = Assets.getMonsterRight();

    public Monster(Handler handler, float x, float y, MonsterBrain brain) {
        super(handler, x, y, DEFAULT_ENTITY_WIDTH, DEFAULT_ENTITY_HEIGHT);
        this.brain = brain;
        brain.setMonster(this);
    }

    @Override
    public void tick() {
        if (handler.getPacman().isDead()) {
            return;
        }
        // Moving our monster
        brain.decide(); // before moving we have to decide where to move
        move();

        // Teleport if we reach portal
        teleportAtPortal(); // automatically teleports

        // Updating collisionBox position
        collisionBox.x = (int) (x + DEFAULT_COLLISION_BOUNDS_X);
        collisionBox.y = (int) (y + DEFAULT_COLLISION_BOUNDS_Y);

        // check if monster collided (ate) pacman
        if (collisionBox.intersects(handler.getPacman().getCollisionBox()) ||
            (handler.getPacman().getCollisionBox().x / Tile.WIDTH == collisionBox.x / Tile.WIDTH &&
            handler.getPacman().getCollisionBox().y / Tile.HEIGHT == collisionBox.y / Tile.HEIGHT)) {
            handler.getPacman().eatenByMonster(this);
        }
    }

    @Override
    public void render(Graphics g) {
        boolean checkingCollisions = Handler.DEBUG;
        if (checkingCollisions) {
            // if we are checking for collisions, then display rectangles instead of sprites
            g.setColor(Color.GREEN);
            g.drawRect((int) (x - handler.getGameCamera().getxOffset()),
                       (int) (y - handler.getGameCamera().getyOffset()), width, height);
            g.setColor(Color.BLACK);
            g.fillRect((int) (collisionBox.x - handler.getGameCamera().getxOffset()),
                       (int) (collisionBox.y - handler.getGameCamera().getyOffset()),
                       collisionBox.width, collisionBox.height);
            g.setColor(Color.BLUE);
            g.fillOval((int) (brain.getChasePosX() - handler.getGameCamera().getxOffset()),
                       (int) (brain.getChasePosY() - handler.getGameCamera().getyOffset()),
                       15, 15);
        } else {
            g.drawImage(sprite,
                        (int) (x - handler.getGameCamera().getxOffset()),
                        (int) (y - handler.getGameCamera().getyOffset()), null);
        }
    }

    private void move() {
        sprite = brain.getCurrentSprite();
        if (brain.getCurrentDirection() == MonsterBrain.UP) {
            y -= speed;
        } else if (brain.getCurrentDirection() == MonsterBrain.DOWN) {
            y += speed;
        } else if (brain.getCurrentDirection() == MonsterBrain.LEFT) {
            x -= speed;
        } else if (brain.getCurrentDirection() == MonsterBrain.RIGHT) {
            x += speed;
        } else {
            System.out.println("Unknown direction of a monster");
        }
    }

    public MonsterBrain getBrain() {
        return brain;
    }

    public String getName() {
        return brain.getName();
    }

    public void slowMeDown() {
        speed = SLOWDOWN;
    }

    public void resetSpeed() {
        speed = DEFAULT_SPEED;
    }
}
