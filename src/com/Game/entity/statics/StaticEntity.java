package com.Game.entity.statics;

import com.Game.entity.Entity;
import com.Game.utils.Handler;

/*
* A StaticEntity is an entity that never moves
* like a tree, stone or apples (in pacman case)
* */
public abstract class StaticEntity extends Entity {
    public StaticEntity(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        speed = 0;
    }


}
