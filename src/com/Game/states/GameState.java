package com.Game.states;

import com.Game.entity.moving.Pacman;
import com.Game.tile.Tile;
import com.Game.utils.Handler;
import com.Game.world.World;

import java.awt.Graphics;

public class GameState extends State {
//    private final Pacman pacman;
    private final World world;

    public GameState(Handler handler) {
        super(handler);
        world = new World(handler,"res/worlds/base_world"); // initializing the world
        handler.setWorld(world);
    }

    @Override
    public void tick() {
        world.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
    }
}
