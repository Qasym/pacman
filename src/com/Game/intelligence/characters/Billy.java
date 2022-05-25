package com.Game.intelligence.characters;

import com.Game.entity.Entity;
import com.Game.entity.moving.Monster;
import com.Game.intelligence.MonsterBrain;
import com.Game.utils.Handler;
import org.jetbrains.annotations.NotNull;

/*
* Billy chases Pacman at exactly his location
* just like a shadow that is always with us in a sunny day
* */
public class Billy extends MonsterBrain {
    public Billy(Handler handler, int scatterPosX, int scatterPosY) {
        super(handler, scatterPosX, scatterPosY);
    }

    @Override
    public void setChaseCoordinates() {
        chasePosX = (int) handler.getPacman().getX();
        chasePosY = (int) handler.getPacman().getY();
    }

    public int getBillyPositionX() {
        return monster.getCollisionBox().x;
    }

    public int getBillyPositionY() {
        return monster.getCollisionBox().y;
    }
}
