package com.Game.states;

import com.Game.gfx.Assets;

import java.awt.*;

public class GameState extends State {
    public GameState() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.getPacmanRight(), 0, 0, null);
    }
}
