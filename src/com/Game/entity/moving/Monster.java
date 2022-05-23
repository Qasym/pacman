package com.Game.entity.moving;

import com.Game.entity.Entity;
import com.Game.gfx.Assets;
import com.Game.intelligence.MonsterBrain;
import com.Game.utils.Handler;

import java.awt.*;

/*
* This class is for the monster logic in Pacman game
* */
public class Monster extends Entity {
    protected float centerX, centerY; // variables that indicate where the center is
    private MonsterBrain brain;

    public Monster(Handler handler, float x, float y, MonsterBrain brain) {
        super(handler, x, y, DEFAULT_ENTITY_WIDTH, DEFAULT_ENTITY_HEIGHT);
        speed = DEFAULT_SPEED + 2;
        centerX = x + (float) DEFAULT_ENTITY_WIDTH / 2;
        centerY = y + (float) DEFAULT_ENTITY_HEIGHT / 2;
        this.brain = brain;
        brain.setMonster(this);
    }

    @Override
    public void tick() {
        // Moving our monster
        brain.decision(); // before moving we have to decide where to move
        move();

        // Updating collisionBox position
        collisionBox.x = (int) (x + DEFAULT_COLLISION_BOUNDS_X);
        collisionBox.y = (int) (y + DEFAULT_COLLISION_BOUNDS_Y);

        // Updating central point, our reference point when deciding movements for this monster
        centerX = x + (float) DEFAULT_ENTITY_WIDTH / 2;
        centerY = y + (float) DEFAULT_ENTITY_HEIGHT / 2;

        // check if monster collided (ate) pacman
        if (collisionBox.intersects(handler.getPacman().getCollisionBox())) {
            handler.getPacman().eatenByMonster();
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
        } else {
            g.drawImage(Assets.getMonsterUp(),
                        (int) (x - handler.getGameCamera().getxOffset()),
                        (int) (y - handler.getGameCamera().getyOffset()), null);
        }
    }

    public void move() {
        if (brain.getCurrentDirection() == brain.UP) {
            y -= speed;
        } else if (brain.getCurrentDirection() == brain.DOWN) {
            y += speed;
        } else if (brain.getCurrentDirection() == brain.LEFT) {
            x -= speed;
        } else if (brain.getCurrentDirection() == brain.RIGHT) {
            x += speed;
        } else {
            new RuntimeException("Unknown direction of a monster").printStackTrace();
            System.exit(-1);
        }
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

}
