package com.Game.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageButton extends UIObject {
    protected BufferedImage[] images;
    protected ClickListener clickListener;

    public ImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clickListener) {
        super(x, y, width, height);
        this.images = images;
        this.clickListener = clickListener;
    }

    @Override
    public void tick() {}

    @Override
    public void render(Graphics g) {
        if (hovering) {
            g.drawImage(images[1], (int) x ,(int) y, width, height, null);
        } else {
            g.drawImage(images[0], (int) x, (int) y, width, height, null);
        }
    }

    @Override
    public void onClick() {
        clickListener.onClick();
    }
}
