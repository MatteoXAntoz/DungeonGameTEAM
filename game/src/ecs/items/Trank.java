package ecs.items;

import ecs.components.HealthComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.LevelAPI;
import starter.Game;
import tools.Point;

public class Trank extends Item {

    public Trank() {
        super();
        name = "Trank";
        path = "potion_blue_no_background.png";
        setupAnimation();
        setupPositionComponent();

    }

    public Entity getEntity() {
        return positionComponent.getEntity();
    }

    public Point getPosition() {
        return positionComponent.getPosition();
    }

    public void setPosition(Point position) {
        positionComponent.setPosition(position);
    }

    public static void SETGODMODE() {
        Hero hero = Game.hero;
        System.out.println("GodMode ist an");
        hero.setGodMode(true);

}


}