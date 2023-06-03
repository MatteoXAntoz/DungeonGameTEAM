package ecs.entities.NPCs;

import com.badlogic.gdx.ai.pfa.GraphPath;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.transition.ITransition;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.items.Zauberstab;
import graphic.Animation;
import level.elements.tile.Tile;
import starter.Game;

/** class to create a friendly npc ghost in the dungeon */
public class Ghost extends Entity implements IInteraction, IIdleAI {

    Animation idleLeft, idleRight;
    InteractionComponent interactionComponent;

    Hero hero = Game.hero;

    GraphPath<Tile> path;

    private final String pathToIdleLeft = "ghost/idleLeft";
    private final String pathToIdleRight = "ghost/idleRight";

    public Ghost() {
        super();
        setupAnimationComponent();
        setupPosition();
        setupInteraction();

        new VelocityComponent(this, 0.04f, 0.04F, idleLeft, idleLeft);
        setupAi();
    }

    public void setupPosition() {
        positionComponent = new PositionComponent(this);
    }

    private void setupInteraction() {
        interactionComponent = new InteractionComponent(this, 0.5f, false, this);
    }

    private void setupAnimationComponent() {
        idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    public void onInteraction(Entity entity) {
        int randomValue = (int) (Math.random() * 2 + 1);

        if (randomValue == 1) {
            Game.removeEntity(this);
        } else if (randomValue == 2) {
            Zauberstab zauberstab = new Zauberstab();
            zauberstab.positionComponent.setPosition(hero.positionComponent.getPosition());
            Game.items.add(zauberstab);
            Game.removeEntity(this);
        }
    }

    @Override
    public void idle(Entity entity) {
        if (path == null) {
            path = AITools.calculatePathToHero(entity);
        }
        AITools.move(this, path);
    }

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
}
