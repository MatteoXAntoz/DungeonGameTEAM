package ecs.components.skill;

import ecs.entities.Entity;
import ecs.entities.Hero;
import starter.Game;

public class SprintSkill extends Skill {

    // Matteo
    public final float xBoost = 0.6f;
    public final float yBoost = 0.6f;

    int damageProTick = 1;
    int duration = 20;
    public boolean active = false;
    private ManaComponent mc;
    private boolean manaConsumed = false;

    /**
     * @param skillFunction Function of this skill
     * @param coolDownInSeconds
     */
    public SprintSkill(Entity entity, ISkillFunction skillFunction, float coolDownInSeconds) {
        super(skillFunction, coolDownInSeconds);
        mc = (ManaComponent)
            entity
                .getComponent(ManaComponent.class).get();
    }

    public void update(Hero hero) {
        if (active) {
            duration -= 1;
            hero.healthComponent.setCurrentHealthpoints(
                    hero.healthComponent.getCurrentHealthpoints() - damageProTick);
            hero.velocityComponent.setXVelocity(xBoost);
            hero.velocityComponent.setYVelocity(yBoost);
        }
        if (duration <= 0) {
            active = false;
            duration = 20;
            hero.velocityComponent.setXVelocity(0.3f);
            hero.velocityComponent.setYVelocity(0.3f);
            manaConsumed = false;
        }
        if(!manaConsumed)
        {
            mc.reduceManaPoints(7);
            manaConsumed = true;
        }
    }
}
