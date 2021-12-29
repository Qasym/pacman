package com.Game.gfx;

import java.awt.image.BufferedImage;

/*
* This class is needed for loading the sprites
*
* Sprite-sheet is a png file which contains multiple
* png files within, so when we load one png file, we actually
* load other png files as well, one shot - multi-kill*/
public class SpriteSheet {
    private BufferedImage sheet;

    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    public BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}
