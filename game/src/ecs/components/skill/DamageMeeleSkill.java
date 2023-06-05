package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
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
        Entity projectile = new Entity();
        PositionComponent epc =
            (PositionComponent)
                entity.getComponent(PositionComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(projectile, epc.getPosition());

        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfProjectile);
        new AnimationComponent(projectile, animation);

        Point aimedOn = selectionFunction.selectTargetPoint();
        Point targetPoint =
            SkillTools.calculateLastPositionInRange(
                epc.getPosition(), aimedOn, projectileRange);
        Point velocity =
            SkillTools.calculateVelocity(epc.getPosition(), targetPoint, projectileSpeed);
        VelocityComponent vc =
            new VelocityComponent(projectile, velocity.x, velocity.y, animation, animation);
        new ProjectileComponent(projectile, epc.getPosition(), targetPoint);
        ICollide collide =
            (a, b, from) -> {
                if (b != entity) {
                    b.getComponent(HealthComponent.class)
                        .ifPresent(
                            hc -> {
                                ((HealthComponent) hc).receiveHit(projectileDamage);
                                Game.removeEntity(projectile);
                            });
                }
            };

        new HitboxComponent(
            projectile, new Point(0.25f, 0.25f), projectileHitboxSize, collide, null);
    }
    private void setAnimationPaths(String pathToTextures)
    {
        this.pathToTexturesUp = pathToTextures + "/up";
        this.pathToTexturesDown = pathToTextures + "/down";
        this.pathToTexturesRight = pathToTextures + "/right";
        this.pathToTexturesLeft = pathToTextures + "/left";
    }
}
