package ecs.items;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import starter.Game;
import tools.Point;

import javax.swing.*;
import java.util.Map;

public class Item extends Entity  {

    public PositionComponent positionComponent;
    protected String path;
    public String name;


    Animation animation;

    public Item() {
        super();

    }





    protected void setupAnimation() {
        animation = AnimationBuilder.buildAnimation(path);
        new AnimationComponent(this, animation);
    }

    protected void setupPositionComponent() {
        positionComponent = new PositionComponent(this);
    }

    public static Item ranItem() {
        int ranValue = (int) (Math.random() * 4);
        if (ranValue == 0) {
            return new FoodBag();
        }
        if (ranValue == 1) {
            return  new PotionBag();
        }
        if (ranValue == 2) {
            return new Nahrung();
        }
        if (ranValue == 3) {
            return new Trank();
        }
        return new Item();
    }

    public PositionComponent getPositionComponent() {
        return positionComponent;
    }

    public void setPositionComponent(PositionComponent positionComponent) {
        this.positionComponent = positionComponent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
