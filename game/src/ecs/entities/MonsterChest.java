package ecs.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.transition.ITransition;
import ecs.entities.items.Item;
import graphic.Animation;
import java.util.List;
import java.util.logging.Logger;
import level.elements.tile.Tile;
import starter.Game;

/** class to create a monsterchest that attacks and follows the player */
public class MonsterChest extends Monster {

    private final Logger monsterChest_logger = Logger.getLogger(this.getClass().getName());
    IFightAI iFightAI;
    ITransition itransition;
    Animation idle, idleLeft, idleRight;
    InteractionComponent interactionComponent;
    IOnDeathFunction deathFunction;
    Hero hero = Game.hero;
    GraphPath<Tile> followHero;
    HealthComponent healthComponent;

    boolean fight;
    /** set the default interaction radius in which the player can operate with the monsterchest */
    public static final float defaultInteractionRadius = 1f;
    /** path to the animation for the monsterchest */
    public static final String monsterChest_closed =
            "objects/treasurechest/chest_full_open_anim_f0.png";
    /** animation frame for the chest */
    public static final List<String> DEFAULT_CLOSED_ANIMATION_FRAMES =
            List.of("objects/treasurechest/chest_full_open_anim_f0.png");
    /** animation frames for the chest */
    public static final List<String> DEFAULT_OPENING_ANIMATION_FRAMES =
            List.of(
                    "objects/treasurechest/chest_full_open_anim_f0.png",
                    "objects/treasurechest/chest_full_open_anim_f1.png",
                    "objects/treasurechest/chest_full_open_anim_f2.png",
                    "objects/treasurechest/chest_empty_open_anim_f2.png");

    /** constructor for class MonsterChest */
    public MonsterChest() {
        super();
        this.deathFunction =
                (e) -> {
                    if (itransition.isInFightMode(this)) {
                        dropItem();
                    }
                };
        setupITransition();
        setupAnimation();
        setupInteraction();
        setupPosition();
        setupHitbox();
        setupAI();
        new VelocityComponent(this, 0.04f, 0.04F, idleLeft, idleRight);
        setupHealthComponent();
    }

    /** setup the position component */
    protected void setupPosition() {
        positionComponent = new PositionComponent(this);
    }

    @Override
    protected void setupVelocity() {}

    /** setup the animation component */
    protected void setupAnimation() {
        idleRight = AnimationBuilder.buildAnimation(monsterChest_closed);
        idleLeft = AnimationBuilder.buildAnimation(monsterChest_closed);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    /**
     * setup the interaction component and sets the fight mode if player interacts with monsterchest
     */
    protected void setupInteraction() {
        interactionComponent =
                new InteractionComponent(
                        this,
                        0.5f,
                        false,
                        new IInteraction() {
                            @Override
                            public void onInteraction(Entity entity) {
                                fight = true;
                            }
                        });
    }

    /** setup the hitbox */
    protected void setupHitbox() {
        HitboxComponent hitboxComponent = new HitboxComponent(this);
    }

    /** setup the AI */
    protected void setupAI() {
        AIComponent ai =
                new AIComponent(
                        this,
                        new IFightAI() {
                            @Override
                            public void fight(Entity entity) {
                                if (itransition.isInFightMode(entity)) {
                                    followHero(entity);
                                }

                                if (healthComponent.getCurrentHealthpoints() == 0) {
                                    dropItem();
                                }
                            }
                        },
                        this,
                        itransition);
    }

    /** setup the health component */
    protected void setupHealthComponent() {
        healthComponent = new HealthComponent(this, 10, deathFunction, idleRight, idleLeft);
        healthComponent.setMaximalHealthpoints(50);
        healthComponent.setCurrentHealthpoints(50);
    }

    /** method to drops an item if the monsterchest dies */
    public void dropItem() {
        Item item1 = Item.ranItem();
        Item item2 = Item.ranItem();
        item1.positionComponent.setPosition(positionComponent.getPosition());
        item2.positionComponent.setPosition(positionComponent.getPosition());
        Game.items.add(item1);
        Game.items.add(item2);
    }

    // method to calculate the path to player and moves the monsterchest in the direction
    private void followHero(Entity entity) {
        if (followHero == null) {
            followHero =
                    AITools.calculatePath(
                            entity.positionComponent.getPosition(),
                            Game.hero.positionComponent.getPosition());
        }
        AITools.move(entity, followHero);
        followHero = null;
    }

    @Override
    public void idle(Entity entity) {}

    private void setupITransition() {
        itransition =
                new ITransition() {
                    @Override
                    public boolean isInFightMode(Entity entity) {
                        return fight;
                    }
                };
    }
}
