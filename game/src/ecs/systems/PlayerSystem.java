package ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import configuration.KeyboardConfig;
import ecs.components.MissingComponentException;
import ecs.components.PlayableComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.*;
import ecs.tools.interaction.InteractionTool;
import jdk.dynalink.linker.GuardingDynamicLinker;
import level.LevelAPI;
import level.elements.tile.TrapTile;
import level.myQuest.*;

import level.tools.LevelElement;
import starter.Game;

import java.nio.file.attribute.FileAttribute;
import java.security.Key;

import static starter.Game.*;


/**
 * Used to control the player
 */
public class PlayerSystem extends ECS_System {

    boolean quests_open = false;
    boolean inventory_open = false;
    int questChoice = 0;
    int inventoryChoice = 0;

    LevelManager levelManager = LevelManager.getInstance();


    private record KSData(Entity e, PlayableComponent pc, VelocityComponent vc) {
    }

    @Override
    public void update() {
        Game.getEntities().stream()
            .flatMap(e -> e.getComponent(PlayableComponent.class).stream())
            .map(pc -> buildDataObject((PlayableComponent) pc))
            .forEach(this::checkKeystroke);

        if(hero.isGodMode()&&LevelAPI.COUNTLEVEL(3)){
            hero.setGodMode(false);
        }else if(hero.isGodMode() && !LevelAPI.COUNTLEVEL(3)){
            hero.healthComponent.setCurrentHealthpoints(100);
        }

        System.out.println((hero.healthComponent.getCurrentHealthpoints()));



        //Quests
        if (quests_open) {
            //Zum auswählen der Quest
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.QUEST_DOWN.get())
                && questChoice < levelManager.myQuests.size() - 1) {
                questChoice++;
                System.out.println("Your choice: " + questChoice);
            }
            //Zum auswählen der Quest
            else if (Gdx.input.isKeyJustPressed(KeyboardConfig.QUEST_UP.get()) && questChoice > 0) {
                questChoice--;
                System.out.println("Your choice: " + questChoice);
            }
            //Quest kann angenommen werden.
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                if (!levelManager.myQuests.get(questChoice).isAgreed() && !levelManager.myQuests.get(questChoice).isAccomplished()) {
                    levelManager.myQuests.get(questChoice).setAgreed(true);
                } else if (levelManager.myQuests.get(questChoice).isAgreed()) {
                    levelManager.myQuests.get(questChoice).setAgreed(false);
                }

                System.out.println(questChoice + " was your choice!");

                quests_open = false;
                questChoice = 0;

            }
        }


        //Inventory
        if (inventory_open) {
            //Zum auswählen
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.ITEM_DOWN.get())
                && inventoryChoice < hero.getMyInventory().getItemAmount() - 1) {
                inventoryChoice++;
                System.out.println("Your choice: " + inventoryChoice);
            }
            //Zum auswählen
            else if (Gdx.input.isKeyJustPressed(KeyboardConfig.ITEM_UP.get()) && inventoryChoice > 0) {
                inventoryChoice--;
                System.out.println("Your choice: " + inventoryChoice);
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) &&
                hero.getMyInventory().isInInventory("Nahrung") && hero.getMyInventory().getInventoryItems().get(inventoryChoice).equals("Nahrung")){
                Nahrung.HEALPLAYER();
                hero.getMyInventory().getInventoryItems().remove(inventoryChoice);
                System.out.println("Player was healed");
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) &&
                hero.getMyInventory().isInInventory("Trank") &&
                hero.getMyInventory().getInventoryItems().get(inventoryChoice).equals("Trank")){
                hero.getMyInventory().getInventoryItems().remove(inventoryChoice);
                Trank.SETGODMODE();
                System.out.println("You have godMode for 3 Levels");
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) &&
                hero.getMyInventory().isInInventory("Zauberstab") &&
                hero.getMyInventory().getInventoryItems().get(inventoryChoice).equals("Zauberstab")){
                hero.getMyInventory().getInventoryItems().remove(inventoryChoice);
                Zauberstab.REMOVETRAPS();
                System.out.println("All Traps have been removed");

            }


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


        //Quests werden geöffnet
        if (Gdx.input.isKeyJustPressed(KeyboardConfig.OPEN_QUEST.get())) {
            quests_open = true;
            levelManager.printQuestInfo();
            System.out.println("Your choice: " + questChoice);
        }

        //Inventory wird geöffnet
        if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
            inventory_open = true;
            hero.getMyInventory().showInventory();
            System.out.println("Your choice: " + inventoryChoice);
        }


        hero.getMyInventory().setMaxSpace(5);


        for (Item item : items) {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.ITEM_COLLECT.get()) && hero.isCollidingWithItems(item)) {
                Game.removeEntity(item);
                hero.getMyInventory().addItems(item.getName());
            }


        }


        // check skills
        if (Gdx.input.isKeyPressed(KeyboardConfig.FIRST_SKILL.get())) {
            ksd.pc.getSkillSlot1().ifPresent(skill -> skill.execute(ksd.e));
        }
        if (Gdx.input.isKeyPressed(KeyboardConfig.SECOND_SKILL.get())) {
            ksd.pc.getSkillSlot2().ifPresent(skill -> skill.execute(ksd.e));
        }

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
