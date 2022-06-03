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
    private final MonsterBrain brain;

    public Monster(Handler handler, float x, float y, MonsterBrain brain) {
        super(handler, x, y, DEFAULT_ENTITY_WIDTH, DEFAULT_ENTITY_HEIGHT);
        speed = DEFAULT_SPEED;
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

        // Updating collisionBox position
        collisionBox.x = (int) (x + DEFAULT_COLLISION_BOUNDS_X);
        collisionBox.y = (int) (y + DEFAULT_COLLISION_BOUNDS_Y);

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
            g.setColor(Color.BLUE);
            g.fillOval((int) (brain.getChasePosX() - handler.getGameCamera().getxOffset()),
                       (int) (brain.getChasePosY() - handler.getGameCamera().getyOffset()),
                       15, 15);
        } else {
            g.drawImage(Assets.getMonsterUp(),
                        (int) (x - handler.getGameCamera().getxOffset()),
                        (int) (y - handler.getGameCamera().getyOffset()), null);
        }
    }

    private void move() {
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
}
