package com.Game.entity;

import com.Game.gfx.Assets;

import java.awt.*;

public class Pacman extends Entity {
    private boolean powerBuff = false, speedBuff = false;

    public Pacman(float x, float y) {
        super(x, y);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.getPacmanRight(), (int) x, (int) x, null);
    }
}
