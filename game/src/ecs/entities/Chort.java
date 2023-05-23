package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;

public class Chort extends Monster{

    public Chort(){

        pathToIdleLeft ="chort/idleLeft";
        pathToIdleRight ="chort/idleRight";

        setupAnimation();
        setupPosition();
        setupVelocity();

    }

    @Override
    protected void setupAnimation() {
        idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    public void idle(Entity entity) {

    }

    @Override
    protected void setupPosition() {
        positionComponent = new PositionComponent(this);
    }

    @Override
    protected void setupVelocity() {
        xSpeed = 0.5f;
        ySpeed = 0.5f;
        velocityComponent = new VelocityComponent(this,xSpeed,ySpeed,idleLeft,idleRight);
    }
}
