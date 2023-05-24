package ecs.components.ai.idle;


import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.VelocityComponent;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import ecs.items.Item;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;

import javax.management.monitor.GaugeMonitorMBean;

/**
 * class to implement a system that let an entity follow
 * the hero or eats an item placed in the dungeon
 */
public class FollowHeroOrEatItem implements IIdleAI {


    public Point from;
    public Point toRandomItem;
    public Item item;
    public GraphPath<Tile> goToPlayer;
    public GraphPath<Tile> eatItems;
    Entity entity;
    int action;

    /**
     * constructor for class FollowHeroOrEatItem
     * @param entity
     */
    public FollowHeroOrEatItem(Entity entity){
        this.entity = entity;
         action = (int) (Math.random()*2+1);
         item = Game.items.get((int) (Math.random()*Game.items.size()-1));
         toRandomItem =  item.positionComponent.getPosition();


    }

    @Override
    public void idle(Entity entity) {

        if(action==1){
            performAction1(entity);
        } else if (action==2) {
            performAction2(entity);
        }


    }

    private void performAction1(Entity entity) {
        if(goToPlayer==null){
            goToPlayer = AITools.calculatePathToHero(entity);

        }
        AITools.move(entity,goToPlayer);



    }

    private void performAction2(Entity entity) {
        if(eatItems==null){
            eatItems = AITools.calculatePath(from,toRandomItem);

        }
        AITools.move(entity,eatItems);
        if(entity.getComponent(VelocityComponent.class).isPresent()){
            VelocityComponent velocityComponent = (VelocityComponent) entity.getComponent(VelocityComponent.class).get();
            if(velocityComponent.getCurrentXVelocity() == 0&& velocityComponent.getCurrentYVelocity() ==0){
               Game.removeEntity(item);
            }
        }



    }


}
