package com.Game.utils;

import com.Game.gfx.GameCamera;
import com.Game.input.KeyManager;
import com.Game.input.MouseManager;
import com.Game.launcher.Game;
import com.Game.world.World;

/*
* Handler class is a simple class that holds all the necessary
* objects, and sole purpose of this class is to ease the access to those
* */
public class Handler {
    Game game;
    World world;

    public static boolean DEBUG = false;

    public Handler(Game game) {
        this.game = game;
    }

    public int getMouseY() {
        return game.getMouseManager().getMouseY();
    }

    public int getMouseX() {
        return game.getMouseManager().getMouseX();
    }

    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public int getGameHeight() {
        return game.getHeight();
    }

    public int getGameWidth() {
        return game.getWidth();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Game getGame() {
        return game;
    }

    public World getWorld() {
        return world;
    }
}
