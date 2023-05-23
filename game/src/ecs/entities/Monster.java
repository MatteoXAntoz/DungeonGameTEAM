package ecs.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.InteractionComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.idle.IIdleAI;
import graphic.Animation;
import level.elements.tile.Tile;
import starter.Game;

public abstract class Monster extends Entity implements IIdleAI {

    float xSpeed;
    float ySpeed;
    Animation idleLeft,idleRight;
    InteractionComponent interactionComponent;
    VelocityComponent velocityComponent;
    GraphPath<Tile> path;

    protected   String pathToIdleLeft;
    protected   String pathToIdleRight;

    protected abstract void setupPosition();
    protected abstract void setupVelocity();

    protected abstract void setupAnimation();


}
