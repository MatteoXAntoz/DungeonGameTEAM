package ecs.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.transition.ITransition;
import level.elements.tile.Tile;

/** class to create a monster of the type mouse */
public class Mouse extends Monster {

    boolean collide;

    GraphPath<Tile> path;

    /** constructor for class mouse */
    public Mouse() {

        pathToIdleLeft = "mouse/idleLeft";
        pathToIdleRight = "mouse/idleRight";

        setupAnimation();
        setupPosition();
        setupVelocity();
        setupAi();
        damage = 2;
    }

    @Override
    protected void setupAnimation() {
        idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    public void idle(Entity entity) {
        if (path == null) {
            path = AITools.calculatePathToHero(entity);
        }
        AITools.move(entity, path);

        path = null;
    }

    /** method to setup AIComponent */
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
    }

    @Override
    protected void setupVelocity() {
        xSpeed = 0.02f;
        ySpeed = 0.02f;
        velocityComponent = new VelocityComponent(this, xSpeed, ySpeed, idleLeft, idleRight);
    }
}
