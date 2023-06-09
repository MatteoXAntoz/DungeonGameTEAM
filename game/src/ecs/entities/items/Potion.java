package ecs.entities.items;

import ecs.entities.Entity;
import ecs.entities.Hero;
import starter.Game;
import tools.Point;

/** class potion to create potion that the player can use */
public class Potion extends Item {

    public Potion() {
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

    public static void INCREASEMAXHEALTH() {
        Hero hero = Game.hero;
        int boost = 4;
        hero.healthComponent.setMaximalHealthpoints(
                hero.healthComponent.getMaximalHealthpoints() + boost);
    }
}
