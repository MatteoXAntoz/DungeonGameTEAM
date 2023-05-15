package ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import configuration.KeyboardConfig;
import ecs.components.MissingComponentException;
import ecs.components.PlayableComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import ecs.items.*;
import ecs.tools.interaction.InteractionTool;
import level.myQuest.*;

import starter.Game;

import static starter.Game.*;


/**
 * Used to control the player
 */
public class PlayerSystem extends ECS_System {

    //Matteo

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


        /**
         *  Benutzen wir einen Trank, so erhoehen wir unser Maximale Leben um 10 Leben
         */


        if (hero.healthComponent.getCurrentHealthpoints() < 100) {
            hero.healthComponent.setMaximalHealthpoints(100);
        }


        if (hero.getMyInventory().isInInventory("PotionBag") || hero.getMyInventory().isInInventory("FoodBag")) {
            hero.playerHasBag = true;
        }

        /**Quests öffnen
         *
         */
        if (quests_open) {

            if (Gdx.input.isKeyJustPressed(KeyboardConfig.QUEST_DOWN.get())
                && questChoice < levelManager.myQuests.size() - 1) {
                questChoice++;
                System.out.println("Your choice: " + questChoice);
            }
            /**
             * Zum auswählen der Quest
             */

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
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                quests_open = false;
                System.out.println("Eingabe wurde abgebrochen");
            }
        }


        /**
         * Inventory
         */

        /**
         * Wenn das Inventar geöffnet wird.
         */

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
            /////////////////////////////////////////////////////////////////////////////
            //Auswahl der Items
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) &&
                hero.getMyInventory().isInInventory("Nahrung") && hero.getMyInventory().getInventoryItems().get(inventoryChoice).equals("Nahrung")) {
                Food.HEALPLAYER();
                hero.getMyInventory().getInventoryItems().remove(inventoryChoice);
                System.out.println("Player was healed");
                inventory_open = false;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) &&
                hero.getMyInventory().isInInventory("Trank") &&
                hero.getMyInventory().getInventoryItems().get(inventoryChoice).equals("Trank")) {
                hero.getMyInventory().getInventoryItems().remove(inventoryChoice);
                Potion.INCREASEMAXHEALTH();
                System.out.println("Your MaxLife has increased");
                inventory_open = false;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) &&
                hero.getMyInventory().isInInventory("Zauberstab") &&
                hero.getMyInventory().getInventoryItems().get(inventoryChoice).equals("Zauberstab")) {
                hero.getMyInventory().getInventoryItems().remove(inventoryChoice);
                Zauberstab.REMOVETRAPS();
                System.out.println("All Traps have been removed");
                inventory_open = false;

            }

            /**
             * Eingabe kann abgebrochen werden
             */
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                inventory_open = false;
                System.out.println("Eingabe wurde abgebrochen");
            }
            //////////////////////////////////////////////////

            /**Items koennen wieder fallengelassen werden*
             **/

            if (hero.getMyInventory().getInventoryItems().size() > 0)
                if (Gdx.input.isKeyJustPressed(Input.Keys.F) &&
                    hero.getMyInventory().getInventoryItems().get(inventoryChoice).equals("Nahrung") &&
                    hero.getMyInventory().isInInventory("Nahrung")) {
                    hero.getMyInventory().getInventoryItems().remove("Nahrung");
                    Food nahrung = new Food();
                    Game.items.add(nahrung);
                    nahrung.positionComponent.setPosition(hero.positionComponent.getPosition());
                    inventory_open = false;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.F) &&
                    hero.getMyInventory().getInventoryItems().get(inventoryChoice).equals("Trank") &&
                    hero.getMyInventory().isInInventory("Trank")) {
                    hero.getMyInventory().getInventoryItems().remove("Trank");
                    Potion trank = new Potion();
                    Game.items.add(trank);
                    trank.positionComponent.setPosition(hero.positionComponent.getPosition());
                    inventory_open = false;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.F) &&
                    hero.getMyInventory().getInventoryItems().get(inventoryChoice).equals("FoodBag") &&
                    hero.getMyInventory().isInInventory("FoodBag")) {
                    hero.getMyInventory().getInventoryItems().remove("FoodBag");
                    FoodBag foodBag = new FoodBag();
                    Game.items.add(foodBag);
                    foodBag.positionComponent.setPosition(hero.positionComponent.getPosition());
                    inventory_open = false;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.F) &&
                    hero.getMyInventory().getInventoryItems().get(inventoryChoice).equals("PotionBag") &&
                    hero.getMyInventory().isInInventory("PotionBag")) {
                    hero.getMyInventory().getInventoryItems().remove("PotionBag");
                    PotionBag potionBag = new PotionBag();
                    Game.items.add(potionBag);
                    potionBag.positionComponent.setPosition(hero.positionComponent.getPosition());
                    inventory_open = false;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.F) &&
                    hero.getMyInventory().getInventoryItems().get(inventoryChoice).equals("Zauberstab") &&
                    hero.getMyInventory().isInInventory("Zauberstab")) {
                    hero.getMyInventory().getInventoryItems().remove("Zauberstab");
                    Zauberstab zauberstab = new Zauberstab();
                    Game.items.add(zauberstab);
                    zauberstab.positionComponent.setPosition(hero.positionComponent.getPosition());
                    inventory_open = false;
                }

            /////////////////////////////////////////////////////////////////////////////////////////

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


        /**Quests werden geöffnet**/

        if (Gdx.input.isKeyJustPressed(KeyboardConfig.OPEN_QUEST.get())) {
            quests_open = true;
            levelManager.printQuestInfo();
            System.out.println("Your choice: " + questChoice);
        }

        /**Inventory wird geöffnet**/


        if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
            inventory_open = true;
            System.out.println("HeroInventory:");
            hero.getMyInventory().showInventory();
            System.out.println("Your choice: " + inventoryChoice);
        }


        for (Item item : items) {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.ITEM_COLLECT.get())
                && !item.collected
                && !hero.getMyInventory().isFull() &&
                hero.isCollidingWithItems(item)) {
                item.collected = true;
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
