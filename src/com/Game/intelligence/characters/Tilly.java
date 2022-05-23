package com.Game.intelligence.characters;

import com.Game.entity.Entity;
import com.Game.entity.moving.Monster;
import com.Game.intelligence.MonsterBrain;
import com.Game.utils.Handler;
import org.jetbrains.annotations.NotNull;

public class Tilly extends MonsterBrain {
    public Tilly(Handler handler, int scatterPosX, int scatterPosY) {
        super(handler, scatterPosX, scatterPosY);
    }

    @Override
    public void decision() {

    }

    @Override
    public void setChaseCoordinates() {

    }
}
