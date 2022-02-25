package com.Game.states;

import com.Game.entity.moving.Pacman;
import com.Game.gfx.Assets;
import com.Game.tile.Tile;
import com.Game.ui.ImageButton;
import com.Game.ui.UIManager;
import com.Game.utils.Handler;
import com.Game.world.World;

import java.awt.Graphics;

public class GameState extends State {
    private World world;
    private final UIManager uiManager;

    public GameState(Handler handler) {
        super(handler);
        world = new World(handler,"res/worlds/base_world"); // initializing the world
        handler.setWorld(world);

        // initializing UI for when the game finishes
        uiManager = new UIManager(handler);
        uiManager.addObject(new ImageButton(300f, 300f, 300, 150, Assets.getReplayButton(), () -> {
            world = new World(handler, "res/worlds/base_world"); // restarting;
            handler.setWorld(world);
        }));
        uiManager.addObject(new ImageButton(300f, 600f, 300, 150, Assets.getExitButton(), () -> {
            System.exit(0);
        }));
    }

    @Override
    public void tick() {
        world.tick();
        if (handler.getWorld().getEntityManager().getPacman().isDead()) {
            handler.getMouseManager().setUiManager(uiManager);
            uiManager.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        if (handler.getWorld().getEntityManager().getPacman().isDead()) {
            uiManager.render(g);
        }
    }
}
