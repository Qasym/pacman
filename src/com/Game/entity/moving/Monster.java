package com.Game.entity.moving;

import com.Game.entity.Entity;
import com.Game.gfx.Assets;
import com.Game.utils.Handler;

import java.awt.*;

/*
* This class is for the monster logic in Pacman game
* */
public class Monster extends Entity {
    public Monster(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_ENTITY_WIDTH, DEFAULT_ENTITY_HEIGHT);
    }

    @Override
    public void tick() {
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
        } else {
            g.drawImage(Assets.getMonsterUp(),
                        (int) (x - handler.getGameCamera().getxOffset()),
                        (int) (y - handler.getGameCamera().getyOffset()), null);
        }
    }
}
