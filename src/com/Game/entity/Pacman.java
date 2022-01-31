package com.Game.entity;

import com.Game.gfx.Assets;
import com.Game.launcher.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/*
* This class is for the Pacman logic in Pacman game
* */
public class Pacman extends Entity {
    private boolean powerBuff = false, speedBuff = false; //buffs I would like to add to my Pacman implementation
    private BufferedImage pacmanSprite = Assets.getPacmanRight(); //pacman sprite that is going to change if we change direction

    public Pacman(Game game, float x, float y) {
        super(game, x, y, Entity.DEFAULT_ENTITY_WIDTH, Entity.DEFAULT_ENTITY_HEIGHT);
    }

    @Override
    public void tick() {
        move();
        game.getGameCamera().centerOnEntity(this);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(pacmanSprite,
                    (int) (x - game.getGameCamera().getxOffset()),
                    (int) (y - game.getGameCamera().getyOffset()),
                    width, height, null);
    }

    public void move() {
        if (game.getKeyManager().up) {
            y -= speed;
            pacmanSprite = Assets.getPacmanUp();
        } else if (game.getKeyManager().right) {
            x += speed;
            pacmanSprite = Assets.getPacmanRight();
        } else if (game.getKeyManager().left) {
            x -= speed;
            pacmanSprite = Assets.getPacmanLeft();
        } else if (game.getKeyManager().down) {
            y += speed;
            pacmanSprite = Assets.getPacmanDown();
        }
    }

}
