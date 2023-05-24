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

    boolean stopped;

    /**
     * Erzeugt eine neue Instanz von FollowHeroOrEatItem für die angegebene Entität.
     *
     * @param entity Die Entität, für die das Verhalten gesteuert wird.
     */
    public FollowHeroOrEatItem(Entity entity){
        this.entity = entity;
        action = (int) (Math.random()*2+1);
        item = Game.items.get((int) (Math.random()*Game.items.size()-1));
        toRandomItem = item.positionComponent.getPosition();
    }

    @Override
    public void idle(Entity entity) {
        if(action == 1) {
            walkToPlayer(entity);
        } else if (action == 2) {
            walkToItem(entity);
        }
    }

    /**
     * Lässt die Entität dem Helden folgen.
     *
     * @param entity Die Entität, die dem Helden folgt.
     */
    private void walkToPlayer(Entity entity) {
        if(goToPlayer == null) {
            goToPlayer = AITools.calculatePathToHero(entity);
        }
        AITools.move(entity, goToPlayer);
    }

    /**
     * Lässt die Entität zu einem zufälligen Item gehen und es fressen.
     *
     * @param entity Die Entität, die zu einem Item geht und es frisst.
     */
    private void walkToItem(Entity entity) {
        if(eatItems == null) {
            eatItems = AITools.calculatePath(from, toRandomItem);
        }
        AITools.move(entity, eatItems);
        if(entity.getComponent(VelocityComponent.class).isPresent()) {
            VelocityComponent velocityComponent = (VelocityComponent) entity.getComponent(VelocityComponent.class).get();
            if(velocityComponent.getCurrentXVelocity() == 0 && velocityComponent.getCurrentYVelocity() == 0 && !stopped) {
                stopped = true;
                Game.removeEntity(item);
            }
        }
    }
}
