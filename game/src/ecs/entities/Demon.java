package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.FollowHeroOrEatItem;
import ecs.components.ai.transition.ITransition;

public class Demon extends Monster {

    boolean collide;
    FollowHeroOrEatItem followHeroOrEatItem;

    public Demon() {

        pathToIdleLeft = "demon/idleLeft";
        pathToIdleRight = "demon/idleRight";

        setupAnimation();
        setupPosition();
        setupVelocity();
        setupAi();
        followHeroOrEatItem = new FollowHeroOrEatItem(this);
        followHeroOrEatItem.from = positionComponent.getPosition();


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

    private void setupAi() {
        new AIComponent(this, new IFightAI() {
            @Override
            public void fight(Entity entity) {

            }
        }, this, new ITransition() {
            @Override
            public boolean isInFightMode(Entity entity) {
                return false;
            }
        });
    }

    @Override
    protected void setupPosition() {
        positionComponent = new PositionComponent(this);
    }

    @Override
    protected void setupVelocity() {
        xSpeed = 0.1f;
        ySpeed = 0.1f;
        velocityComponent = new VelocityComponent(this, xSpeed, ySpeed, idleLeft, idleRight);
    }
}
