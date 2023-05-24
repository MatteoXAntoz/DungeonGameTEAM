package ecs.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
import dslToGame.AnimationBuilder;
import dslToGame.graph.Graph;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.FollowHeroOrEatItem;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.ITransition;
import level.elements.tile.Tile;
import starter.Game;

/**
 * class to create a monster of the type demon
 */
public class Demon extends Monster {

    boolean collide;

    GraphPath<Tile> path;


    public Demon() {

        pathToIdleLeft = "demon/idleLeft";
        pathToIdleRight = "demon/idleRight";

        setupAnimation();
        setupPosition();
        setupVelocity();
        setupAi();
        damage = 10;


    }

    @Override
    protected void setupAnimation() {
        idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    /**
     * method to setup the AIComponent
     * @param entity associated entity
     */
    @Override
    public void idle(Entity entity) {
      if(path==null){
          path =  AITools.calculatePathToHero(entity);
      }

      AITools.move(entity,path);
      path =null;

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
        xSpeed = 0.05f;
        ySpeed = 0.05f;
        velocityComponent = new VelocityComponent(this, xSpeed, ySpeed, idleLeft, idleRight);
    }
}
