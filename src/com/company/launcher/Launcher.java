package com.company.launcher;

import com.company.display.Display;

public class Launcher {
    static Game game;

    public static void main(String[] args) {
        game = new Game("RPG", 400, 400);
        game.start();
    }
}
