package com.Game.gfx;

import com.Game.audio.AudioManager;
import com.Game.ui.Text;
import com.Game.utils.Utils;

import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

// An asset is any image, sound or piece of music in our game
public class Assets {
    // Font
    public static Font font;

    // This subclass holds all the assets needed for Pacman
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


    // This subclass holds all the assets needed for Monster
    static class MonsterAssets {
        private static boolean initialized = false; //this boolean is needed to check if sprites are initialized or not

        public static final byte UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3; //constants for easy access
        public static BufferedImage[] monster = new BufferedImage[4]; //this array will store sprites for monsters
        public static BufferedImage[] monsterFrightened = new BufferedImage[4]; // sprites for frightened monsters
        public static BufferedImage[] monsterEaten = new BufferedImage[4]; // sprites for eaten monsters

        public static void loadMonster(SpriteSheet sheet) {
            initialized = true;
            monster[MonsterAssets.RIGHT] = sheet.crop(32, 0, 32, 50); //picture of monster going right
            monster[MonsterAssets.LEFT] = sheet.crop(0, 0, 32, 50); //picture of monster going left
            monster[MonsterAssets.UP] = sheet.crop(0, 50, 32, 50); //picture of monster going up
            monster[MonsterAssets.DOWN] = sheet.crop(32, 50, 32, 50); //picture of monster going down
        }

        public static void loadMonsterFrightened(SpriteSheet sheet) {
            monsterFrightened[MonsterAssets.RIGHT] = sheet.crop(32, 0, 32, 50); //picture of monster going right
            monsterFrightened[MonsterAssets.LEFT] = sheet.crop(0, 0, 32, 50); //picture of monster going left
            monsterFrightened[MonsterAssets.UP] = sheet.crop(0, 50, 32, 50); //picture of monster going up
            monsterFrightened[MonsterAssets.DOWN] = sheet.crop(32, 50, 32, 50); //picture of monster going down
        }

        public static void loadMonsterEaten(SpriteSheet sheet) {
            monsterEaten[MonsterAssets.RIGHT] = sheet.crop(32, 0, 32, 50); //picture of monster going right
            monsterEaten[MonsterAssets.LEFT] = sheet.crop(0, 0, 32, 50); //picture of monster going left
            monsterEaten[MonsterAssets.UP] = sheet.crop(0, 50, 32, 50); //picture of monster going up
            monsterEaten[MonsterAssets.DOWN] = sheet.crop(32, 50, 32, 50); //picture of monster going down
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

    public static BufferedImage getMonsterFrightenedRight() {
        return MonsterAssets.initialized ? MonsterAssets.monsterFrightened[MonsterAssets.RIGHT] : null;
    }

    public static BufferedImage getMonsterFrightenedLeft() {
        return MonsterAssets.initialized ? MonsterAssets.monsterFrightened[MonsterAssets.LEFT] : null;
    }

    public static BufferedImage getMonsterFrightenedUp() {
        return MonsterAssets.initialized ? MonsterAssets.monsterFrightened[MonsterAssets.UP] : null;
    }

    public static BufferedImage getMonsterFrightenedDown() {
        return MonsterAssets.initialized ? MonsterAssets.monsterFrightened[MonsterAssets.DOWN] : null;
    }

    public static BufferedImage getMonsterEatenRight() {
        return MonsterAssets.initialized ? MonsterAssets.monsterEaten[MonsterAssets.RIGHT] : null;
    }

    public static BufferedImage getMonsterEatenLeft() {
        return MonsterAssets.initialized ? MonsterAssets.monsterEaten[MonsterAssets.LEFT] : null;
    }

    public static BufferedImage getMonsterEatenUp() {
        return MonsterAssets.initialized ? MonsterAssets.monsterEaten[MonsterAssets.UP] : null;
    }

    public static BufferedImage getMonsterEatenDown() {
        return MonsterAssets.initialized ? MonsterAssets.monsterEaten[MonsterAssets.DOWN] : null;
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

    private static SpriteSheet[] tileSprites;
    private static SpriteSheet[] entitySprites;
    private static BufferedImage[] playButton, musicButton, entityCollisionsButton; // it is put into a separate array because it has multiple sprites
    private static BufferedImage[] uiSprites;

    public static BufferedImage[] getPlayButton() {
        // UI elements' sprites for menu state
        if (playButton == null) {
            System.exit(102);
        }
        return playButton;
    }

    public static BufferedImage[] getMusicButton() {
        if (musicButton == null) {
            System.exit(102);
        }
        return musicButton;
    }

    public static BufferedImage[] getEntityCollisionsButton() {
        if (entityCollisionsButton == null) {
            System.exit(102);
        }
        return entityCollisionsButton;
    }

    public static BufferedImage getBackButton() {
        if (uiSprites == null) {
            System.exit(102);
        }
        return uiSprites[3];
    }

    public static BufferedImage getOptionsButton() {
        if (uiSprites == null) {
            System.exit(102);
        }
        return uiSprites[0];
    }

    public static BufferedImage getReplayButton() {
        if (uiSprites == null) {
            System.exit(102);
        }
        return uiSprites[1];
    }


    public static BufferedImage getExitButton() {
        if (uiSprites == null) {
            System.exit(102);
        }
        return uiSprites[2];
    }

    public static Font getFont(){
        return font;
    }

    public static BufferedImage getWall() {
        return (tileSprites[0] == null) ? null : tileSprites[0].crop(0, 0, 20, 16); //tile width=20, height=16
    }

    public static BufferedImage getAppleSprite() {
        return (entitySprites[2] == null) ? null : entitySprites[2].crop(0, 0, 13, 13);
    }

    public static BufferedImage getAngrySprite() {
        return (entitySprites[3] == null) ? null : entitySprites[3].crop(0, 0, 13, 13);
    }

    public static BufferedImage getSpeedSprite() {
        return (entitySprites[4] == null) ? null : entitySprites[4].crop(0, 0, 13, 13);
    }

    public static BufferedImage getBackgroundSprite() {
        return (tileSprites[1] == null) ? null : tileSprites[1].crop(0, 0, 32, 32);
    }

    /*
    * This method is going to load everything into our game
    * This method is called only once
    * */
    public static void init() {
        // Fonts
        font = Text.loadFont("/fonts/game_font.ttf", 32);

        // Entities' sprites
        entitySprites = new SpriteSheet[7];

        // pacman sprite
        entitySprites[0] = new SpriteSheet(Utils.loadImage("/textures/entities/Pac-Man.png"));
        PacmanAssets.loadPacman(entitySprites[0]);

        // monster sprite
        entitySprites[1] = new SpriteSheet(Utils.loadImage("/textures/entities/Monster.png"));
        MonsterAssets.loadMonster(entitySprites[1]);

        // apple sprite, angry sprite, speed sprite in respective orders
        entitySprites[2] = new SpriteSheet(Utils.loadImage("/textures/entities/apple.png"));
        entitySprites[3] = new SpriteSheet(Utils.loadImage("/textures/entities/angry.png"));
        entitySprites[4] = new SpriteSheet(Utils.loadImage("/textures/entities/speed.png"));

        // monster frightened sprite
        entitySprites[5] = new SpriteSheet(Utils.loadImage("/textures/entities/Monster_frightened.png"));
        MonsterAssets.loadMonsterFrightened(entitySprites[5]);

        // monster eaten sprite
        entitySprites[6] = new SpriteSheet(Utils.loadImage("/textures/entities/Monster_eaten.png"));
        MonsterAssets.loadMonsterEaten(entitySprites[6]);

        // Tile sprites
        tileSprites = new SpriteSheet[2];
        tileSprites[0] = new SpriteSheet(Utils.loadImage("/textures/tiles/wall.png"));
        tileSprites[1] = new SpriteSheet(Utils.loadImage("/textures/tiles/background.png"));

        // UI element sprites
        playButton = new BufferedImage[2];
        playButton[0] = Utils.loadImage("/textures/ui_elements/button_play_not_selected.png");
        playButton[1] = Utils.loadImage("/textures/ui_elements/button_play_selected.png");

        uiSprites = new BufferedImage[4];
        uiSprites[0] = Utils.loadImage("/textures/ui_elements/button_options.png");
        uiSprites[1] = Utils.loadImage("/textures/ui_elements/button_replay.png");
        uiSprites[2] = Utils.loadImage("/textures/ui_elements/button_exit.png");
        uiSprites[3] = Utils.loadImage("/textures/ui_elements/button_back.png");

        musicButton = new BufferedImage[2];
        SpriteSheet musicButtonSprites = new SpriteSheet(Utils.loadImage("/textures/ui_elements/button_music_on_off.png"));
        musicButton[0] = musicButtonSprites.crop(0, 0, 200, 50); // music on
        musicButton[1] = musicButtonSprites.crop(0, 50, 200, 50); // music off

        entityCollisionsButton = new BufferedImage[2];
        SpriteSheet collisionButtonSprites = new SpriteSheet(Utils.loadImage("/textures/ui_elements/button_hideShow_colBox.png"));
        entityCollisionsButton[0] = collisionButtonSprites.crop(0, 0, 200, 50); // show
        entityCollisionsButton[1] = collisionButtonSprites.crop(0, 50, 200, 50); // hide

        // Initializing sounds
        try {
            String musicPath = "/sounds/Fireplace.wav";
            AudioManager.addMusic(AudioSystem.getAudioInputStream(Objects.requireNonNull(Utils.getResourceAsFile(musicPath))
                                                                         .getAbsoluteFile()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(103);
        }
    }
}

