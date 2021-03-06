package com.Game.utils;

import com.Game.entity.moving.Monster;
import com.Game.entity.moving.Pacman;
import com.Game.gfx.GameCamera;
import com.Game.input.KeyManager;
import com.Game.input.MouseManager;
import com.Game.launcher.Game;
import com.Game.world.World;

import java.util.ArrayList;

/*
* Handler class is a simple class that holds all the necessary
* objects, and sole purpose of this class is to ease the access to those
* */
public class Handler {
    protected Game game;
    protected World world;

    public static boolean DEBUG = false;

    public Handler(Game game) {
        this.game = game;
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

    public Pacman getPacman() {
        return world.getEntityManager().getPacman();
    }

    public ArrayList<Monster> getMonsters() {
        return world.getEntityManager().getMonsters();
    }
}
