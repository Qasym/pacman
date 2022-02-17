package com.Game.entity;

import com.Game.gfx.Assets;
import com.Game.launcher.Game;
import com.Game.utils.Handler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/*
* This class is for the Pacman logic in Pacman game
* */
public class Pacman extends Entity {
    private boolean powerBuff = false, speedBuff = false; //buffs I would like to add to my Pacman implementation
    private BufferedImage pacmanSprite = Assets.getPacmanRight(); //pacman sprite that is going to change if we change direction

    public Pacman(Handler handler, float x, float y) {
        super(handler, x, y, Entity.DEFAULT_ENTITY_WIDTH, Entity.DEFAULT_ENTITY_HEIGHT);
    }

    @Override
    public void tick() {
        move();
        handler.getGameCamera().centerOnEntity(this);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(pacmanSprite,
                    (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()),
                    width, height, null);
    }

    public void move() {
        if (handler.getKeyManager().up) {
            y -= speed;
            pacmanSprite = Assets.getPacmanUp();
        } else if (handler.getKeyManager().right) {
            x += speed;
            pacmanSprite = Assets.getPacmanRight();
        } else if (handler.getKeyManager().left) {
            x -= speed;
            pacmanSprite = Assets.getPacmanLeft();
        } else if (handler.getKeyManager().down) {
            y += speed;
            pacmanSprite = Assets.getPacmanDown();
        }
    }

}
