package ecs.entities;


import com.badlogic.gdx.ai.pfa.GraphPath;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.FollowHeroOrEatItem;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.transition.ITransition;
import ecs.entities.items.Item;
import graphic.Animation;
import level.elements.tile.Tile;
import starter.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * class to create a monster of the type chort
 */
public class MonsterChest extends Monster {

    IFightAI iFightAI;
    ITransition itransition;
    Animation idle, idleLeft, idleRight;
    InteractionComponent interactionComponent;
    IOnDeathFunction deathFunction;
    Hero hero = Game.hero;
    GraphPath<Tile> followHero;
    HealthComponent healthComponent;

    boolean fight;
    public static final float defaultInteractionRadius = 1f;
    public static final String monsterChest_closed = "objects/treasurechest/chest_full_open_anim_f0.png";
    public static final List<String> DEFAULT_CLOSED_ANIMATION_FRAMES =
        List.of("objects/treasurechest/chest_full_open_anim_f0.png");
    public static final List<String> DEFAULT_OPENING_ANIMATION_FRAMES =
        List.of(
            "objects/treasurechest/chest_full_open_anim_f0.png",
            "objects/treasurechest/chest_full_open_anim_f1.png",
            "objects/treasurechest/chest_full_open_anim_f2.png",
            "objects/treasurechest/chest_empty_open_anim_f2.png");

    public void setupPosition() {
        positionComponent = new PositionComponent(this);
    }

    @Override
    protected void setupVelocity() {

    }

    public void setupAnimation() {
        AnimationComponent ac =
            new AnimationComponent(
                this,
                new Animation(DEFAULT_CLOSED_ANIMATION_FRAMES, 100, false),
                new Animation(DEFAULT_OPENING_ANIMATION_FRAMES, 100, false));
        idleRight = AnimationBuilder.buildAnimation(monsterChest_closed);
        idleLeft = AnimationBuilder.buildAnimation(monsterChest_closed);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    protected void setupHealthcomponent() {
        healthComponent = new HealthComponent(
            this,
            10,
            deathFunction,
            idle,
            idle
        );
    }

    @Override
    protected void setupAi() {
        AIComponent ai = new AIComponent(this, new IFightAI() {
            @Override
            public void fight(Entity entity) {
                if (itransition.isInFightMode(entity)) {
                    followHero(entity);
                }

                if(healthComponent.getCurrentHealthpoints()==0){
                    dropItem();
                }


            }
        }, this, itransition);
    }

    public void setupInteraction() {
        interactionComponent = new InteractionComponent(this, 0.5f, false, new IInteraction() {
            @Override
            public void onInteraction(Entity entity) {
                fight = true;
            }
        });
    }

    public void setupHitbox() {
        HitboxComponent hitboxComponent = new HitboxComponent(this);
    }





    public MonsterChest() {
        super();
        setupITransition();
        setupAnimation();
        setupInteraction();
        setupPosition();
        setupHitbox();
        setupAi();
        new VelocityComponent(this, 0.04f, 0.04F, idleLeft, idleRight);
        setupHealthcomponent();


    }

    public void dropItem() {
        Game.removeEntity(this);
        int anzahl = 2;
        ArrayList<Item> drop = new ArrayList<>();
        for(int i = 0;i<anzahl;i++ ){
           drop.add(Item.ranItem());
        }

        for(Item item:drop){
            item.positionComponent.setPosition(positionComponent.getPosition().toCoordinate().toPoint());
        }


    }



    private void followHero(Entity entity) {
        if (followHero == null) {
            followHero = AITools.calculatePath(entity.positionComponent.getPosition(), Game.hero.positionComponent.getPosition());
        }
        AITools.move(entity, followHero);
        followHero  = null;
    }


    @Override
    public void idle(Entity entity) {

    }

    private void setupITransition(){
        itransition = new ITransition() {
            @Override
            public boolean isInFightMode(Entity entity) {
                return fight;
            }
        };
    }


}
