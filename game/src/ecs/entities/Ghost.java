package ecs.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.transition.ITransition;
import ecs.entities.items.Zauberstab;
import graphic.Animation;
import java.util.logging.Logger;
import level.elements.tile.Tile;
import level.riddle.Riddle;
import starter.Game;

/** class to create a friendly npc ghost in the dungeon */
public class Ghost extends Entity implements IInteraction, IIdleAI {

    private final Logger ghost_logger = Logger.getLogger(this.getClass().getName());
    Animation idleLeft, idleRight;
    InteractionComponent interactionComponent;

    Hero hero = Game.hero;

    GraphPath<Tile> path;

    Riddle riddle;

    private final String pathToIdleLeft = "ghost/idleLeft";
    private final String pathToIdleRight = "ghost/idleRight";

    public Ghost() {
        super();
        setupAnimationComponent();
        setupPosition();
        setupInteraction();
        new VelocityComponent(this, 0.9f, 0.9f, idleLeft, idleLeft);
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
        new Thread(this::dialogWithGhost).start();
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

    private void giveWand() {
        Zauberstab zauberstab = new Zauberstab();
        zauberstab.positionComponent.setPosition(hero.positionComponent.getPosition());
        Game.items.add(zauberstab);
        Game.removeEntity(this);
    }

    private void killHero() {
        hero.healthComponent.setCurrentHealthpoints(0);
    }

    private void dialogWithGhost() {
        Game.togglePause();
        int riddleReturnValue = new Riddle().ghostRiddle();
        switch (riddleReturnValue) {
            case 0:
                giveWand();
                break;
            case 1:
                killHero();
                break;
            default:
                ghost_logger.warning("Riddle was neither solved nor not solved!");
        }
        Game.togglePause();
    }
}
