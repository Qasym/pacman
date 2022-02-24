package com.Game.states;

import com.Game.launcher.Game;
import com.Game.utils.Handler;

import java.awt.*;

public class MenuState extends State {
    public MenuState(Handler handler) {
        super(handler);
    }

    @Override
    public void tick() {
        System.out.println(handler.getMouseManager().getMouseX()); // delete this line soon, please
    }

    @Override
    public void render(Graphics g) {

    }
}
