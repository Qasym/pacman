package com.Game.ui;

import com.Game.utils.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {
    private Handler handler;
    private ArrayList<UIObject> allObjects;

    public UIManager(Handler handler) {
        this.handler = handler;
        allObjects = new ArrayList<UIObject>();
    }

    public void tick() {
        for (UIObject object : allObjects) {
            object.tick();
        }
    }

    public void render(Graphics g) {
        for (UIObject object : allObjects) {
            object.render(g);
        }
    }

    public void onMouseMove(MouseEvent e) {
        for (UIObject object : allObjects) {
            object.onMouseMove(e);
        }
    }

    public void onMouseRelease(MouseEvent e) {
        for (UIObject object : allObjects) {
            object.onMouseRelease(e);
        }
    }

    public void addObject(UIObject object) {
        allObjects.add(object);
    }

    public void removeObject(UIObject object) {
        allObjects.remove(object);
    }
}
