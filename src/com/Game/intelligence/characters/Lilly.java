package com.Game.intelligence.characters;

import com.Game.entity.Entity;
import com.Game.entity.moving.Monster;
import com.Game.intelligence.MonsterBrain;
import com.Game.utils.Handler;
import org.jetbrains.annotations.NotNull;

public class Lilly extends MonsterBrain {
    public Lilly(Handler handler, int scatterPosX, int scatterPosY) {
        super(handler, scatterPosX, scatterPosY);
    }

    @Override
    public void decision() {

    }

    @Override
    public void setChaseCoordinates() {

    }
}
