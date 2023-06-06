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
        meeleLogger.setLevel(Level.ALL);
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


        new PositionComponent(meele, this.calculateHitboxPosition(ehc, direction));

        new MeeleComponent(meele);

        Animation animation = AnimationBuilder.buildAnimation("animation/");
        new AnimationComponent(meele, animation);

        ICollide collide =
            (a, b, from) -> {
                if (b != entity) {
                    b.getComponent(HealthComponent.class)
                        .ifPresent(
                            hc -> {
                                System.out.println("Hit!");
                                ((HealthComponent) hc).receiveHit(meeleDamage);
                                Game.removeEntity(meele);
                            });
                }
            };

        Point hitboxSize = meeleHitboxSize;

        // Hitbox is rotated if direction is east or west
        if(direction.x != 0)
            hitboxSize = new Point(meeleHitboxSize.y, meeleHitboxSize.x);

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

    private Point calculateHitboxPosition(HitboxComponent ehc, Point direction) {
        meeleLogger.finer("Direction: " + direction);
        Point center = ehc.getCenter();
        Point size = new Point(ehc.getTopRight().x - ehc.getBottomLeft().x, ehc.getTopRight().y - ehc.getBottomLeft().y);
        meeleLogger.finer("Entity Hitbox Size: " + size);

        Point edge = new Point(center.x + direction.x * size.x/2, center.y + direction.y * size.y/2);
        meeleLogger.finer("Entity Edge: " + edge);

        // new Direction rotated 90° counterclockwise from pDirection
        Point angleDirection = new Point(direction.y * -1, direction.x);
        meeleLogger.finer("New Direction from Edge: " + angleDirection);

        Point position = new Point(edge.x + angleDirection.x * this.meeleHitboxSize.x/2, edge.y + angleDirection.y * this.meeleHitboxSize.y/2);
        meeleLogger.finer("New Position: " + position);
        return position;

    }
}
