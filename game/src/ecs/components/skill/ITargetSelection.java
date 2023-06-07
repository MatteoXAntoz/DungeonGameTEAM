package ecs.components.skill;

import ecs.entities.Entity;
import tools.Point;

public interface ITargetSelection {

    Point selectTargetPoint(Entity entity);
}
