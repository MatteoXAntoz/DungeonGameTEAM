package ecs.items;

import ecs.components.MyInventory;
import ecs.entities.Entity;
import ecs.entities.Hero;
import tools.Point;

import java.util.ArrayList;


/**
 * Bag superclass is for subclasses: FoodBag and PotionBag
 */

public class Bag  extends Item {
   private MyInventory myInventory;



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

    protected void setupInventory(){
        myInventory = new MyInventory();
    }

    public MyInventory getMyInventory() {
        return myInventory;
    }

    public void setMyInventory(MyInventory myInventory) {
        this.myInventory = myInventory;
    }
}
