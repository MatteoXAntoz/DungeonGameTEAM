package ecs.components.skill;

public class HealingSkill extends Skill {

    // Matteo

    public final int healingBoost = 25;
    public int potion = 0;

    public final int MAX_POTIONAMOUNT = 5;

    /**
     * @param skillFunction Function of this skill
     * @param coolDownInSeconds
     */
    public HealingSkill(ISkillFunction skillFunction, float coolDownInSeconds) {
        super(skillFunction, coolDownInSeconds);
    }

    public void addPotion() {
        potion += 1;
    }

    public void removePotion() {
        potion -= 1;
    }
}
