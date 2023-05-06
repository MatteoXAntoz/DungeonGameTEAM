package ecs.items;

import ecs.components.PositionComponent;
import ecs.entities.Entity;
import tools.Point;

public class Item extends Entity implements IOnCollect,IOnDrop,IOnUse {

    protected PositionComponent positionComponent;



    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {

    }

    @Override
    public void onDrop(Entity user, ItemData which, Point position) {

    }

    @Override
    public void onUse(Entity e, ItemData item) {

    }
}
