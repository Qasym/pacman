package com.Game.gfx;

import java.awt.image.BufferedImage;

/*
* An asset is any image, sound or piece of music in our game
* */
public class Assets {

    /*
    * This subclass holds all the assets needed for Pacman
    * */
    static class PacmanAssets {
        private static boolean initialized = false; //this boolean is needed to check if sprites are initialized or not

        public static final byte UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3; //constants for easy access
        public static BufferedImage[] pacman = new BufferedImage[4]; //this array will store sprites for pacman

        public static void loadPacman(SpriteSheet sheet) {
            initialized = true;
            pacman[PacmanAssets.RIGHT] = sheet.crop(0, 0, 16, 17); //picture of pacman going right
            pacman[PacmanAssets.LEFT] = sheet.crop(19, 0, 16, 17); //picture of pacman going left
            pacman[PacmanAssets.UP] = sheet.crop(0, 18, 17, 16); //picture of pacman going up
            pacman[PacmanAssets.DOWN] = sheet.crop(18, 18, 17, 16); //picture of pacman going down
        }

    }

    public static BufferedImage getPacmanRight() {
        return PacmanAssets.initialized ? PacmanAssets.pacman[PacmanAssets.RIGHT] : null;
    }

    public static BufferedImage getPacmanLeft() {
        return PacmanAssets.initialized ? PacmanAssets.pacman[PacmanAssets.LEFT] : null;
    }

    public static BufferedImage getPacmanUp() {
        return PacmanAssets.initialized ? PacmanAssets.pacman[PacmanAssets.UP] : null;
    }

    public static BufferedImage getPacmanDown() {
        return PacmanAssets.initialized ? PacmanAssets.pacman[PacmanAssets.DOWN] : null;
    }

    /*
    * This method is going to load everything into our game
    * This method is called only once
    * */
    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Pac-Man.png"));
        PacmanAssets.loadPacman(sheet);
    }
}

