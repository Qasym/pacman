package com.Game.gfx;

import com.Game.tile.Tile;
import com.Game.utils.Utils;

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

    /*
     * This subclass holds all the assets needed for Monster
     * */
    static class MonsterAssets {
        private static boolean initialized = false; //this boolean is needed to check if sprites are initialized or not

        public static final byte UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3; //constants for easy access
        public static BufferedImage[] monster = new BufferedImage[4]; //this array will store sprites for pacman

        public static void loadMonster(SpriteSheet sheet) {
            initialized = true;
            monster[MonsterAssets.RIGHT] = sheet.crop(16, 0, 16, 25); //picture of pacman going right
            monster[MonsterAssets.LEFT] = sheet.crop(0, 0, 16, 25); //picture of pacman going left
            monster[MonsterAssets.UP] = sheet.crop(0, 25, 16, 25); //picture of pacman going up
            monster[MonsterAssets.DOWN] = sheet.crop(16, 25, 16, 25); //picture of pacman going down
        }
    }

    public static BufferedImage getMonsterRight() {
        return MonsterAssets.initialized ? MonsterAssets.monster[MonsterAssets.RIGHT] : null;
    }

    public static BufferedImage getMonsterLeft() {
        return MonsterAssets.initialized ? MonsterAssets.monster[MonsterAssets.LEFT] : null;
    }

    public static BufferedImage getMonsterUp() {
        return MonsterAssets.initialized ? MonsterAssets.monster[MonsterAssets.UP] : null;
    }

    public static BufferedImage getMonsterDown() {
        return MonsterAssets.initialized ? MonsterAssets.monster[MonsterAssets.DOWN] : null;
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

    //holds the wall from wall.png; the background;the apple; for the angryBuff; for the speedBuff;
    static SpriteSheet wallSprite, background, appleSprite, angrySprite, speedSprite; //the latter two are buffed apples

    public static BufferedImage getWall() {
        return (wallSprite == null) ? null : wallSprite.crop(0, 0, 20, 16); //tile width=20, height=16
    }

    public static BufferedImage getAppleSprite() {
        return (appleSprite == null) ? null : appleSprite.crop(0, 0, 13, 13);
    }

    public static BufferedImage getAngrySprite() {
        return (angrySprite == null) ? null : angrySprite.crop(0, 0, 13, 13);
    }

    public static BufferedImage getSpeedSprite() {
        return (speedSprite == null) ? null : speedSprite.crop(0, 0, 13, 13);
    }

    public static BufferedImage getBackgroundSprite() {
        return (background == null) ? null : background.crop(0, 0, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
    }

    /*
    * This method is going to load everything into our game
    * This method is called only once
    * */
    public static void init() {
        SpriteSheet pacmanSprites = new SpriteSheet(Utils.loadImage("/textures/entities/Pac-Man.png"));
        SpriteSheet monsterSprites = new SpriteSheet(Utils.loadImage("/textures/entities/Monster.png"));
        wallSprite = new SpriteSheet(Utils.loadImage("/textures/tiles/wall.png"));
        appleSprite = new SpriteSheet(Utils.loadImage("/textures/tiles/apple.png"));
        angrySprite = new SpriteSheet(Utils.loadImage("/textures/tiles/angry.png"));
        speedSprite = new SpriteSheet(Utils.loadImage("/textures/tiles/speed.png"));
        background = new SpriteSheet(Utils.loadImage("/textures/tiles/background.png"));
        PacmanAssets.loadPacman(pacmanSprites);
        MonsterAssets.loadMonster(monsterSprites);
    }
}

