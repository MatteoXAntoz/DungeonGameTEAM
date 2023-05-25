package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.FollowHeroOrEatItem;
import ecs.components.ai.transition.ITransition;
import starter.Game;

/** class to create a monster of the type chort */
public class Chort extends Monster {

    boolean collide;
    FollowHeroOrEatItem followHeroOrEatItem;

    public Chort() {

        pathToIdleLeft = "chort/idleLeft";
        pathToIdleRight = "chort/idleRight";

        setupAnimation();
        setupPosition();
        setupVelocity();
        setupAi();
        followHeroOrEatItem = new FollowHeroOrEatItem(this);
        followHeroOrEatItem.from = positionComponent.getPosition();
        damage = 20;
    }

    @Override
    protected void setupAnimation() {
        idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    public void idle(Entity entity) {
        followHeroOrEatItem.idle(entity);
    }

    /** method to setup the AIComponent */
    private void setupAi() {
        new AIComponent(
                this,
                new IFightAI() {
                    @Override
                    public void fight(Entity entity) {}
                },
                this,
                new ITransition() {
                    @Override
                    public boolean isInFightMode(Entity entity) {
                        return false;
                    }
                });
    }

    @Override
    protected void setupPosition() {
        positionComponent = new PositionComponent(this);
        positionComponent.setPosition(
                Game.currentLevel.getRandomFloorTile().getCoordinateAsPoint());
    }

    @Override
    protected void setupVelocity() {
        xSpeed = 0.02f;
        ySpeed = 0.02f;
        velocityComponent = new VelocityComponent(this, xSpeed, ySpeed, idleLeft, idleRight);
    }
}
