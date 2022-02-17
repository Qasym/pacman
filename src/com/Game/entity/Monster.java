package com.Game.entity;

import com.Game.launcher.Game;
import com.Game.utils.Handler;

import java.awt.*;

/*
* This class is for the monster logic in Pacman game
* */
public class Monster extends Entity {
    public Monster(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, Entity.DEFAULT_ENTITY_WIDTH, Entity.DEFAULT_ENTITY_HEIGHT);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }
}
