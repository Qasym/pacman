package com.Game.states;

import com.Game.gfx.Assets;
import com.Game.launcher.Game;
import com.Game.ui.ImageButton;
import com.Game.ui.UIManager;
import com.Game.utils.Handler;

import java.awt.*;

public class MenuState extends State {
    private final UIManager uiManager;

    public MenuState(Handler handler) {
        super(handler);

        uiManager = new UIManager(handler);
        uiManager.addObject(new ImageButton(300f, 300f, 300, 150, Assets.getPlayButton(), () -> {
            State.setState(handler.getGame().gameState);
        }));

        handler.getMouseManager().setUiManager(uiManager);
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        uiManager.render(g);
    }
}
