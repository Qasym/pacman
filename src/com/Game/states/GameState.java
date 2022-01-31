package com.Game.states;

import com.Game.entity.Pacman;
import com.Game.launcher.Game;
import com.Game.tile.Tile;
import com.Game.world.World;

import java.awt.Graphics;

public class GameState extends State {
    private final Pacman pacman;
    private World world;

    public GameState(Game game) {
        super(game);
        pacman = new Pacman(game,0, 0);

        world = new World("");
    }

    @Override
    public void tick() {
        world.tick();
        pacman.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        pacman.render(g);
    }
}
