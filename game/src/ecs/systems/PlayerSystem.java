package ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import configuration.KeyboardConfig;
import ecs.components.MissingComponentException;
import ecs.components.PlayableComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.tools.interaction.InteractionTool;
import level.LevelAPI;
import level.myQuest.*;

import starter.Game;

import static starter.Game.togglePause;


/**
 * Used to control the player
 */
public class PlayerSystem extends ECS_System {

    boolean quests_open = false;
    int choice = 0;

    LevelManager levelManager = LevelManager.getInstance();


    private record KSData(Entity e, PlayableComponent pc, VelocityComponent vc) {
    }

    @Override
    public void update() {
        Game.getEntities().stream()
            .flatMap(e -> e.getComponent(PlayableComponent.class).stream())
            .map(pc -> buildDataObject((PlayableComponent) pc))
            .forEach(this::checkKeystroke);

        if (quests_open) {
            if (quests_open) {
                if (Gdx.input.isKeyJustPressed(KeyboardConfig.QUEST_DOWN.get())
                    && choice < levelManager.myQuests.size()-1) {
                    choice++;
                    System.out.println("Your choice: " + choice);
                }
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.QUEST_UP.get())&& choice>0) {
                choice--;
                System.out.println("Your choice: " + choice);
            }


            //Quest kann angenommen werden.
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ) {
               if(!levelManager.myQuests.get(choice).isAgreed() && !levelManager.myQuests.get(choice).isAccomplished() ){
                   levelManager.myQuests.get(choice).setAgreed(true);
               }
                if(levelManager.myQuests.get(choice).isAgreed()){
                    levelManager.myQuests.get(choice).setAgreed(true);
                }

            }
            //Wurde die Quest angenommen, so kann sie wieder abgelehnt werden.

        }
    }

    private void checkKeystroke(KSData ksd) {
        if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_UP.get()))
            ksd.vc.setCurrentYVelocity(1 * ksd.vc.getYVelocity());
        else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_DOWN.get()))
            ksd.vc.setCurrentYVelocity(-1 * ksd.vc.getYVelocity());
        else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_RIGHT.get()))
            ksd.vc.setCurrentXVelocity(1 * ksd.vc.getXVelocity());
        else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_LEFT.get()))
            ksd.vc.setCurrentXVelocity(-1 * ksd.vc.getXVelocity());

        if (Gdx.input.isKeyPressed(KeyboardConfig.INTERACT_WORLD.get()))
            InteractionTool.interactWithClosestInteractable(ksd.e);

        //Quests
        if (Gdx.input.isKeyJustPressed(KeyboardConfig.OPEN_QUEST.get())) {
            quests_open = true;
            levelManager.printQuestInfo();
            System.out.println("Your choice: " + choice);
        }


        // check skills
        else if (Gdx.input.isKeyPressed(KeyboardConfig.FIRST_SKILL.get()))
            ksd.pc.getSkillSlot1().ifPresent(skill -> skill.execute(ksd.e));
        else if (Gdx.input.isKeyPressed(KeyboardConfig.SECOND_SKILL.get()))
            ksd.pc.getSkillSlot2().ifPresent(skill -> skill.execute(ksd.e));


    }

    private KSData buildDataObject(PlayableComponent pc) {
        Entity e = pc.getEntity();

        VelocityComponent vc = (VelocityComponent) e.getComponent(VelocityComponent.class)
            .orElseThrow(PlayerSystem::missingVC);

        return new KSData(e, pc, vc);
    }

    private static MissingComponentException missingVC() {
        return new MissingComponentException("VelocityComponent");
    }


}
