package com.Game.states;

import com.Game.entity.Entity;
import com.Game.entity.Pacman;
import com.Game.launcher.Game;
import com.Game.tile.Tile;
import com.Game.utils.Handler;
import com.Game.world.World;

import java.awt.Graphics;

public class GameState extends State {
    private final Pacman pacman;
    private final World world;

    public GameState(Handler handler) {
        super(handler);
        world = new World(handler,"res/worlds/base_world"); // initializing the world
        handler.setWorld(world);

        pacman = new Pacman(handler,
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
