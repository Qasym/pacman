package com.Game.entity.statics.apples;

import com.Game.entity.Entity;
import com.Game.entity.statics.StaticEntity;
import com.Game.gfx.Assets;
import com.Game.utils.Handler;

import java.awt.*;

/*
* Apple class is a class that is responsible for
* giving a score to our player.
* Those yellow balls in the original pacman game
* */
public class Apple extends StaticEntity {
    public Apple(Handler handler, float x, float y) {
        // I want my apples to be 4 times smaller than the pacman
        super(handler, x, y, Entity.DEFAULT_ENTITY_WIDTH / 4, Entity.DEFAULT_ENTITY_HEIGHT / 4);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.getAppleSprite(),
                    (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), null);
    }
}
