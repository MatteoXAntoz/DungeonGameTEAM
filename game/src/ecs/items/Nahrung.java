package ecs.items;

import ecs.entities.Entity;
import tools.Point;

public class Nahrung extends Item{
    public Entity getEntity() {
        return positionComponent.getEntity();
    }

    public Point getPosition() {
        return positionComponent.getPosition();
    }

    public void setPosition(Point position) {
        positionComponent.setPosition(position);
    }
}
