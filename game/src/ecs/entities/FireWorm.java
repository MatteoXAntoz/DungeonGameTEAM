package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.GoToLadder;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.ITransition;
import graphic.Animation;
import starter.Game;

public class FireWorm extends BossMonster {

    GoToLadder goToLadder;
    Animation walkLeft, walkRight;

    String pathToAttackLeft = "fire-worm/attackLeft";
    String pathToAttackRight = "fire-worm/attackRight";
    String pathToWalkingLeft = "fire-worm/walkLeft";
    String pathToWalkingRight = "fire-worm/walkRight";

    public FireWorm() {

        goToLadder = new GoToLadder();
        pathToIdleLeft = "fire-worm/idleLeft";
        pathToIdleRight = "fire-worm/idleRight";

        pathToAttackLeft = "fire-worm/attackLeft";
        pathToAttackRight = "fire-worm/attackRight";

        pathToWalkingLeft = "fire-worm/walkLeft";
        pathToWalkingRight = "fire-worm/walkRight";


        setupAnimation();
        setupPosition();
        setupVelocity();
        setupAi();

    }

    @Override
    public void idle(Entity entity) {
        goToLadder.idle(entity);
    }

    @Override
    protected void setupPosition() {

    }

    @Override
    protected void setupVelocity() {
        xSpeed = 0.2f;
        ySpeed = 0.2f;
        velocityComponent = new VelocityComponent(this, xSpeed, ySpeed, walkLeft, walkRight);


    }

    @Override
    protected void setupAnimation() {
        walkLeft = AnimationBuilder.buildAnimation(pathToAttackLeft);
        walkRight = AnimationBuilder.buildAnimation(pathToAttackRight);
        idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);

        new AnimationComponent(this, walkLeft, walkRight);


    }

    @Override
    protected void setupHealthcomponent() {
        healthComponent = new HealthComponent(this);

    }

    @Override
    protected void setupAi() {

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

    private void activateSecondStage() {

    }

    private boolean isSecondStage() {
        return (healthComponent.getCurrentHealthpoints() <= healthComponent.getMaximalHealthpoints() / 2);
    }




}
