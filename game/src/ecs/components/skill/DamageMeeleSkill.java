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

public abstract class DamageMeeleSkill implements ISkillFunction {

    private String pathToTexturesUp;
    private String pathToTexturesDown;
    private String pathToTexturesRight;
    private String pathToTexturesLeft;
    private Damage meeleDamage;
    private Point meeleHitboxSize;

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
    }

    @Override
    public void execute(Entity entity) {
        Entity meele = new Entity();

        Tile.Direction direction = SkillTools.getCursorPositionAsDirection(entity);

        HitboxComponent ehc =
            (HitboxComponent)
                entity.getComponent(HitboxComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("HitboxComponent"));


        new PositionComponent(meele, this.calculateHitboxPosition(ehc, direction));
//
//        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfProjectile);
//        new AnimationComponent(meele, animation);

        new ProjectileComponent(meele, epc.getPosition(), targetPoint);
        ICollide collide =
            (a, b, from) -> {
                if (b != entity) {
                    b.getComponent(HealthComponent.class)
                        .ifPresent(
                            hc -> {
                                ((HealthComponent) hc).receiveHit(projectileDamage);
                                Game.removeEntity(meele);
                            });
                }
            };

        Point hitboxSize = meeleHitboxSize;

        // Hitbox is rotated if direction is east or west
        if(direction.getValue().x == 0)
            hitboxSize = new Point(meeleHitboxSize.y, meeleHitboxSize.x);

        new HitboxComponent(
            meele, new Point(0f, 0f), hitboxSize, collide, null);
    }
    private void setAnimationPaths(String pathToTextures)
    {
        this.pathToTexturesUp = pathToTextures + "/up";
        this.pathToTexturesDown = pathToTextures + "/down";
        this.pathToTexturesRight = pathToTextures + "/right";
        this.pathToTexturesLeft = pathToTextures + "/left";
    }

    private Point calculateHitboxPosition(HitboxComponent ehc, Tile.Direction direction) {
        Point center = ehc.getCenter();
        Point size = new Point(ehc.getTopRight().x - ehc.getBottomLeft().x, ehc.getTopRight().y - ehc.getBottomLeft().y);
        Point pDirection = direction.getValue();

        Point edge = new Point(center.x * pDirection.x * size.x/2, center.y * pDirection.y * size.y/2);

        // new Direction rotated 90° counterclockwise from pDirection
        Point pAngleDirection = new Point(edge.y * -1,edge.x);

        Point position = new Point(edge.x * pAngleDirection.x * this.meeleHitboxSize.x/2, edge.x * pAngleDirection.x * this.meeleHitboxSize.x/2);
        return position;

    }
}
