package com.Game.entity;

import com.Game.entity.moving.Pacman;
import com.Game.utils.Handler;

import java.awt.*;
import java.util.ArrayList;

/*
* EntityManager is class that has a self-descriptive name
* It manages all the entities I have in a pacman game
* */
public class EntityManager {
    private Handler handler;
    private Pacman pacman;
    private ArrayList<Entity> entities;

    public EntityManager(Handler handler, Pacman pacman) {
        this.handler = handler;
        this.pacman = pacman;
        entities = new ArrayList<>(31 * 28); // 31 * 28 because of the world dimensions
    }

    public void tick() {
        Entity entity;
        for (int i = 0; i < entities.size(); i++) {
            entity = entities.get(i);
            entity.tick();
        }
        pacman.tick();
    }

    public void render(Graphics g) {
        Entity entity;
        for (int i = 0; i < entities.size(); i++) {
            entity = entities.get(i);
            entity.render(g);
        }
        pacman.render(g);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public Handler getHandler() {
        return handler;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
