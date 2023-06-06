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

    private String pathToTexturesUp;
    private String pathToTexturesDown;
    private String pathToTexturesRight;
    private String pathToTexturesLeft;
    private Damage meeleDamage;
    private Point meeleHitboxSize;
    private static Logger meeleLogger = Logger.getLogger(DamageMeeleSkill.class.getName());

    private ITargetSelection selectionFunction;

    public DamageMeeleSkill(
        String pathToTexturesOfMeele,
        Damage meeleDamage,
        Point meeleHitboxSize,
        ITargetSelection selectionFunction) {
        this.setAnimationPaths(pathToTexturesOfMeele);
        this.meeleDamage = meeleDamage;
        this.meeleHitboxSize = meeleHitboxSize;
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

        new MeeleComponent(meele);

        Animation animation = AnimationBuilder.buildAnimation("animation/");
        new AnimationComponent(meele, animation);

        ICollide collide =
            (self, other, from) -> {
                System.out.println("Hit1!");
                System.out.println(other.getClass().getSimpleName());
                System.out.println(other.getComponent(HealthComponent.class).isPresent());
                if (other != entity) {
                    other.getComponent(HealthComponent.class)
                        .ifPresent(
                            hc -> {
                                System.out.println("Hit2!");
                                ((HealthComponent) hc).receiveHit(meeleDamage);
                                Game.removeEntity(meele);
                            });
                }
            };

        HitboxComponent hc =
            new HitboxComponent(
            meele, new Point(0f, 0f), hitboxSize, collide, null);

        // Logger
        meeleLogger.fine(
            "Entity Hitbox: BL-" + ehc.getBottomLeft() + " TR-" + ehc.getTopRight() + "\n" +
                "Meele Hitbox: BL-" + hc.getBottomLeft() + " TR-" + hc.getTopRight()
        );
    }
    private void setAnimationPaths(String pathToTextures)
    {
        this.pathToTexturesUp = pathToTextures + "/up";
        this.pathToTexturesDown = pathToTextures + "/down";
        this.pathToTexturesRight = pathToTextures + "/right";
        this.pathToTexturesLeft = pathToTextures + "/left";
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
}
