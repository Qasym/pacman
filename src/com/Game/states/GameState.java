package com.Game.states;

import com.Game.entity.Pacman;

import java.awt.Graphics;

public class GameState extends State {
    private final Pacman pacman;

    public GameState() {
        pacman = new Pacman(0, 0);
    }

    @Override
    public void tick() {
        pacman.tick();
    }

    @Override
    public void render(Graphics g) {
        pacman.render(g);
    }
}
