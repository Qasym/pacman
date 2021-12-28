package com.Game.launcher;

public class Launcher {
    static Game game;

    public static void main(String[] args) {
        game = new Game("RPG", 1000, 1000);
        game.start();
    }
}
