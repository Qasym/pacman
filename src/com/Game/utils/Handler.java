package com.Game.utils;

import com.Game.gfx.GameCamera;
import com.Game.input.KeyManager;
import com.Game.launcher.Game;
import com.Game.world.World;

public class Handler {
    Game game;
    World world;

    public Handler(Game game) {
        this.game = game;
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
