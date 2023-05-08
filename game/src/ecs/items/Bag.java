package ecs.items;

import ecs.entities.Entity;
import ecs.entities.Hero;
import tools.Point;

import java.util.ArrayList;

public class Bag  extends Item {
   public ArrayList<String> space = new ArrayList<>();


    public Bag(){
        super();
        path = "bag-no_background.png";
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



}
