package com.Game.launcher;

public class Launcher {
    static Game game;

    public static void main(String[] args) {
        game = new Game("PacMan", 896, 992);
        game.start();
    }
}
