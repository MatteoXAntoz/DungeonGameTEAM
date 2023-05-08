package ecs.items;

import ecs.entities.Entity;
import ecs.entities.Hero;
import starter.Game;
import tools.Point;

public class Nahrung extends Item {

    //Erstmal nur eine Klasse
    //Wird spaeter zu einer Superklasse für andere Nahrungen
    public Nahrung(){
        super();
        name ="Nahrung";
        path = "apple_no_background.png";
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


    public static void HEALPLAYER(){
        Hero hero = Game.hero;
        int ranValue = (int) (Math.random()*hero.healthComponent.getCurrentHealthpoints()+10);
        hero.healthComponent.setCurrentHealthpoints(hero.healthComponent.getMaximalHealthpoints()+ranValue);
    }


}
