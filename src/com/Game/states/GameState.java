package com.Game.states;

import com.Game.gfx.Assets;
import com.Game.intelligence.MonsterBrain;
import com.Game.ui.ImageButton;
import com.Game.ui.UIManager;
import com.Game.utils.Handler;
import com.Game.utils.Utils;
import com.Game.world.World;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class GameState extends State {
    private World world;
    private final UIManager uiManager;

    public GameState(Handler handler) {
        super(handler);
        // initializing the monster AI
        initializeAI();

        // initializing the world
        world = new World(handler,"res/worlds/base_world");
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

    /*
    * Every value at even index i is the x coordinate and
    * i+1 is the corresponding y coordinate of a turning point
    *
    * Turning point is a tile at which monster can turn
    * */
    private void initializeAI() {
        String[] file = Utils.loadFileAsString("res/worlds/base_world_turns.txt").split("\\s+");
        Point[] points = new Point[file.length / 2];
        for (int i = 0, j = 0; i < file.length; i += 2) {
            points[j++] = new Point(Utils.parseInt(file[i]), Utils.parseInt(file[i + 1]));
        }
        MonsterBrain.setTurningPoints(points);
    }

    public UIManager getUiManager() {
        return uiManager;
    }
}
