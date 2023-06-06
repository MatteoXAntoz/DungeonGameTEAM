package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DamageMeeleSkill implements ISkillFunction {

    private String pathToTextures;
    private Damage meeleDamage;
    private Point meeleHitboxSize;
    private final float knockBackVelocity;
    private final int knockBackDuration;
    private static Logger meeleLogger = Logger.getLogger(DamageMeeleSkill.class.getName());

    private ITargetSelection selectionFunction;

    public DamageMeeleSkill(
        String pathToTextures,
        Damage meeleDamage,
        Point meeleHitboxSize,
        float knockBackVelocity,
        int knockBackDuration,
        ITargetSelection selectionFunction) {
        this.pathToTextures = pathToTextures;
        this.meeleDamage = meeleDamage;
        this.meeleHitboxSize = meeleHitboxSize;
        this.knockBackVelocity = knockBackVelocity;
        this.knockBackDuration = knockBackDuration;
        this.selectionFunction = selectionFunction;
        meeleLogger.setLevel(Level.INFO);
    }

    @Override
    public void execute(Entity entity) {
        Entity meele = new Entity();

        Point direction = selectionFunction.selectTargetPoint(entity);

        HitboxComponent ehc =
            (HitboxComponent)
                entity.getComponent(HitboxComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("HitboxComponent"));

        Point hitboxSize = meeleHitboxSize;
        // Hitbox is rotated if direction is east or west
        if(direction.x != 0)
            hitboxSize = new Point(meeleHitboxSize.y, meeleHitboxSize.x);

        new PositionComponent(meele, this.calculateHitboxPosition(ehc, direction, hitboxSize));

        int durationInFrames = 9;
        new MeeleComponent(meele, durationInFrames);

        Animation animation = this.getAnimations(direction, durationInFrames/3);
        new AnimationComponent(meele, animation);

        ICollide collide =
            (self, other, from) -> {
                if (other != entity) {
                    other.getComponent(HealthComponent.class)
                        .ifPresent(
                            hc -> {
                                ((HealthComponent) hc).receiveHit(meeleDamage);
                                Game.removeEntity(meele);
                            });
                    PositionComponent epc = (PositionComponent)(entity.getComponent(PositionComponent.class).get());
                    PositionComponent opc = (PositionComponent)(other.getComponent(PositionComponent.class).get());

                    Point velocity = SkillTools.calculateVelocity(
                        epc.getPosition(),
                        opc.getPosition(),
                        knockBackVelocity
                    );
                    other.addComponent  (
                        new KnockBackComponent(
                            other,
                            velocity.x,
                            velocity.y,
                            knockBackDuration
                        ));
                }
            };

        HitboxComponent hc =
            new HitboxComponent(
            meele, new Point(0f, 0f), hitboxSize, collide, null);

//        this.cloneEntityVelocity(entity, meele);

        // Logger
        meeleLogger.fine(
            "Entity Hitbox: BL-" + ehc.getBottomLeft() + " TR-" + ehc.getTopRight() + "\n" +
                "Meele Hitbox: BL-" + hc.getBottomLeft() + " TR-" + hc.getTopRight()
        );
    }
    private Animation getAnimations(Point direction, int frameTime)
    {
        String sDirection;
        if(direction.y == 1)
            sDirection = "attack_Up/";
        else if(direction.y == -1)
            sDirection = "attack_Down/";
        else if(direction.x == 1)
            sDirection = "attack_Right/";
        else
            sDirection = "attack_Left/";

        return AnimationBuilder.buildAnimation("knight/attack/" + sDirection, frameTime);
    }

    private Point calculateHitboxPosition(HitboxComponent ehc, Point direction, Point hitboxSize) {
        meeleLogger.finer("Direction: " + direction);
        Point eCenter = ehc.getCenter();
        Point eSize = new Point(
            ehc.getTopRight().x - ehc.getBottomLeft().x,
            ehc.getTopRight().y - ehc.getBottomLeft().y);
        meeleLogger.finer("Entity Hitbox Size: " + eSize);

        Point mCenter = new Point(
            eCenter.x + (eSize.x + hitboxSize.x) * direction.x / 2,
            eCenter.y + (eSize.y + hitboxSize.y) * direction.y / 2
            );

        Point mPosition = new Point(
            mCenter.x + hitboxSize.x / -2,
            mCenter.y + hitboxSize.y / -2
        );
        return mPosition;
    }

//    private void cloneEntityVelocity(Entity entity, Entity meele) {
//        VelocityComponent ehc =
//            (VelocityComponent)
//                entity.getComponent(VelocityComponent.class)
//                    .orElseThrow(
//                        () -> new MissingComponentException("VelocityComponent"));
//
//        meele.addComponent(ehc);
//    }
}
