package com.Game.states;

import com.Game.entity.Pacman;
import com.Game.launcher.Game;

import java.awt.Graphics;

public class GameState extends State {
    private final Pacman pacman;

    public GameState(Game game) {
        super(game);
        pacman = new Pacman(game,0, 0);
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
