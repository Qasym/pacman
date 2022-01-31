package com.Game.states;

import com.Game.entity.Entity;
import com.Game.entity.Pacman;
import com.Game.launcher.Game;
import com.Game.tile.Tile;
import com.Game.world.World;

import java.awt.Graphics;

public class GameState extends State {
    private final Pacman pacman;
    private final World world;

    public GameState(Game game) {
        super(game);
        world = new World(game,"res/worlds/base_world"); // initializing the world
        pacman = new Pacman(game,
                        world.getPlayerX() * Entity.DEFAULT_ENTITY_HEIGHT,
                        world.getPlayerY() * Entity.DEFAULT_ENTITY_WIDTH); // initializing pacman at his spawn-point
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
