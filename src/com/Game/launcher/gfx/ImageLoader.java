package com.Game.launcher.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class ImageLoader {
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(ImageLoader.class.getResource(path)));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(555);
        }
        return null;
    }
}
