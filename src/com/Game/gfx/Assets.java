package com.Game.gfx;

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

        private static final int width = 17, height = 17;

        public static BufferedImage[] pacman = new BufferedImage[4]; //this array will store sprites for pacman
        
        // Arrays to store the animation sprites
        public static BufferedImage[] pacmanUp = new BufferedImage[6];
        public static BufferedImage[] pacmanRight = new BufferedImage[6];
        public static BufferedImage[] pacmanLeft = new BufferedImage[6];
        public static BufferedImage[] pacmanDown = new BufferedImage[6];

        /*
        * In Pac-Man.png file, dimensions of 1 pacman image are */
        public static void loadPacman(SpriteSheet sheet) {
            if (!initialized) {
                initialized = true;
                // Let's crop animations for pacman going right
                pacmanRight[0] = sheet.crop(0, 0, width, height);
                pacmanRight[1] = sheet.crop(0, 34, width, height);
                pacmanRight[2] = sheet.crop(0, 68, width, height);
                pacmanRight[3] = sheet.crop(0, 102, width, height);
                pacmanRight[4] = sheet.crop(0, 68, width, height);
                pacmanRight[5] = sheet.crop(0, 34, width, height);
                // Let's crop animations for pacman going left
                pacmanLeft[0] = sheet.crop(18, 0, width, height);
                pacmanLeft[1] = sheet.crop(18, 34, width, height);
                pacmanLeft[2] = sheet.crop(18, 68, width, height);
                pacmanLeft[3] = sheet.crop(18, 102, width, height);
                pacmanLeft[4] = sheet.crop(18, 68, width, height);
                pacmanLeft[5] = sheet.crop(18, 34, width, height);
                // Let's crop animations for pacman going up
                pacmanUp[0] = sheet.crop(0, 17, width, height);
                pacmanUp[1] = sheet.crop(0, 17 + 34, width, height);
                pacmanUp[2] = sheet.crop(0, 17 + 68, width, height);
                pacmanUp[3] = sheet.crop(0, 17 + 102, width, height);
                pacmanUp[4] = sheet.crop(0, 17 + 68, width, height);
                pacmanUp[5] = sheet.crop(0, 17 + 34, width, height);
                // Let's crop animations for pacman going right
                pacmanDown[0] = sheet.crop(18, 17, width, height);
                pacmanDown[1] = sheet.crop(18, 17 + 34, width, height);
                pacmanDown[2] = sheet.crop(18, 17 + 68, width, height);
                pacmanDown[3] = sheet.crop(18, 17 + 102, width, height);
                pacmanDown[4] = sheet.crop(18, 17 + 68, width, height);
                pacmanDown[5] = sheet.crop(18, 17 + 34, width, height);
            }
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

    public static BufferedImage[] getPacmanUpAnimation() {
        if (!PacmanAssets.initialized) return null;
        return PacmanAssets.pacmanUp;
    }

    public static BufferedImage[] getPacmanDownAnimation() {
        if (!PacmanAssets.initialized) return null;
        return PacmanAssets.pacmanDown;
    }

    public static BufferedImage[] getPacmanLeftAnimation() {
        if (!PacmanAssets.initialized) return null;
        return PacmanAssets.pacmanLeft;
    }

    public static BufferedImage[] getPacmanRightAnimation() {
        if (!PacmanAssets.initialized) return null;
        return PacmanAssets.pacmanRight;
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
        return (background == null) ? null : background.crop(0, 0, 32, 32);
    }

    /*
    * This method is going to load everything into our game
    * This method is called only once
    * */
    public static void init() {
        SpriteSheet pacmanSprites = new SpriteSheet(Utils.loadImage("/textures/entities/Pac-Man.png"));
        SpriteSheet monsterSprites = new SpriteSheet(Utils.loadImage("/textures/entities/Monster.png"));
        wallSprite = new SpriteSheet(Utils.loadImage("/textures/tiles/wall.png"));
        appleSprite = new SpriteSheet(Utils.loadImage("/textures/entities/apple.png"));
        angrySprite = new SpriteSheet(Utils.loadImage("/textures/entities/angry.png"));
        speedSprite = new SpriteSheet(Utils.loadImage("/textures/entities/speed.png"));
        background = new SpriteSheet(Utils.loadImage("/textures/tiles/background.png"));
        PacmanAssets.loadPacman(pacmanSprites);
        MonsterAssets.loadMonster(monsterSprites);
    }
}

