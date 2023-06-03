package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import graphic.Animation;
import starter.Game;

public class FireWorm extends BossMonster {


    public FireWorm() {

        pathToIdleLeft = "fire-worm/idleRight";
        pathToIdleRight = "fire-worm/idleRight";

        setupAnimation();
        setupPosition();
        setupVelocity();

    }

    @Override
    public void idle(Entity entity) {

    }

    @Override
    protected void setupPosition() {
        positionComponent.setPosition(Game.currentLevel.getEndTile().getCoordinateAsPoint());
    }

    @Override
    protected void setupVelocity() {
        xSpeed = 1;
        ySpeed = 1;

    }

    @Override
    protected void setupAnimation() {
        idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        new AnimationComponent(this, idleLeft, idleRight);


    }

    @Override
    protected void setupHealthcomponent() {
        healthComponent = new HealthComponent(this);
    }
}
