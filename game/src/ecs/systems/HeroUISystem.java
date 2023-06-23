package ecs.systems;

import ecs.components.HealthComponent;
import ecs.components.skill.ManaComponent;
import ecs.components.skill.SkillComponent;
import ecs.components.xp.XPComponent;
import graphic.hud.HeroUI;
import starter.Game;

/** Updates Values for HeroUI */
public class HeroUISystem extends ECS_System {
    private HealthComponent hc;
    private ManaComponent mc;
    private XPComponent xpc;
    private SkillComponent sc;

    private boolean doSetup = true;

    private void setup() {
        doSetup = false;
        hc = (HealthComponent) Game.hero.getComponent(HealthComponent.class).get();

        mc = (ManaComponent) Game.hero.getComponent(ManaComponent.class).get();

        xpc = (XPComponent) Game.hero.getComponent(XPComponent.class).get();

        sc = (SkillComponent) Game.hero.getComponent(SkillComponent.class).get();
    }

    @Override
    public void update() {
        if (doSetup) setup();

        HeroUI heroUI = HeroUI.getInstance();

        heroUI.setCurrentHealth(hc.getCurrentHealthpoints());
        heroUI.setMaximumHealth(hc.getMaximalHealthpoints());

        heroUI.setCurrentMana(mc.getCurrentPoints());

        heroUI.setCurrentXP((int) xpc.getCurrentXP());
        heroUI.setMaximumXP((int) (xpc.getCurrentXP() + xpc.getXPToNextLevel()));
        heroUI.setLevel((int) xpc.getCurrentLevel());

        heroUI.update();
    }
}
