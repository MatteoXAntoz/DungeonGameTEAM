package ecs.entities;

import ecs.components.InteractionComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import level.generator.randomwalk.RandomWalkGenerator;

public class Ghost extends Entity{

    AIComponent aiComponent;
    PositionComponent positionComponent;
    VelocityComponent velocityComponent;
    InteractionComponent interactionComponent;



}
